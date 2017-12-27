package ng.com.coursecode.piqmessenger.firebasee;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.extLib.StartUp;
import ng.com.coursecode.piqmessenger.model__.Stores;

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
        context=FirebaseInstanceIdServ.this;
        Stores store=new Stores(context);
        if(!store.getUsername().isEmpty()) {
            (new StartUp(context)).subsrcibe();
        }
    }

    private void sendRegistrationToServer() {
        context=FirebaseInstanceIdServ.this;
        Stores store=new Stores(context);
        if(!store.getUsername().isEmpty()) {
            (new StartUp(context)).sendToken();
        }
    }
}
