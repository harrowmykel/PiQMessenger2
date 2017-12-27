package ng.com.coursecode.piqmessenger.networkcalls;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.interfaces.FetchMore;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.servicess.StatusCallService;

/**
 * Created by harro on 24/10/2017.
 */

public class StatusCall {


    Context context;

    public StatusCall(Context context1) {
        context=context1;
    }


    public void getAllMessages() {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.GET_MSG);
        context.startService(intent);
    }
    public void clear() {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.CLEAR);
        intent.putExtra(StatusCallService.CLEAR, true);
        context.startService(intent);
    }

    public void getAllMessages(FetchMore fetchMore) {
        getAllMessages();
    }

    public void getAllDelMessages() {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.DEL);
        intent.putExtra(StatusCallService.DEL, true);
        context.startService(intent);
    }

    public void hasViewed(ArrayList<String> all_updates) {
        Intent intent=new Intent(context, StatusCallService.class);
        intent.putExtra(StatusCallService.HAS_VIEWED, true);
        intent.putExtra(Stores.TYPE_OF_ACTION, StatusCallService.HAS_VIEWED);
        intent.putExtra(StatusCallService.STATCODES, all_updates);
        context.startService(intent);
    }
}
