package ng.com.coursecode.piqmessenger;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Random;

import ng.com.coursecode.piqmessenger.ExtLib.FullScreenActivity;
import ng.com.coursecode.piqmessenger.ExtLib.StartUp;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.Servicess.StatusCallService;
import ng.com.coursecode.piqmessenger.Signin.LoginActivity;

public class SplashScreen extends FullScreenActivity {

    public static String IS_NT_FIRST_TRIAL="is_first_trial";
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

    public void reqPerm(){
        String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {

            @Override
            public void onGranted() {
                if(!Prefs.getString(Profile.USERS_NAME, "").isEmpty()){
                    if(Prefs.getBoolean(StatusCallService.CHECKUPDATE, true)){
                        (new StartUp(context)).checkStatus();
                    }
                    if(Prefs.getBoolean(FirebaseInstanceIdServ.TOKEN_SENT, true)){
                        (new StartUp(context)).sendToken();
                    }
                    if(Prefs.getBoolean(FirebaseInstanceIdServ.SUBSCRIBED_TO_FRIENDS, false)){

                    }
                    (new StartUp(context)).checkMessage();
                    Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
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
