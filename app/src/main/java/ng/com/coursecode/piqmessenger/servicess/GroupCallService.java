package ng.com.coursecode.piqmessenger.servicess;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Group_tab;
import ng.com.coursecode.piqmessenger.database__.Status_tab;
import ng.com.coursecode.piqmessenger.fragments_.Groups;
import ng.com.coursecode.piqmessenger.interfaces.FetchMore;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 22/11/2017.
 */

public class GroupCallService extends Service {

    public static final String CHECKUPDATE = "checkgroupupdate";
    public static final String GET_MSG = "Knknf";
    public static final String CLEAR = "jkhgkjhljh";
    public static final String SUBSCRIBED_TO_FRIENDS = "jfkbfkj";
    public static final String SUBSCRIBE_TIME = "jkbfmdsjrnf";
    List<Model__> model_list;
    Stores stores;

    ApiInterface apiInterface;
    int page=1;
    boolean sendMsgAfterResult=false;
    String GRPCALL="Hbdfnzbdwjd";
    private FetchMore fetchMore;
    Context context;
    ArrayList<String> arrayList;
    String token;
    String location="1234";
    boolean moreCanBeLoaded;
    String username, sTime, sTTime;
    boolean redo=false;


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
        if(!redo){
            page=1;
        }
        redo=true;
        Prefs.putBoolean(CHECKUPDATE, true);
        Prefs.putBoolean(SUBSCRIBED_TO_FRIENDS, false);
        Call<Model__> call=apiInterface.getAllGroups(stores.getUsername(), stores.getPass(), stores.getApiKey(), ""+page);
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
                        stores.handleError(modelll.getError(), context, Stores.getServerError(context), true);
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
                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }else{
                    redo=false;
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
