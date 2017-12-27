package ng.com.coursecode.piqmessenger.adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class StatusViewHolderRecom extends RecyclerView.ViewHolder {

    public CircleImageView status_dp;

    public StatusViewHolderRecom(View itemView) {
        super(itemView);
        status_dp=(CircleImageView)itemView.findViewById(R.id.status_dp);
    }
}
