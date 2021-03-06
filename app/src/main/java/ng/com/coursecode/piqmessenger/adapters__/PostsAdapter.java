package ng.com.coursecode.piqmessenger.adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.coursecode.piqmessenger.adapters__.ViewHolders.PostsViewHolder;
import ng.com.coursecode.piqmessenger.database__.Posts_tab;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.interfaces.PostItemClicked;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {

    List<Posts_tab> messages_list;
    Context context;
    PostItemClicked postItemClicked;
//
//    public PostsAdapter(List<Posts_tab> messages_) {
//        super();
//        messages_list=messages_;
//    }

    public PostsAdapter(List<Posts_tab> model_list_, PostItemClicked postItemClicked_) {
        super();
        messages_list=model_list_;
        postItemClicked=postItemClicked_;
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.posts_layout, parent, false);
        return new PostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        if(messages_list.size()>0){
            Posts_tab messages=messages_list.get(position);
//            holder.bind(messages, postItemClicked, position);
            holder.posts_subtitle.setText((new TimeModel(context)).getDWM3(messages.getTime()));
            holder.posts_username.setText(messages.getFullname());
            holder.posts_text.setText(messages.getText());
            String lkes=Stores.notZero(messages.getLikes());

            holder.post_likes_text.setText(lkes);

            String image=messages.getImage();
            if(postItemClicked!=null)
                runListeners(holder, position);

            if(!image.isEmpty()){
                holder.posts_image.setVisibility(View.VISIBLE);
                Piccassa.load(context, image, R.drawable.nosong, R.drawable.empty, holder.posts_image, holder.progressBar);
            }else{
                holder.posts_image.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.GONE);
            }

            holder.posts_fav.setImageResource(Stores.getLikeImageRes(messages.getLiked()));
            Piccassa.loadDp(context, messages.getUser_image(), holder.posts_dp);
        }
    }

    private void runListeners(final PostsViewHolder holder, final int position) {
        holder.post_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onShowClicked(position);
            }
        });
        holder.posts_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onMoreClicked(position);
            }
        });
        holder.posts_fav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                postItemClicked.onShowMoreLikeOptions(position);
                return false;
            }
        });
        holder.posts_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onDMessageClicked(position);
            }
        });
        holder.posts_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onLikeClicked(position);
                int likess;
                if((messages_list.get(position).getLiked()).equals("0")){
                    holder.posts_fav.setImageResource(Stores.getLikeSymbol());
                    likess= (Stores.parseInt(messages_list.get(position).getLikes())+1);
                    holder.post_likes_text.setText(Stores.notZero(likess));
                }else{
                    holder.posts_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    likess=(Stores.parseInt(messages_list.get(position).getLikes())-1);
                    holder.post_likes_text.setText(Stores.notZero(likess));
                }
            }
        });
        holder.posts_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onReplyClicked(position);
            }
        });
        holder.posts_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onShowClicked(position);
            }
        });
        holder.posts_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onUsernameClicked(position);
            }
        });
        holder.posts_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onUsernameClicked(position);
            }
        });
        holder.posts_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postItemClicked.onShowClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }

}
