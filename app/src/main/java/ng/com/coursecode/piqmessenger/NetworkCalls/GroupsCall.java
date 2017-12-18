package ng.com.coursecode.piqmessenger.NetworkCalls;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Servicess.GroupCallService;
import ng.com.coursecode.piqmessenger.Servicess.GroupCallService;

/**
 * Created by harro on 24/10/2017.
 */

public class GroupsCall {

    Context context;
    Intent intent;
    public GroupsCall(Context context1) {
        super();
        context=context1;
    }
    
    public void getAllGroups() {
        intent=new Intent(context, GroupCallService.class);
        intent.putExtra(Stores.TYPE_OF_ACTION, GroupCallService.GET_MSG);
        context.startService(intent);
    }

    public void clear() {
        intent=new Intent(context, GroupCallService.class);
        intent.putExtra(GroupCallService.CLEAR, "Jnd");
        intent.putExtra(Stores.TYPE_OF_ACTION, GroupCallService.CLEAR);
        context.startService(intent);
    }


    private void checkMsg(boolean b) {
        GroupsCall messagesCall=new GroupsCall(context);
        if(b) {
            messagesCall.getAllGroups();
        }
    }

    public void refresh() {
        if(Prefs.getBoolean(GroupCallService.CHECKUPDATE, false)){
            checkMsg(true);
        }
    }
}
