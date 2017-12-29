package ng.com.coursecode.piqmessenger.servicess;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Status_tab;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.fragments_.Status;
import ng.com.coursecode.piqmessenger.interfaces.FetchMore;
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

public class StatusCallService extends Service {

    public static final String CHECKUPDATE = "checkstatupdate";
    public static final String DEL = "Jddkl";
    public static final String CLEAR = "Jdkkeke";
    public static final String HAS_VIEWED = "dllsfl";
    public static final String STATCODES = "kdfdk";
    public static final String SUBSCRIBE = "kdkfnkf";
    public static final String GET_MSG = "Knknf";
    List<Model__> model_list;
    Stores stores;

    ApiInterface apiInterface;
    int page=1, frndpage=1, delpage=1;
    boolean sendMsgAfterResult=false;
    String STATCALL="Hbdfnzhgcvhgnvkjhbjd";
    private FetchMore fetchMore;
    Context context;
    ArrayList<String> arrayList;
    String token;
    String location="1234";
    boolean moreCanBeLoaded;
    String username, sTime, sTTime;
    boolean redo=false, frndredo=false, delredo=false;
    String user_name;

    public StatusCallService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = StatusCallService.this;
        model_list = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ans;
        if(intent!=null){
            String todod=intent.getStringExtra(Stores.TYPE_OF_ACTION);
            switch (todod){
                case DEL:
                    getAllDeletedMessages();
                    break;
                case CLEAR:
                    DeleteAllMessages();
                    break;
                case SUBSCRIBE:
                    subscribeToAllFriendsPosts();
                    break;
                case HAS_VIEWED:
                    arrayList=intent.getStringArrayListExtra(STATCODES);
                    if(arrayList==null){
                        arrayList=new ArrayList<>();
                    }
                    HasViewedAllMessages();
                    break;
                case GET_MSG:
                default:
                    getAllMessages();
                    break;
            }
        }else{
            getAllMessages();
        }
        ans=Service.START_NOT_STICKY;
        return ans;
    }

    private void HasViewedAllMessages() {
        Status_tab messages_=new Status_tab();
        SQLiteDatabase db=messages_.getDb(context);
        for (String code:arrayList){
            messages_.viewed(db, code);
        }
        pushAllViewed(messages_, db);
    }

    private void pushAllViewed(final Status_tab smg, final SQLiteDatabase db) {
        final ArrayList<String> kl=smg.getOld(db);
        final String type=smg.mkString(kl);
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call= apiInterface.saveStatus(stores.getUsername(), stores.getPass(), stores.getApiKey(), type);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, serverError, true);
                }else if(modelll.getSuccess() !=null){
                    for (String code:arrayList){
                        smg.sent(db, code);
                    }
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "show stat");
            }
        });
    }

    ServerError serverError=Stores.getServerError(context);

    private void DeleteAllMessages() {
        Status_tab messages_=new Status_tab();
        messages_.delete(context);
    }

    public void getAllMessages(){
        if(!redo){
            page=1;
            sTTime=sTime=stores.getTime(STATCALL);
        }
        redo=true;
        Prefs.putBoolean(CHECKUPDATE, true);
        Call<Model__> call=apiInterface.getAllStatuses(stores.getUsername(), stores.getPass(), stores.getApiKey(), sTime, ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();
                int num=model_lis.size();
                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);
                    Status_tab messages_=new Status_tab();


                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, serverError, true);
                        break;
                    }

                    messages_.setUser_name(getString__(modelll.getAuth_username()));
                    messages_.setTime(getString__(modelll.getTime()));
                    messages_.setText(getString__(modelll.getSubtitle()));
                    messages_.setStatus_id(getString__(modelll.getStatus_code()));
                    messages_.setFav(getString__(modelll.getFav()));
                    messages_.setImage(getString__(modelll.getImage()));
                    messages_.setSeen("0");

                    Users_prof users_prof=new Users_prof();
                    users_prof.setUser_name(modelll.getAuth_username());
                    users_prof.setFullname(modelll.getAuth_data().getAuth());
                    users_prof.setImage(modelll.getAuth_data().getAuth_img());
                    users_prof.save(context);

                    sTTime=modelll.getTime();
                    try {
                        messages_.save(context);
                    }catch (Exception r){
                        stores.reportException(r, "statuscall.class");
                    }
                }

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }else{
                    redo=false;
                    Prefs.putBoolean(CHECKUPDATE, false);
                    Prefs.putLong(STATCALL, TimeModel.getLongTime(sTTime));
                    sendEnd();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "statuscall.class");
                Prefs.putLong(STATCALL, TimeModel.getLongTime(sTTime));
                sendEnd();
            }
        });
    }

    private void sendEnd() {
        Intent intent = new Intent(Status.REFRESH_VIEW_STATUS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        if(sendMsgAfterResult){
            fetchMore.fetchNow();
        }
    }


    public void getAllDeletedMessages(){
        if(!delredo){
            delpage=1;
        }
        delredo=true;
        Call<Model__> call=apiInterface.getAllDelStatuses(stores.getUsername(), stores.getPass(), stores.getApiKey(), ""+delpage);
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
                    Status_tab messages_=new Status_tab();


                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, serverError, true);
                        break;
                    }
                    try {
                        messages_.delete(context, getString__(modelll.getStatus_code()));
                    }catch (Exception r){
                        stores.reportException(r, "statuscall.class");
                    }
                }

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                delpage++;
                if(pgLeft>0){
                    getAllDeletedMessages();
                }else{
                    delredo=false;
                    sendEnd();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "statuscall.class");
            }
        });
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

    public void getAllMessages(FetchMore fetchMore) {
        this.fetchMore=fetchMore;
        sendMsgAfterResult=true;
        getAllMessages();
    }

    private void subscribeToAllFriendsPosts() {
        stores = new Stores(context);
        username=stores.getUsername();

        if(!frndredo){
            frndpage=1;
        }
        frndredo=true;
        if(frndredo) {
            FirebaseMessaging.getInstance().subscribeToTopic((Stores.APP_FIREBASE_TOPIC).toLowerCase());//status
            FirebaseMessaging.getInstance().subscribeToTopic((username.trim()+Stores.USER_FIREBASE_TOPIC).toLowerCase());//status

            Prefs.putBoolean(FirebaseInstanceIdServ.SUBSCRIBED_TO_FRIENDS, true);
            Prefs.putLong(FirebaseInstanceIdServ.SUBSCRIBE_TIME, System.currentTimeMillis());
            return;//todo remove
        }
        Prefs.putBoolean(FirebaseInstanceIdServ.SUBSCRIBED_TO_FRIENDS, false);
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String query="";


        Call<Model__> call=apiInterface.searchUsers(username, stores.getPass(), stores.getApiKey(), query, location, ""+frndpage);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();

                int num=model_lis.size();

                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);

                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, new ServerError() {
                            @Override
                            public void onEmptyArray() {
                            }

                            @Override
                            public void onShowOtherResult(String res__) {
                                if (Stores.serviceError.contains(res__)) {
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        }, true);
                        break;
                    }
                    user_name = modelll.getAuth_username();
                    FirebaseMessaging.getInstance().subscribeToTopic((user_name+Stores.TopicEND).toLowerCase());//status
                }

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                moreCanBeLoaded = (pgLeft>0);
                frndpage++;
                if(moreCanBeLoaded){
                    subscribeToAllFriendsPosts();
                }else{
                    frndredo=false;
                    Prefs.putBoolean(FirebaseInstanceIdServ.SUBSCRIBED_TO_FRIENDS, true);
                    Prefs.putLong(FirebaseInstanceIdServ.SUBSCRIBE_TIME, System.currentTimeMillis());
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "firbase subscribe");
            }
        });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
