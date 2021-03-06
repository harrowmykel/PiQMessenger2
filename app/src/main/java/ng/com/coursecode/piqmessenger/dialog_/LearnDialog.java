package ng.com.coursecode.piqmessenger.dialog_;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 01/12/2017.
 */

public class LearnDialog{

    public static final String DONT_SHOW_LEARN_HOW = "lkdndklznkln";
    Context context;
    AlertDialog.Builder dialog;

    public LearnDialog(Context context) {
        this.context = context;
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.learn_how_to_use);
        dialog.setMessage(R.string.how_to_use_app);
        dialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setPositiveButton(R.string.dont_show_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Prefs.putBoolean(DONT_SHOW_LEARN_HOW, true);
            }
        });
    }

    public void show(){
        dialog.show();
    }
}
