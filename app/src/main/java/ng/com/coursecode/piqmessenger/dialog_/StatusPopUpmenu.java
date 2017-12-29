package ng.com.coursecode.piqmessenger.dialog_;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.statuses.Show_Status;

/**
 * Created by harro on 28/12/2017.
 */

public class StatusPopUpmenu {
    PopupMenu popupMenu;
    SendDatum sendDatum;
    Context context;

    public StatusPopUpmenu(Context context1, View v, boolean thisUser_, SendDatum sendDatum1){
        sendDatum=sendDatum1;
        context=context1;

        popupMenu = new PopupMenu(context, v);
        if (thisUser_) {
            popupMenu.getMenuInflater().inflate(R.menu.menu_status_act, popupMenu.getMenu());
        } else {
            popupMenu.getMenuInflater().inflate(R.menu.menu_report, popupMenu.getMenu());
        }
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                sendDatum.send(Show_Status.FULLSCREEN);
            }
        });
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        DeleteDialog deleteDialog = new DeleteDialog(context, new SendDatum() {
                            @Override
                            public void send(Object object) {
                                boolean sdf = (boolean) object;
                                if (sdf) {
                                    sendDatum.send(Show_Status.DELETE);
                                }
                            }
                        });
                        deleteDialog.show();
                        break;
                    case R.id.action_view:
                        sendDatum.send(Show_Status.VIEW_USER);
                        break;
                }
                return false;
            }
        });
    }

    public void show(){
        popupMenu.show();
    }
}
