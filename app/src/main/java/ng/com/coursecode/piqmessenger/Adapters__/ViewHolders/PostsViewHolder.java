package ng.com.coursecode.piqmessenger.Adapters__.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class PostsViewHolder extends RecyclerView.ViewHolder {

    public TextView posts_username, posts_subtitle, posts_text;
    public CircleImageView posts_dp;
    public ImageView posts_image, posts_fav, posts_reply, posts_msg, posts_more;
    public View post_view;
    public ProgressBar progressBar;

    public PostsViewHolder(View itemView) {
        super(itemView);
        post_view=itemView;
        posts_username=(TextView)itemView.findViewById(R.id.posts_username);
        posts_subtitle=(TextView)itemView.findViewById(R.id.posts_subtitle);
        posts_text=(TextView)itemView.findViewById(R.id.posts_text);
        posts_dp=(CircleImageView) itemView.findViewById(R.id.posts_dp);
        posts_image=(ImageView) itemView.findViewById(R.id.posts_image);
        posts_fav=(ImageView) itemView.findViewById(R.id.posts_fav);
        posts_reply=(ImageView) itemView.findViewById(R.id.posts_reply);
        posts_msg=(ImageView) itemView.findViewById(R.id.posts_msg);
        posts_more=(ImageView) itemView.findViewById(R.id.posts_more);
        progressBar=(ProgressBar)itemView.findViewById(R.id.post_pic_prog);
    }
}
