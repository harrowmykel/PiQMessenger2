package ng.com.coursecode.piqmessenger;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.marcoscg.easypermissions.EasyPermissions;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Random;

import ng.com.coursecode.piqmessenger.extLib.FullScreenActivity;
import ng.com.coursecode.piqmessenger.extLib.StartUp;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.firebasee.FirebaseInstanceIdServ;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.servicess.GroupCallService;
import ng.com.coursecode.piqmessenger.servicess.StatusCallService;
import ng.com.coursecode.piqmessenger.signin.LoginActivity;

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
        //TODO SHOW SOME PERMISSION SHIT
        String[] permissions = {EasyPermissions.WRITE_EXTERNAL_STORAGE};
        int requestCode = 0;
        EasyPermissions.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];
            if (permission.equals(EasyPermissions.WRITE_EXTERNAL_STORAGE)) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    doAfterPermission();
                }else{
                    Toasta.makeText(SplashScreen.this, R.string.storage_perm,  Toast.LENGTH_SHORT);
                    reqPerm();
                }
            }
        }
    }
    private void doAfterPermission() {
        if(StartUp.isLogined(context)){
            if(Prefs.getBoolean(StatusCallService.CHECKUPDATE, true)){
                (new StartUp(context)).checkStatus();
            }
            if(Prefs.getBoolean(GroupCallService.CHECKUPDATE, true)){
                (new StartUp(context)).checkGroup();
            }
            if(!Prefs.getBoolean(FirebaseInstanceIdServ.TOKEN_SENT, false)){
                Stores stores=new Stores(context);
                if(!stores.getUsername().isEmpty()) {
                    (new StartUp(context)).sendToken();
                }
            }
            if(!Prefs.getBoolean(FirebaseInstanceIdServ.SUBSCRIBED_TO_FRIENDS, false)){
                (new StartUp(context)).subsrcibe();
            }
            (new StartUp(context)).checkMessage();
            Intent intent=new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        }else {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        }
        finish();
    }
}
