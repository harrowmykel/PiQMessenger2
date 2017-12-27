package ng.com.coursecode.piqmessenger.adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class DiscoverViewHolder extends RecyclerView.ViewHolder {

    public TextView discover_username, discover_subtitle;
    public CircleImageView discover_dp;
    public ImageView discover_image;

    public DiscoverViewHolder(View itemView) {
        super(itemView);
        discover_username=(TextView)itemView.findViewById(R.id.discover_username);
        discover_subtitle=(TextView)itemView.findViewById(R.id.discover_subtitle);
        discover_dp=(CircleImageView) itemView.findViewById(R.id.discover_dp);
        discover_image=(ImageView) itemView.findViewById(R.id.discover_image);
    }
}
