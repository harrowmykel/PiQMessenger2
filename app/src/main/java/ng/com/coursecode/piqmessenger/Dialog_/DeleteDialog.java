package ng.com.coursecode.piqmessenger.Dialog_;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.Interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 01/12/2017.
 */

public class DeleteDialog {

    public static final String DONT_SHOW_LEARN_HOW = "lkdndklznkln";
    Context context;
    AlertDialog.Builder dialog;
    SendDatum sendDatum;

    public DeleteDialog(Context context, SendDatum sendDatum_) {
        this.context = context;
        sendDatum=sendDatum_;
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.action_delete);
        dialog.setMessage(R.string.delete_confirm);
        dialog.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton(R.string.action_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sendDatum.send(true);
            }
        });
    }

    public void show(){
        dialog.show();
    }
}
