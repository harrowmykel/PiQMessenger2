package ng.com.coursecode.piqmessenger.Adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {

    public TextView group_username, group_subtitle;//, group_time;
    public CircleImageView group_dp;
    public View allview;
    public MaterialFancyButton group_frnd;

    public GroupViewHolder(View itemView) {
        super(itemView);
        allview=itemView;
        group_username=(TextView)itemView.findViewById(R.id.group_username);
        group_subtitle=(TextView)itemView.findViewById(R.id.group_subtitle);
//        group_time=(TextView)itemView.findViewById(R.id.group_time);
        group_dp=(CircleImageView) itemView.findViewById(R.id.group_dp);
        group_frnd=(MaterialFancyButton) itemView.findViewById(R.id.group_frnds);
    }
}
