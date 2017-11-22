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

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.goncalves.pugnotification.notification.PugNotification;
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
import ng.com.coursecode.piqmessenger.R;


public class MFirebaseMessagingService extends FirebaseMessagingService {
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
            notify(body);

            Gson gson=new Gson();
            try{
                NotificationData model__=gson.fromJson(body, NotificationData.class);

                NotificationData.Data data=model__.getData();

                if(data.getFriends()!=null){
                    notify(body);
                }else if(data.getGroupMessage()!=null){
                    saveGroupMessage(data);
                }else if(data.getMessage()!=null){
                    saveMessage(data);
                }else if(data.getNotify()!=null){
                    notify(body);
                }else if(data.getStatus()!=null){
                    saveStatus(data);
                }
            }catch (Exception r){
                stores.reportException(r, MFirename);
            }
        }
    }

    private void saveGroupMessage(NotificationData.Data data) {

        NotificationData.Message modelll=data.getMessage();
        Group_tab messages_=new Group_tab();

        messages_.setConfirm(getString__(modelll.getConfirm()));
        messages_.setImage(getString__(modelll.getImage()));
        messages_.setMess_age(getString__(modelll.getSubtitle()));
        messages_.setTim_e(getString__(modelll.getTimestamp()));
        messages_.setTime_stamp(getString__(modelll.getTimestamp()));
        messages_.setFullname(getString__(modelll.getRecivData().getReciv()));
        messages_.setAuth(getString__(modelll.getAuthData().getAuth()));
        messages_.setGroup_id(getString__(modelll.getRecivUsername()));
        messages_.setAuth(getString__(modelll.getAuthUsername()));
        messages_.setmsg_id(getString__(modelll.getId()));
        messages_.save(context);

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

        Intent intent1=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        PugNotification.with(context)
                .load()
                .title(R.string.new_message)
                .message(body)
                .identifier(MSG_NOTIFICATION_ID)
                .smallIcon(R.drawable.message_placeholder)
                .largeIcon(R.drawable.new_message)
                .flags(Notification.DEFAULT_ALL)
                .click(pendingIntent)
                .simple()
                .build();
    }

    public void notify(String body){
        PugNotification.with(context)
                .load()
                .title(body)
                .message(body)
                .bigTextStyle(body + body + body + body + body)
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }
}
