package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.SplashScreen;

/**
 * Created by harro on 07/12/2017.
 */

public class PiccMaqCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogined();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogined();
    }

    public void checkLogined(){
        if(!StartUp.isLogined(this)){
            startActivity(new Intent(this, SplashScreen.class));
            finish();
        }
    }
}
