package ng.com.coursecode.piqmessenger.adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class ConvoViewHolder extends RecyclerView.ViewHolder {

    public TextView convo_username, convo_time, convo_subtitle;
    public CircleImageView convo_dp;
    public View view;

    public ConvoViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        convo_username=(TextView)itemView.findViewById(R.id.convo_username);
        convo_time=(TextView)itemView.findViewById(R.id.convo_time);
        convo_subtitle=(TextView)itemView.findViewById(R.id.convo_subtitle);
        convo_dp=(CircleImageView) itemView.findViewById(R.id.convo_dp);
    }
}
