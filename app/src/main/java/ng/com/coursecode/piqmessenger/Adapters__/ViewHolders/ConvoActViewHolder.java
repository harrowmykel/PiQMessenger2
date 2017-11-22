package ng.com.coursecode.piqmessenger.Adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class ConvoActViewHolder extends RecyclerView.ViewHolder {

    public TextView convo_username, convo_time, convo_subtitle, convo_date;
    public ScaleImageView convo_dp;
    public View view, crdview;

    public ConvoActViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        crdview=itemView.findViewById(R.id.l1);
        convo_date=(TextView)itemView.findViewById(R.id.convo_date);
        convo_username=(TextView)itemView.findViewById(R.id.convo_username);
        convo_time=(TextView)itemView.findViewById(R.id.convo_time);
        convo_subtitle=(TextView)itemView.findViewById(R.id.convo_subtitle);
        convo_dp=(ScaleImageView) itemView.findViewById(R.id.convo_dp);
    }
}
