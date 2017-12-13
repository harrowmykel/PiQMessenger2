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
import ng.com.coursecode.piqmessenger.ExtLib.StartUp;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Servicess.StatusCallService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 31/10/2017.
 */

public class FirebaseInstanceIdServ extends FirebaseInstanceIdService {

    public static final String TOKEN_SENT = "jdbjdkbfjf";
    public static final String TOKEN = "KJdbjfkj";
    public static final String SUBSCRIBED_TO_FRIENDS = "knfk;fnk;gnk";
    public static final String SUBSCRIBE_TIME = "Kndk;fnkfn";
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
        Prefs.putString(FirebaseInstanceIdServ.TOKEN, token);
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
        (new StartUp(context)).subsrcibe();
    }

    private void sendRegistrationToServer() {
        (new StartUp(context)).sendToken();
    }
}
