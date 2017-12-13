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
import ng.com.coursecode.piqmessenger.Interfaces.ContactsItemClicked;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.GroupViewHolder;

/**
 * Created by harro on 12/10/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    List<Group_tab> messages_list;
    Context context;
    boolean isSearch;
    ContactsItemClicked contactsItemClicked1;

    public GroupAdapter(List<Group_tab> messages_, ContactsItemClicked contactsItemClicked) {
        super();
        messages_list=messages_;
        contactsItemClicked1=contactsItemClicked;
    }

    public GroupAdapter(List<Group_tab> messages_, boolean isSearch_, ContactsItemClicked contactsItemClicked) {
        messages_list=messages_;
        isSearch=isSearch_;
        contactsItemClicked1=contactsItemClicked;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.group_layout, parent, false);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder holder, int position) {
        if(messages_list.size()>0){
            Group_tab messages=messages_list.get(position);
            /*if(isSearch){
//                holder.group_time.setText("");
            }*/
            String dg="@"+messages.getUser_name();
            holder.group_subtitle.setText(dg);
            holder.group_username.setText(messages.getFullname());
            Piccassa.load(context, messages.image, R.drawable.group_error, holder.group_dp);
            holder.allview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positio=holder.getAdapterPosition();
                    contactsItemClicked1.onUsernameCLicked(positio);
                }
            });
            holder.group_frnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positio=holder.getAdapterPosition();
                    String op=messages_list.get(positio).getFriends();
                    Stores2.setGroupTextU(holder.group_frnd, op, context);
                    contactsItemClicked1.onFriendCLicked(positio);
                }
            });
            String type=messages.getFriends();
            Stores2.setGroupText(holder.group_frnd, type, context);
        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
