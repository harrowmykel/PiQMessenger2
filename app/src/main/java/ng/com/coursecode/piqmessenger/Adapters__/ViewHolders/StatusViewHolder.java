package ng.com.coursecode.piqmessenger.Adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class StatusViewHolder extends RecyclerView.ViewHolder {

    public TextView status_username, status_subtitle, status_time;
    public CircleImageView status_dp;
    public View view;

    public StatusViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        status_username=(TextView)itemView.findViewById(R.id.status_username);
        status_subtitle=(TextView)itemView.findViewById(R.id.status_subtitle);
        status_dp=(CircleImageView) itemView.findViewById(R.id.status_dp);
        status_time=(TextView) itemView.findViewById(R.id.status_time);
    }
}
