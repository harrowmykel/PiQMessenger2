package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.SplashScreen;

/**
 * Created by harro on 07/12/2017.
 */

public class PiccMaqCompatActivity extends AppCompatActivity {
    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    public BroadcastReceiver mMessageReceiverDef=null;
    Context context;

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

    public BroadcastReceiver getMessageReceiver() {
        return mMessageReceiverDef;
    }

    public void setMessageReceiver(BroadcastReceiver mMessageReceiverDef) {
        this.mMessageReceiverDef = mMessageReceiverDef;
    }

    public void listenToBroadCast(String string){
        context=getApplicationContext();
        if(getMessageReceiver()!=null) {
            LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiverDef,
                    new IntentFilter(string));
        }
    }

    @Override
    protected void onStop() {
        stopListening();
        super.onStop();
    }

    public void stopListening() {
        context=getApplicationContext();
        if(getMessageReceiver()!=null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiverDef);
        }
    }
}
