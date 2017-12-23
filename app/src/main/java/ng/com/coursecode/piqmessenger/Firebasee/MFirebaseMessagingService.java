package ng.com.coursecode.piqmessenger.Firebasee;

/**
 * Created by harro on 31/10/2017.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.goncalves.pugnotification.notification.PugNotification;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.Fragments_.Chats;
import ng.com.coursecode.piqmessenger.Fragments_.Status;
import ng.com.coursecode.piqmessenger.MainActivity;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.NotificationData;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.NetworkCalls.NotifyCall;
import ng.com.coursecode.piqmessenger.NetworkCalls.StatusCall;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Searches.ConvoSearchAct;


public class MFirebaseMessagingService extends FirebaseMessagingService {
    private int NOT_INT = 12;
    Context context;
    Stores stores;
    private String MFirename="MFirebaseMessagingService.class";
    String body;
    private static int MSG_NOTIFICATION_ID=223232;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        context = getApplicationContext();
        stores = new Stores(context);
        if (remoteMessage == null)
            return;

        body = null;

        if(remoteMessage.getData()!=null){
            body = remoteMessage.getData().toString();
        }else if(remoteMessage.getNotification()!=null) {
            body = remoteMessage.getNotification().getBody();
        }

        if (body==null)
            return;

        body = body.replace("&quot;", "\"");

        Gson gson=new Gson();
        try{
            NotificationData model__=gson.fromJson(body, NotificationData.class);
            NotificationData.Data data=model__.getData();
            if(data.getGroupMessage()!=null){
                saveGroupMessage(data);
            }else if(data.getMessage()!=null){
                saveMessage(data);
            }else if(data.getNotify()!=null){
                doNotify(data);
            }else if(data.getStatus()!=null){
                saveStatus(data);
            }else if(data.getDelstatus()!=null){
                delStatus(data);
            }
        }catch (Exception r){
            stores.reportServiceException(r, MFirename);
        }
    }

    private void delStatus(NotificationData.Data data) {
        String code=data.getDelstatus().getStatusCode();
        Status_tab messages_=new Status_tab();
        messages_.delete(context, code);
        StatusCall statusCall=new StatusCall(context);
        statusCall.getAllDelMessages();
    }

    private void doNotify(NotificationData.Data data) {
        int ResString=R.string.sent_req;
        NotificationData.Notify note=data.getNotify();
        String topic="";
        String not_id = note.getNotifId();
        Intent  intent=Stores.getIntentNotif(context, not_id);
        int not_int=intent.getIntExtra(Stores.NOTIFICATION_DISP_ID,  232);
        ResString=intent.getIntExtra(Stores.NOTIFICATION_STRING, ResString);

        topic=getString(ResString, note.getSubj());
        intent.putExtra(PostsAct.POSTID, note.getNotifC());
        intent.putExtra(Profile.USERNAME, note.getNotifC());
        if(not_int!=232){
            notify(topic, not_int, intent);
        }

        NotifyCall NotifyCall=new NotifyCall(context);
        NotifyCall.getAllNotifys();
    }

    private void saveGroupMessage(NotificationData.Data data) {
        NotificationData.Message modelll=data.getMessage();
        //TODO msgs here

        /*Group_tab usertab=new Group_tab();
        usertab.setUser_name(getString__(modelll.getAuthUsername()));
        usertab.setFullname(getString__(modelll.getAuthData().getAuth()));
        usertab.setImage(getString__(modelll.getImage()));
        usertab.save(context);*/
    }

    private void saveStatus(NotificationData.Data data) {
        NotificationData.Status modelll=data.getStatus();
        StatusCall statusCall=new StatusCall(context);
        statusCall.getAllMessages();
    }

    private void saveMessage(NotificationData.Data data) {
        MessagesCall messagesCall=new MessagesCall(context);
        messagesCall.getAllMessages();
        Intent intent1=new Intent(context, ConvoSearchAct.class);

        notify(getString(R.string.new_message_have), MSG_NOTIFICATION_ID, intent1);
    }

    public void notify(String body_, int NOT_INT_, Intent intent1){
        PendingIntent pendingIntent=PendingIntent.getActivity(context, NOT_INT_, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        PugNotification.with(context)
                .load()
                .identifier(NOT_INT_)
                .title(R.string.app_name)
                .message(body_)
                .smallIcon(R.drawable.message_placeholder)
                .largeIcon(R.drawable.new_message)
                .flags(Notification.DEFAULT_ALL)
                .click(pendingIntent)
                .autoCancel(true)
                .simple()
                .build();
    }
    public void notify(String body_){
        NOT_INT++;
        PugNotification.with(context)
                .load()
                .identifier(NOT_INT)
                .title(R.string.app_name)
                .message(body_)
                .bigTextStyle(body_)
                .smallIcon(R.drawable.message_placeholder)
                .largeIcon(R.drawable.new_message)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .simple()
                .build();
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }
}
