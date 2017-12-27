package ng.com.coursecode.piqmessenger.servicess;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 22/11/2017.
 */

public class MessageCallService extends Service {
    public static final String CHECKUPDATE = "checkmsgupdate";
    public static final String SEND_NEW = "jdklnksnd";
    public static final String CLEAR = "d.nkne";
    Context context;
    List<Model__> model_list;
    Stores stores;
    int page=1;

    ApiInterface apiInterface;

    String MSGCALL="hdksbljzhgbnvhjdjbk", sTime, sTTime, thisUser;
    boolean redo=false;

    public MessageCallService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = MessageCallService.this;
        model_list = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        thisUser=stores.getUsername();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            String action_type=intent.getStringExtra(Stores.TYPE_OF_ACTION);
            if(action_type!=null){
                switch (action_type){
                    case SEND_NEW:
                        sendAllMessages();
                        break;
                    case CLEAR:
                        DeleteAllMessages();
                        break;
                    default:
                        getAllMessages();
                        break;
                }
            }else{
                getAllMessages();
            }
        }else{
            getAllMessages();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendAllMessages(){
        Prefs.putBoolean(SEND_NEW, true);

        Messages messages_=new Messages();
        String[] combo=messages_.getOldMess(context);
        String text=combo[0];
        final String text1=combo[1];

        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.newMsg(stores.getUsername(), stores.getPass(), stores.getApiKey(), text);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, new ServerError() {
                        @Override
                        public void onEmptyArray() {
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            if(Stores.serviceError.contains(res__)){
                                Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }else if(modelll.getSuccess() !=null){
                    Prefs.putBoolean(SEND_NEW, false);
                    Messages msggdg=new Messages();
                    msggdg.delete(context, text1);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "converse");
            }
        });

    }

    private void DeleteAllMessages() {
        Messages messages_=new Messages();
        messages_.delete(context);
    }

    public void getAllMessages(){
        if(!redo){
            sTTime=sTime=stores.getTime(MSGCALL);
            page=1;
        }
        redo=true;
        Prefs.putBoolean(CHECKUPDATE, true);
        Call<Model__> call=apiInterface.getAllMessages(stores.getUsername(), stores.getPass(), stores.getApiKey(), sTime, ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();

                int num=model_lis.size();
                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);
                    Messages messages_=new Messages();
                    Users_prof users_prof=new Users_prof();
                    Users_prof users_prof1=new Users_prof();

                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, Stores.getServerError(context), true);
                        break;
                    }
                    if(modelll.getReciv_data()==null){
                        continue;
                    }

                    String auth=modelll.getAuth_username();
                    boolean userIsAuth=(auth.equalsIgnoreCase(thisUser));

                    if(userIsAuth){
                        users_prof.setUser_name(modelll.getReciv_username());
                        users_prof.setFullname(modelll.getReciv_data().getReciv());
                        users_prof.setImage(modelll.getReciv_data().getReciv_img());
                        users_prof.save(context);
                    }else {
                        users_prof1.setUser_name(modelll.getAuth_username());
                        users_prof1.setFullname(modelll.getAuth_data().getAuth());
                        users_prof1.setImage(modelll.getAuth_data().getAuth_img());
                        users_prof1.save(context);
                    }

                    messages_.setConfirm(getString__(modelll.getConfirm()));
                    messages_.setImage(getString__(modelll.getImage()));
                    messages_.setMess_age(getString__(modelll.getSubtitle()));
                    messages_.setTim_e(Stores.parseInt(getString__(modelll.getTimestamp())));
                    messages_.setTime_stamp(getString__(modelll.getTimestamp()));
                    messages_.setRecip(getString__(modelll.getReciv_data().getReciv()));
                    messages_.setAuth(getString__(modelll.getAuth_data().getAuth()));
                    messages_.setRecip(getString__(modelll.getReciv_username()));
                    messages_.setAuth(getString__(modelll.getAuth_username()));
                    messages_.setmsg_id(getString__(modelll.getId()));
                    messages_.setSent(1);
                    messages_.setContext(context);
                    sTTime=modelll.getTimestamp();
                    boolean fg=messages_.save(context);
                }
                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }else{
                    redo=false;
                    Prefs.putLong(MSGCALL, TimeModel.getLongTime(sTTime));
                    Prefs.putBoolean(CHECKUPDATE, false);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Chats.REFRESH_NEW_MESSAGE));
                   }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "messagescall.class");
                Prefs.putLong(MSGCALL, TimeModel.getLongTime(sTTime));
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Chats.REFRESH_NEW_MESSAGE));
                }
        });
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }


}
