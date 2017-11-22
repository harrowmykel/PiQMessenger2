package ng.com.coursecode.piqmessenger.Firebasee;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import ng.com.coursecode.piqmessenger.Adapters__.ContactAdapter;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 31/10/2017.
 */

public class FirebaseInstanceIdServ extends FirebaseInstanceIdService {

    public static String TAG="firebase";
    public static String FIREBASE_KEY="firebase_KEY";
    Context context;
    Stores stores;
    String token;
    int page=1;
    String location="1234";
    boolean moreCanBeLoaded;
    String username;

    public FirebaseInstanceIdServ() {
        super();
    }

    private void storeRegIdInPref(String token) {
        Prefs.putString(FIREBASE_KEY, token);
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(refreshedToken);
        token=refreshedToken;
        sendRegistrationToServer();
        subscribeToAllFriendsPosts();
    }

    private void subscribeToAllFriendsPosts() {
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String query="";
        username=stores.getUsername();

        Call<Model__> call=apiInterface.searchUsers(username, stores.getPass(), stores.getApiKey(), query, location, ""+page);
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
                            public void onShowOtherResult(int res__) {
                                if (Stores.serviceError.contains(res__)) {
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        });
                        break;
                    }
                    String user_name = modelll.getAuth_username();
                    FirebaseMessaging.getInstance().subscribeToTopic((user_name+Stores.TopicEND).toLowerCase());
                    FirebaseMessaging.getInstance().subscribeToTopic((user_name+username+Stores.TopicEND).toLowerCase());//auth+recip+ending
                }

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                moreCanBeLoaded = (pgLeft>0);
                if(moreCanBeLoaded){
                    subscribeToAllFriendsPosts();
                }else{
                    Toasta.makeText(FirebaseInstanceIdServ.this, "Subscribed to all", Toast.LENGTH_SHORT);
                }
                page++;
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "firbase subscribe");
            }
        });
    }

    private void sendRegistrationToServer() {
        context=FirebaseInstanceIdServ.this;
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
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
                            Toasta.makeText(FirebaseInstanceIdServ.this, R.string.error_sending_token, Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onShowOtherResult(int res__) {
                            if(Stores.serviceError.contains(res__)){
                                Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                            }
                        }
                    });

                }else if(modelll.getSuccess() !=null){
                    Toasta.makeText(context, R.string.token_saved, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }
}
