package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.List;

import ng.com.coursecode.piqmessenger.Db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.Firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.NetworkCalls.StatusCall;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Servicess.MessageCallService;
import ng.com.coursecode.piqmessenger.Servicess.StatusCallService;
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
        FirebaseInstanceId.getInstance().getToken();
        Prefs.putBoolean(SplashScreen.IS_NT_FIRST_TRIAL, true);
        Prefs.putBoolean(GifAct.srcIsTENOR, true);
        fetchOldThings();
        Prefs.putBoolean(ISLOGINED, true);
    }



    public void fetchOldThings(){
        checkStatus();
        checkMessage();
        Prefs.putBoolean(MessageCallService.CHECKUPDATE, true);

//        GroupsCall groupsCall=new GroupsCall(context);
//        groupsCall.getAllMessages();
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
        StatusCall statusCall=new StatusCall(context);
        MessagesCall messagesCall=new MessagesCall(context);
        statusCall.clear();
        messagesCall.clear();
        context.deleteDatabase(DB_Aro.SQLL_DATEBASE_NAME);
        (DB_Aro.getHelper(context)).clearAll();
        Prefs.clear();
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLogined(Context context) {
        return Prefs.getBoolean(StartUp.ISLOGINED, false);
    }

    public void sendToken() {
        Prefs.putBoolean(FirebaseInstanceIdServ.TOKEN_SENT, false);
        String token=Prefs.getString(FirebaseInstanceIdServ.TOKEN, "");
        if(token.trim().isEmpty()){
            return;
        }
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.sendToken(stores.getUsername(), token, stores.getApiKey());
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
                        public void onShowOtherResult(int res__) {
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
    }
}

