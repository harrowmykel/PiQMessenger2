package ng.com.coursecode.piqmessenger.networkcalls;

import android.content.Context;
import android.content.Intent;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.servicess.MessageCallService;

/**
 * Created by harro on 24/10/2017.
 */

public class MessagesCall {

    Context context;
    Intent intent;
    public MessagesCall(Context context1) {
        super();
        context=context1;
        intent=new Intent(context, MessageCallService.class);
    }


    public void getAllMessages() {
        intent=new Intent(context, MessageCallService.class);
        context.startService(intent);
    }


    public void sendAllMessages() {
        intent=new Intent(context, MessageCallService.class);
        intent.putExtra(MessageCallService.SEND_NEW, "Jnd");
        intent.putExtra(Stores.TYPE_OF_ACTION, MessageCallService.SEND_NEW);
        context.startService(intent);
    }

    public void clear() {
        intent.putExtra(MessageCallService.CLEAR, "Jnd");
        intent.putExtra(Stores.TYPE_OF_ACTION, MessageCallService.CLEAR);
        context.startService(intent);
    }


    private void checkMsg(boolean b) {
        MessagesCall messagesCall=new MessagesCall(context);
        if(b) {
            messagesCall.getAllMessages();
        }else{
            messagesCall.sendAllMessages();
        }
    }

    public void refresh() {
        if(Prefs.getBoolean(MessageCallService.CHECKUPDATE, false)){
            checkMsg(true);
        }

        if(Prefs.getBoolean(MessageCallService.SEND_NEW, false)) {
            checkMsg(false);
        }
    }
}
