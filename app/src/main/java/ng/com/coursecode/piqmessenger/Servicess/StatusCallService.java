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

import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Fragments_.Status;
import ng.com.coursecode.piqmessenger.Interfaces.FetchMore;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.NetworkCalls.StatusCall;
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

public class StatusCallService extends Service {

    public static final String CHECKUPDATE = "sdjbdfjnjklbsdf";
    List<Model__> model_list;
    Stores stores;

    ApiInterface apiInterface;
    int page=1;
    boolean sendMsgAfterResult=false;
    String STATCALL="Hbdfnzbjd";
    private FetchMore fetchMore;
    Context context;

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
        getAllMessages();
    }

    public void getAllMessages(){
        Prefs.putBoolean(CHECKUPDATE, true);
        Call<Model__> call=apiInterface.getAllStatuses(stores.getUsername(), stores.getPass(), stores.getApiKey(), stores.getTime(STATCALL), ""+page);
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
                            public void onShowOtherResult(int res__) {
                                if(Stores.serviceError.contains(res__)){
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        });
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

                    Stime=modelll.getTime();
                    try {
                        messages_.save(context);
                    }catch (Exception r){
                        stores.reportException(r, "statuscall.class");
                    }
                }


                Prefs.putLong(STATCALL, TimeModel.getLongTime(Stime));

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }else{
                    Prefs.putBoolean(CHECKUPDATE, false);
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
        Intent intent = new Intent(Status.REFRESH_VIEW_STATUS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        if(sendMsgAfterResult){
            fetchMore.fetchNow();
        }
    }


    public void getAllDeletedMessages(){
        Call<Model__> call=apiInterface.getAllDelStatuses(stores.getUsername(), stores.getPass(), stores.getApiKey(), stores.getTime(STATCALL), ""+page);
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
                            public void onShowOtherResult(int res__) {
                                if(Stores.serviceError.contains(res__)){
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        });
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

                    Stime=modelll.getTime();
                    try {
                        messages_.save(context);
                    }catch (Exception r){
                        stores.reportException(r, "statuscall.class");
                    }
                }


                Prefs.putLong(STATCALL, TimeModel.getLongTime(Stime));

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllDeletedMessages();
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


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
