package ng.com.coursecode.piqmessenger.Servicess;

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

import ng.com.coursecode.piqmessenger.Database__.Notify;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Datum;
import ng.com.coursecode.piqmessenger.Model__.Pagination;
import ng.com.coursecode.piqmessenger.Model__.PostsModel;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.NotificationsA.NotificationsAct;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 22/11/2017.
 */

public class NotifyCallService extends Service {
    public static final String CHECKUPDATE = "checknotifyupdate";
    public static final String SEND_NEW = "jdklnksnd";
    public static final String CLEAR = "d.nkgfxne";
    Context context;
    List<PostsModel> model_list;
    Stores stores;
    int page=1;

    ApiInterface apiInterface;

    String NOTIFCALL="hdksbljzfdsgjbmmsdjbk", sTime, sTTime;

    public NotifyCallService() {
        super();
    }
    boolean redo=false;

    @Override
    public void onCreate() {
        super.onCreate();

        context = NotifyCallService.this;
        model_list = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            String action_type=intent.getStringExtra(Stores.TYPE_OF_ACTION);
            if(action_type!=null){
                switch (action_type){
                    case CLEAR:
                        DeleteAllNotify();
                        break;
                    default:
                        getAllNotify();
                        break;
                }
            }else{
                getAllNotify();
            }
        }else{
            getAllNotify();
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void DeleteAllNotify() {
        Notify Notify_=new Notify();
        Notify_.delete(context);
        Intent intent = new Intent(NotificationsAct.REFRESH_VIEW_STATUS);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void getAllNotify(){
        if(!redo){
            page=1;
            sTTime=sTime=stores.getTime(NOTIFCALL);
        }
        redo=true;
        Prefs.putBoolean(CHECKUPDATE, true);
        Call<PostsModel> call=apiInterface.getAllNotify(stores.getUsername(), stores.getPass(), stores.getApiKey(), sTime, ""+page);
        call.enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Call<PostsModel> call, Response<PostsModel> response) {
                PostsModel model_lisj=response.body();
                List<Datum> model_lis=model_lisj.getData();
                Pagination model_l=model_lisj.getPagination();

                int num=model_lis.size();

                String Stime="0";
                for(int i=0; i<num; i++){
                    Datum modelll=model_lis.get(i);
                    Notify Notify_=new Notify();
                    Users_prof users_prof=new Users_prof();

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
                        break;
                    }
                    if(modelll.getAuthData()==null){
                        continue;
                    }

                    users_prof.setUser_name(modelll.getAuthUsername());
                    users_prof.setFullname(modelll.getAuthData().getAuth());
                    users_prof.setImage(modelll.getAuthData().getAuthImg());
                    users_prof.save(context);

                    Notify_.setSubj(getString__(modelll.getAuthUsername()));
                    Notify_.setMsg_id(getString__(modelll.getId()));
                    Notify_.setType(getString__(modelll.getNotifyId()));
                    Notify_.setSeen(getString__(modelll.getSeen()));
                    Notify_.setTime_stamp(getString__(modelll.getTimestamp()));
                    Notify_.setObj_id(getString__(modelll.getObjId()));
                    Notify_.setLink(getString__(modelll.getLink()));
                    sTTime=Stime=modelll.getTimestamp();
                    boolean fg=Notify_.save(context);
                }
                Prefs.putLong(NOTIFCALL, TimeModel.getLongTime(Stime));
                int pgLeft= Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllNotify();
                }else{
                    redo=false;
                    Prefs.putBoolean(CHECKUPDATE, false);
                    Prefs.putLong(NOTIFCALL, TimeModel.getLongTime(sTTime));
                    Intent intent = new Intent(NotificationsAct.REFRESH_VIEW_STATUS);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<PostsModel> call, Throwable t) {
                stores.reportThrowable(t, "Notifycall.class");
                Intent intent = new Intent(NotificationsAct.REFRESH_VIEW_STATUS);
                Prefs.putLong(NOTIFCALL, TimeModel.getLongTime(sTTime));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }


}
