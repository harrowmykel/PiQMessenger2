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

        body = (body == null) ? "jnf" : body;

        if(remoteMessage.getData()!=null){

        }
        if(remoteMessage.getNotification()!=null){
            body = remoteMessage.getNotification().getBody();
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
                    Intent intent1=new Intent(context, ConvoSearchAct.class);
                    notify(getString(R.string.new_message_have), MSG_NOTIFICATION_ID, intent1);

                }
            }catch (Exception r){
                stores.reportException(r, MFirename);
            }
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
        Intent  intent=new Intent(context, Profile.class);
        int not_int=232;
        int ResString=R.string.sent_req;
        NotificationData.Notify note=data.getNotify();
        String topic="";
        String not_id = note.getNotifId();
        switch (not_id){
            case "sent_req":
                intent=new Intent(context, Profile.class);
                ResString=R.string.sent_req;
                not_int=2321;
                break;
            case "mention_req":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.mention_req;
                not_int=222321;
                break;
            case "comment_like":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.comment_like;
                not_int=22232;
                break;
            case "comment_req":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.comment_req;
                not_int=22232;
                break;
            case "add_reply":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.add_reply;
                not_int=2223212;
                break;
            case "acc_req":
                intent=new Intent(context, Profile.class);
                ResString=R.string.acc_req;
                not_int=221221;
                break;
            case "add_like":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.add_like;
                not_int=220221;
                break;
            case "new_post_by_admin":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.new_post_by_admin;
                not_int=229321;
                break;
            case "new_post_r":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.new_post_r;
                not_int=2223212;
                break;
            case "send_piccoin":
                intent=new Intent(context, Profile.class);
                ResString=R.string.send_piccoin;
                not_int=22321;
                break;
            case "check_prof":
                intent=new Intent(context, Profile.class);
                ResString=R.string.check_prof;
                not_int=22221;
                break;
        }
        topic=getString(ResString, note.getSubj());
        intent.putExtra(PostsAct.POSTID, note.getNotifC());
        intent.putExtra(Profile.USERNAME, note.getNotifC());
        if(not_int!=232){
            notify(topic, not_int, intent);
        }
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
        NotificationData.Message modelll=data.getMessage();
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

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }
}
