package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.NetworkCalls.StatusCall;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.Servicess.MessageCallService;
import ng.com.coursecode.piqmessenger.SplashScreen;

/**
 * Created by harro on 28/11/2017.
 */

public class StartUp {
    public static final String ISLOGINED = "ihkfdlbfj";
    Context context;
    public StartUp(Context context1) {
        context=context1;
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

    }

    public static boolean isLogined(Context context) {
        return Prefs.getBoolean(StartUp.ISLOGINED, false);
    }
}

