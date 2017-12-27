package ng.com.coursecode.piqmessenger.dialog_;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.statuses.EditPost;

/**
 * Created by harro on 01/12/2017.
 */

public class MsgLongClickDialog {

    public static final String DONT_SHOW_LEARN_HOW = "lkdndklznkln";
    Context context;
    AlertDialog.Builder alert;
    String mscv;
    int msgId;
    SendDatum sendDatum;

    public MsgLongClickDialog(Context context1, String msgText, int msgIdo, SendDatum sendDatum1) {
        context=context1;
        mscv=msgText;
        msgId=msgIdo;
        sendDatum=sendDatum1;

        alert=new AlertDialog.Builder(context);
        int NewPost_array=R.array.copy_del;
        alert.setItems(NewPost_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText(mscv);
                        } else {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText(Stores.CLIPBOARD_LABEL, mscv);
                            clipboard.setPrimaryClip(clip);
                            Toasta.makeText(context, R.string.text_copied_to_clipboard, Toast.LENGTH_SHORT);
                        }
                        break;
                    case 1:
                        DeleteDialog deleteDialog=new DeleteDialog(context, new SendDatum() {
                            @Override
                            public void send(Object object) {
                                boolean sdf=(boolean)object;
                                if(sdf){
                                    (new Messages()).deleteto(context, ""+msgId);
                                    sendDatum.send(Chats.REFRESH);
                                }
                            }
                        });
                        deleteDialog.show();
                        break;
                }
            }
        });
    }

    public MsgLongClickDialog(Context context1, String username, SendDatum sendDatum1) {
        context=context1;
        mscv=username;
        sendDatum=sendDatum1;

        alert=new AlertDialog.Builder(context);
        int NewPost_array=R.array.refresh_del;
        alert.setItems(NewPost_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        sendDatum.send(Chats.REFRESH);
                        break;
                    case 1:
                        DeleteDialog deleteDialog=new DeleteDialog(context, new SendDatum() {
                            @Override
                            public void send(Object object) {
                                boolean sdf=(boolean)object;
                                if(sdf){
                                    (new Messages()).deleteFrom(context, mscv);
                                    sendDatum.send(Chats.REFRESH);
                                }
                            }
                        });
                        deleteDialog.show();
                        break;
                }
            }
        });
    }

    public void show(){
        alert.show();
    }
}
