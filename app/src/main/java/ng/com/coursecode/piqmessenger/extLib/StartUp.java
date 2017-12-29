package ng.com.coursecode.piqmessenger.extLib;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.List;

import ng.com.coursecode.piqmessenger.db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.fragments_.StatusFragment;
import ng.com.coursecode.piqmessenger.gif_replace.GifAct;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.networkcalls.GroupsCall;
import ng.com.coursecode.piqmessenger.networkcalls.MessagesCall;
import ng.com.coursecode.piqmessenger.networkcalls.StatusCall;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.servicess.GroupCallService;
import ng.com.coursecode.piqmessenger.servicess.MessageCallService;
import ng.com.coursecode.piqmessenger.servicess.StatusCallService;
import ng.com.coursecode.piqmessenger.SplashScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 28/11/2017.
 */

public class StartUp {
    public static final String ISLOGINED = "ihkfdlbfj";
    Stores stores;
    Context context;
    public StartUp(Context context1) {
        context=context1;
        stores = new Stores(context);
    }

    public void start(){
        context.deleteDatabase(DB_Aro.SQLL_DATEBASE_NAME);
        Prefs.putBoolean(ISLOGINED, true);
        FirebaseInstanceId.getInstance().getToken();
        Prefs.putBoolean(SplashScreen.IS_NT_FIRST_TRIAL, true);
        Prefs.putBoolean(Stores.PLAY_LIKE, true);
        Prefs.putBoolean(GifAct.srcIsTENOR, true);
        Prefs.putBoolean(StatusFragment.HAS_SEEN_DEF_STAT, false);
        fetchOldThings();
        FirebaseInstanceId.getInstance().getToken();
    }

    public void fetchOldThings(){
        Prefs.putBoolean(MessageCallService.CHECKUPDATE, true);
        Prefs.putBoolean(GroupCallService.CHECKUPDATE, true);
        checkStatus();
        checkMessage();
        checkGroup();
    }

    public void checkMessage() {
        MessagesCall messagesCall=new MessagesCall(context);
        messagesCall.refresh();
    }

    public void checkStatus() {
        StatusCall statusCall=new StatusCall(context);
        statusCall.getAllMessages();
    }

    public void end() {
        Prefs.putString(Profile.USERS_NAME, "");
        Prefs.putString(Profile.USERS_PASS, "");
        Prefs.putBoolean(ISLOGINED, false);
        Prefs.putBoolean(SplashScreen.IS_NT_FIRST_TRIAL, false);
        Prefs.putBoolean(FirebaseInstanceIdServ.TOKEN_SENT, false);
        Prefs.putString(FirebaseInstanceIdServ.TOKEN, "");
        context.deleteDatabase(DB_Aro.SQLL_DATEBASE_NAME);
        Prefs.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static boolean isLogined(Context context) {
        return Prefs.getBoolean(StartUp.ISLOGINED, false);
    }

    public void sendToken() {
        Prefs.putBoolean(FirebaseInstanceIdServ.TOKEN_SENT, false);
        String token=Prefs.getString(FirebaseInstanceIdServ.TOKEN, "");
        if(token.trim().isEmpty()){
            Prefs.putString(FirebaseInstanceIdServ.TOKEN, FirebaseInstanceId.getInstance().getToken());
            return;
        }

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.sendToken(stores.getUsername(), stores.getPass(), token, stores.getApiKey());
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
                            Toasta.makeText(context, R.string.error_sending_token, Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            if(Stores.serviceError.contains(res__)){
                                Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                            }
                        }
                    });

                }else if(modelll.getSuccess() !=null){
                    Prefs.putBoolean(FirebaseInstanceIdServ.TOKEN_SENT, true);
                    Toasta.makeText(context, R.string.token_saved, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    public void subsrcibe() {
        boolean sub=Prefs.getBoolean(FirebaseInstanceIdServ.SUBSCRIBED_TO_FRIENDS, false);
        boolean subTimeTooOld=(System.currentTimeMillis() - Prefs.getLong(FirebaseInstanceIdServ.SUBSCRIBE_TIME, 0))>Stores.SUB_REFRESH_TIME;
        if(!sub || subTimeTooOld){
            Intent intent=new Intent(context, StatusCallService.class);
            intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.SUBSCRIBE);
            context.startService(intent);
        }
        sub=Prefs.getBoolean(GroupCallService.SUBSCRIBED_TO_FRIENDS, false);
        subTimeTooOld=(System.currentTimeMillis() - Prefs.getLong(GroupCallService.SUBSCRIBE_TIME, 0))>Stores.SUB_REFRESH_TIME;
        if(!sub || subTimeTooOld){
            Intent intent=new Intent(context, GroupCallService.class);
            intent.putExtra(Stores.TYPE_OF_ACTION, GroupCallService.GET_MSG);
            context.startService(intent);
        }
    }

    public void checkGroup() {
        GroupsCall groupsCall=new GroupsCall(context);
        groupsCall.getAllGroups();
    }
}

