package ng.com.coursecode.piqmessenger.Servicess;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.Fragments_.Groups;
import ng.com.coursecode.piqmessenger.Fragments_.Status;
import ng.com.coursecode.piqmessenger.Interfaces.FetchMore;
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

public class GroupCallService extends Service {

    public static final String CHECKUPDATE = "sdjbdfjnjkjnaklbsdf";
    public static final String GET_MSG = "Knknf";
    public static final String CLEAR = "jkhgkjhljh";
    public static final String SUBSCRIBED_TO_FRIENDS = "jfkbfkj";
    public static final String SUBSCRIBE_TIME = "jkbfmdsjrnf";
    List<Model__> model_list;
    Stores stores;

    ApiInterface apiInterface;
    int page=1;
    boolean sendMsgAfterResult=false;
    String STATCALL="Hbdfnzbjd";
    private FetchMore fetchMore;
    Context context;
    ArrayList<String> arrayList;
    String token;
    String location="1234";
    boolean moreCanBeLoaded;
    String username;

    public GroupCallService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = GroupCallService.this;
        model_list = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int ans;
        page=1;
        if(intent!=null){
            String todod=intent.getStringExtra(Stores.TYPE_OF_ACTION);
            switch (todod){
                case GET_MSG:
                default:
                    clearAll();
                    getAllMessages();
                    break;
            }
        }else{
            getAllMessages();
        }
        ans=Service.START_NOT_STICKY;
        return ans;
    }

    private void clearAll() {
        Group_tab group_tab=new Group_tab();
        group_tab.clear(context);
    }

    public void getAllMessages(){
        Prefs.putBoolean(CHECKUPDATE, true);
        Prefs.putBoolean(SUBSCRIBED_TO_FRIENDS, false);
        Call<Model__> call=apiInterface.getAllGroups(stores.getUsername(), stores.getPass(), stores.getApiKey(), stores.getTime(STATCALL), ""+page);
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
                        stores.handleError(modelll.getError(), context, new ServerError() {
                            @Override
                            public void onEmptyArray() {
//                                Toasta.makeText(context, R.string.error_sending_token, Toast.LENGTH_SHORT);
                            }

                            @Override
                            public void onShowOtherResult(String res__) {
                                if(Stores.serviceError.contains(res__)){
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        });
                        break;
                    }else {
                        String user_name = modelll.getAuth_username();
                        String image = modelll.getAuth_data().getAuth_img();
                        String fullnames = modelll.getAuth_data().getFullname();

                        Group_tab usertab = new Group_tab();
                        usertab.setUser_name(user_name);
                        usertab.setFullname(fullnames);
                        usertab.setImage(image);
                        Groups.subscribeTo(context, usertab, true);
                    }
                }

                Prefs.putLong(STATCALL, TimeModel.getLongTime(Stime));

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }else{
                    Prefs.putBoolean(CHECKUPDATE, false);
                    Prefs.putBoolean(SUBSCRIBED_TO_FRIENDS, true);
                    Prefs.putLong(SUBSCRIBE_TIME, System.currentTimeMillis());
                    sendEnd();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "statuscall.class");
                sendEnd();
            }
        });
    }

    private void sendEnd() {
        Intent intent = new Intent(Groups.REFRESH_VIEW_GROUP);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        if(sendMsgAfterResult){
            fetchMore.fetchNow();
        }
    }
    
    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

    public void getAllMessages(FetchMore fetchMore) {
        this.fetchMore=fetchMore;
        sendMsgAfterResult=true;
        getAllMessages();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
