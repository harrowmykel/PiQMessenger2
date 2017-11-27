package ng.com.coursecode.piqmessenger;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Random;

import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.NetworkCalls.GroupsCall;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.NetworkCalls.StatusCall;
import ng.com.coursecode.piqmessenger.Servicess.MessageCallService;
import ng.com.coursecode.piqmessenger.Servicess.StatusCallService;

public class SplashScreen extends AppCompatActivity {

    private String IS_NT_FIRST_TRIAL="is_first_trial";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context=SplashScreen.this;

        int rand=(new Random()).nextInt(4);
        Techniques techniques;
        Techniques[] techniques1={Techniques.BounceInDown,Techniques.RubberBand,Techniques.Tada,Techniques.FadeIn,Techniques.Pulse};
        techniques=techniques1[rand];
        YoYo.with(techniques).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                reqPerm();
            }
        }).duration(2000).playOn(findViewById(R.id.ic_launcher));

    }

    public void fetchOldThings(){
        checkStatus();
        checkMsg();
//        GroupsCall groupsCall=new GroupsCall(context);
//        groupsCall.getAllMessages();
    }

    private void checkMsg() {
        MessagesCall messagesCall=new MessagesCall(context);
        messagesCall.getAllMessages();
    }

    private void checkStatus() {
        StatusCall statusCall=new StatusCall(context);
        statusCall.getAllMessages();
    }

    public void reqPerm(){
        String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, perms, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        if(!Prefs.getBoolean(IS_NT_FIRST_TRIAL, false)){
                            FirebaseInstanceId.getInstance().getToken();
                            Prefs.putBoolean(IS_NT_FIRST_TRIAL, true);
                            Prefs.putBoolean(GifAct.srcIsTENOR, true);
                            fetchOldThings();
                        }else{
                            if(Prefs.getBoolean(StatusCallService.CHECKUPDATE, false)){
                                checkStatus();
                            }
                            if(Prefs.getBoolean(MessageCallService.CHECKUPDATE, false)){
                                checkMsg();
                            }
                        }
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onDenied(String permission) {
                        Toasta.makeText(SplashScreen.this,
                                "Sorry, we need the Storage Permission to do that",
                                Toast.LENGTH_SHORT);
                        reqPerm();
                    }
                });
    }
}
