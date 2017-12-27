package ng.com.coursecode.piqmessenger.adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class ConvoActViewHolder extends RecyclerView.ViewHolder {

    public TextView convo_username, txtView_unread, convo_time, convo_subtitle, convo_date;
    public ScaleImageView convo_dp;
    public ImageView convo_icon;
    public View view, crdview, crdview_unread;

    public ConvoActViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        crdview=itemView.findViewById(R.id.l2);
        crdview_unread=itemView.findViewById(R.id.l1);
        txtView_unread=(TextView)itemView.findViewById(R.id.convo_unread);
        convo_date=(TextView)itemView.findViewById(R.id.convo_date);
        convo_username=(TextView)itemView.findViewById(R.id.convo_username);
        convo_time=(TextView)itemView.findViewById(R.id.convo_time);
        convo_subtitle=(TextView)itemView.findViewById(R.id.convo_subtitle);
        convo_dp=(ScaleImageView) itemView.findViewById(R.id.convo_dp);
        convo_icon=(ImageView) itemView.findViewById(R.id.img_icon);
    }
}
