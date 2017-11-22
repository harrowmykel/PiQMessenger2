package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.GroupViewHolder;

/**
 * Created by harro on 12/10/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    List<Group_tab> messages_list;
    Context context;
    boolean isSearch;

    public GroupAdapter(List<Group_tab> messages_) {
        super();
        messages_list=messages_;
    }

    public GroupAdapter(List<Group_tab> messages_, boolean isSearch_) {
        messages_list=messages_;
        isSearch=isSearch_;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.group_layout, parent, false);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        if(messages_list.size()>0){
            Group_tab messages=messages_list.get(position);
            if(isSearch){
                holder.group_time.setText("");
            }
            holder.group_subtitle.setText(messages.getMess_age());
            holder.group_username.setText(messages.getUser_name());
            Piccassa.load(context, messages.image, R.drawable.group_error, holder.group_dp);
        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
