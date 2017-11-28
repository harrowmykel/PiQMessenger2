package ng.com.coursecode.piqmessenger.Servicess;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 22/11/2017.
 */

public class MessageCallService extends Service {
    public static final String CHECKUPDATE = "fd,bkjfdkfbhfsjkbhskb vjhknb";
    public static final String SEND_NEW = "jdklnksnd";
    Context context;
    List<Model__> model_list;
    Stores stores;
    int page=1;

    ApiInterface apiInterface;

    String MSGCALL="hdksbljzdjbk";

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
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       String joshua="";
        if(intent!=null){
            if(intent.getStringExtra(SEND_NEW)!=null){
                if(!intent.getStringExtra(SEND_NEW).isEmpty()){
                    joshua=intent.getStringExtra(SEND_NEW);
                }
            }
        }
       if(joshua==null || joshua.isEmpty()){
           getAllMessages();
       }else{
           sendAllMessages();
       }
        int ans=super.onStartCommand(intent, flags, startId);
        return ans;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

public void sendAllMessages(){
    Prefs.putBoolean(SEND_NEW, true);

    Messages messages_=new Messages();
    String text=messages_.getOldMess(context);

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
                    public void onShowOtherResult(int res__) {
                        if(Stores.serviceError.contains(res__)){
                            Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                        }
                    }
                });
            }else if(modelll.getSuccess() !=null){
                Prefs.putBoolean(SEND_NEW, false);
            }
        }

        @Override
        public void onFailure(Call<Model__> call, Throwable t) {
            (new Stores(context)).reportThrowable(t, "converse");
        }
    });

}

    public void getAllMessages(){
        Prefs.putBoolean(CHECKUPDATE, true);
        Call<Model__> call=apiInterface.getAllMessages(stores.getUsername(), stores.getPass(), stores.getApiKey(), stores.getTime(MSGCALL), ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();

                int num=model_lis.size();

                String Stime="0";
                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);
                    Messages messages_=new Messages();
                    Users_prof users_prof=new Users_prof();
                    Users_prof users_prof1=new Users_prof();

                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, new ServerError() {
                            @Override
                            public void onEmptyArray() {

                            }

                            @Override
                            public void onShowOtherResult(int res__) {
                                if(Stores.serviceError.contains(res__)){
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        });
                        break;
                    }
                    if(modelll.getReciv_data()==null){
                        continue;
                    }

                    users_prof.setUser_name(modelll.getReciv_username());
                    users_prof.setFullname(modelll.getReciv_data().getReciv());
                    users_prof.setImage(modelll.getReciv_data().getReciv_img());
                    users_prof1.setUser_name(modelll.getAuth_username());
                    users_prof1.setFullname(modelll.getAuth_data().getAuth());
                    users_prof1.setImage(modelll.getAuth_data().getAuth_img());

                    users_prof.save(context);
                    users_prof1.save(context);

                    messages_.setConfirm(getString__(modelll.getConfirm()));
                    messages_.setImage(getString__(modelll.getImage()));
                    messages_.setMess_age(getString__(modelll.getSubtitle()));
                    messages_.setTim_e(getString__(modelll.getTimestamp()));
                    messages_.setTime_stamp(getString__(modelll.getTimestamp()));
                    messages_.setRecip(getString__(modelll.getReciv_data().getReciv()));
                    messages_.setAuth(getString__(modelll.getAuth_data().getAuth()));
                    messages_.setRecip(getString__(modelll.getReciv_username()));
                    messages_.setAuth(getString__(modelll.getAuth_username()));
                    messages_.setmsg_id(getString__(modelll.getId()));
                    messages_.setSent(1);
                    messages_.setContext(context);
                    Stime=modelll.getTimestamp();
                    boolean fg=messages_.save(context);
                }
                Prefs.putLong(MSGCALL, TimeModel.getLongTime(Stime));
                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }else{
                    Prefs.putBoolean(CHECKUPDATE, false);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "messagescall.class");
            }
        });
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }


}
