package ng.com.coursecode.piqmessenger.dialog_;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.statuses.EditPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 01/12/2017.
 */

public class MsgLongClickDialog {

    public static final String DONT_SHOW_LEARN_HOW = "lkdndklznkln";
    Context context;
    AlertDialog.Builder alert;
    String mscv;
    int msgId;
    String msgId2;
    SendDatum sendDatum;
    Stores stores;

    public MsgLongClickDialog(Context context1, String msgText, int msgIdo, String msgIdo2, SendDatum sendDatum1) {
        context=context1;
        mscv=msgText;
        msgId=msgIdo;
        msgId2=msgIdo2;
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
                                    sendToMsgServer(msgId2, false);
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
                        sendDatum.send(R.string.action_search);
                        break;
                    case 2:
                        DeleteDialog deleteDialog=new DeleteDialog(context, new SendDatum() {
                            @Override
                            public void send(Object object) {
                                boolean sdf=(boolean)object;
                                if(sdf){
                                    sendToMsgServer(mscv, true);
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

    public void sendToMsgServer(String name, final boolean is_convo){
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.deleteById(stores.getUsername(), stores.getPass(), stores.getApiKey(), name);
        if(is_convo){
            call = apiInterface.deleteConvo(stores.getUsername(), stores.getPass(), stores.getApiKey(), name);
        }
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null){
                    stores.handleError(modelll.getError(), context, new ServerError() {
                        @Override
                        public void onEmptyArray() {
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                        }
                    });
                }else if(modelll.getSuccess() !=null){
                    if(is_convo) {
                        (new Messages()).deleteFrom(context, mscv);
                    }else{
                        (new Messages()).deleteto(context, ""+msgId);
                    }
                    sendDatum.send(Chats.REFRESH);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "editprofile");
                Toasta.makeText(context, R.string.error_no_internet_unable_to_delete, Toast.LENGTH_SHORT);
            }
        });
    }
}
