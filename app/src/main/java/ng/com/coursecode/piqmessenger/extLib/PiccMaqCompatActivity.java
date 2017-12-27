package ng.com.coursecode.piqmessenger.extLib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.SplashScreen;

/**
 * Created by harro on 07/12/2017.
 */

public class PiccMaqCompatActivity extends AppCompatActivity {
    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    public BroadcastReceiver mMessageReceiverDef=null;
    Context context=null;
    ArrayList<String> broadcastStringList=new ArrayList<>();

    public Context getContext() {
        context=(context==null)?getApplicationContext():context;
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogined();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogined();
        resumeListening();
    }

    private void resumeListening() {
        for(String broad: broadcastStringList){
            listenToBroadCast(broad, false);
        }
    }

    public void checkLogined(){
        context=getContext();
        if(!StartUp.isLogined(this)){
            Toasta.makeText(context, R.string.relogin, Toast.LENGTH_SHORT);
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

    public void setMessageReceiver(Context context, BroadcastReceiver mMessageReceiver) {
        setMessageReceiver(mMessageReceiver);
        setContext(context);
    }

    public void listenToBroadCast(String string){
        listenToBroadCast(string, true);
    }

    public void listenToBroadCast(String string, boolean addToArray){
        context=getContext();
        if(getMessageReceiver()!=null) {
            registerReceiver(mMessageReceiverDef, new IntentFilter(string));
            if(addToArray) {
                broadcastStringList.add(string);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopListening();
    }

    public void stopListening() {
        context=getContext();
        if(getMessageReceiver()!=null) {
            unregisterReceiver(mMessageReceiverDef);
        }
    }
}
