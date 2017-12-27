package ng.com.coursecode.piqmessenger.networkcalls;

import android.content.Context;
import android.content.Intent;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.servicess.GroupCallService;

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
