package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by harro on 23/12/2017.
 */

public class PiccMaqFragment extends Fragment {

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    public BroadcastReceiver mMessageReceiverDef=null;
    Context context;

    public BroadcastReceiver getMessageReceiver() {
        return mMessageReceiverDef;
    }

    public void setMessageReceiver(BroadcastReceiver mMessageReceiverDef) {
        this.mMessageReceiverDef = mMessageReceiverDef;
    }

    public void listenToBroadCast(String string){
        context=getContext();
        if(getMessageReceiver()!=null) {
            LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiverDef,
                    new IntentFilter(string));
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
