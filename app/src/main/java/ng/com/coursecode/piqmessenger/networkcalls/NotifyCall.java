package ng.com.coursecode.piqmessenger.networkcalls;

import android.content.Context;
import android.content.Intent;

import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.servicess.NotifyCallService;

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
