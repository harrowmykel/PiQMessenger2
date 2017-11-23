package ng.com.coursecode.piqmessenger.Adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView users_username, users_subtitle;
    public CircleImageView users_dp;
    public MaterialFancyButton users_frnd;
    public View view;

    public ContactViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        users_username=(TextView)itemView.findViewById(R.id.users_username);
        users_subtitle=(TextView)itemView.findViewById(R.id.users_subtitle);
        users_dp=(CircleImageView) itemView.findViewById(R.id.users_dp);
        users_frnd=(MaterialFancyButton) itemView.findViewById(R.id.users_frnds);
    }
}
