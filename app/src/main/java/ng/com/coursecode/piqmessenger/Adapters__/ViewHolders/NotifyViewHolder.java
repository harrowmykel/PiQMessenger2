package ng.com.coursecode.piqmessenger.Adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class NotifyViewHolder extends RecyclerView.ViewHolder {

    public TextView notif_text;
    public CircleImageView users_dp;
    public View view;

    public NotifyViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        notif_text=(TextView)itemView.findViewById(R.id.users_username);
        users_dp=(CircleImageView) itemView.findViewById(R.id.users_dp);
    }
}
