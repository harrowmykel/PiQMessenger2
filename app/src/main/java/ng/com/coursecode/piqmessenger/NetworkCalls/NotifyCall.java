package ng.com.coursecode.piqmessenger.NetworkCalls;

import android.content.Context;
import android.content.Intent;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Servicess.GroupCallService;
import ng.com.coursecode.piqmessenger.Servicess.NotifyCallService;

/**
 * Created by harro on 24/10/2017.
 */

public class NotifyCall {

    Context context;
    Intent intent;
    public NotifyCall(Context context1) {
        super();
        context=context1;
        intent=new Intent(context, NotifyCallService.class);
    }


    public void getAllNotifys() {
        context.startService(intent);
    }

    public void clear() {
        intent.putExtra(NotifyCallService.CLEAR, "Jnd");
        intent.putExtra(Stores.TYPE_OF_ACTION, NotifyCallService.CLEAR);
        context.startService(intent);
    }
}
