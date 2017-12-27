package ng.com.coursecode.piqmessenger.extLib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by harro on 23/12/2017.
 */

public class PiccMaqFragment extends Fragment {

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    public BroadcastReceiver mMessageReceiverDef=null;
    Context context;
    ArrayList<String> broadcastStringList=new ArrayList<>();

    public BroadcastReceiver getMessageReceiver() {
        return mMessageReceiverDef;
    }

    public void setMessageReceiver(BroadcastReceiver mMessageReceiverDef) {
        this.mMessageReceiverDef = mMessageReceiverDef;
    }

    public void listenToBroadCast(String string){
        listenToBroadCast(string, true);
    }

    public void listenToBroadCast(String string, boolean addToArray){
        context=getContext();
        if(getMessageReceiver()!=null) {
            if(addToArray) {
                broadcastStringList.add(string);
            }
            LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiverDef,
                    new IntentFilter(string));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resumeListening();
    }

    private void resumeListening() {
        for(String broad: broadcastStringList){
            listenToBroadCast(broad, false);
        }
    }

    @Override
    public void onDestroy() {
        context=getContext();
        if(getMessageReceiver()!=null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiverDef);
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        context=getContext();
        // Unregister since the activity is about to be closed.
        if(getMessageReceiver()!=null) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiverDef);
        }
        super.onDestroyView();
    }

}
