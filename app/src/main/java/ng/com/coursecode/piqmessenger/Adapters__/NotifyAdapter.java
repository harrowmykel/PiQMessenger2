package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.ContactViewHolder;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Interfaces.ContactsItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.NotifyItemClicked;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class NotifyAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    List<Users_prof> messages_list;
    Context context;
    boolean showFriendsIcon=false;
    NotifyItemClicked contactsItemClicked;

    public NotifyAdapter(List<Users_prof> messages_) {
        super();
        messages_list=messages_;
    }

    public NotifyAdapter(List<Users_prof> messages_, ContactsItemClicked contact) {
        super();
        messages_list=messages_;
        contactsItemClicked=contact;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.contact_layout, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if(messages_list.size()>0){
            Users_prof messages=messages_list.get(position);
            String cv="@"+ messages.getUser_name();
            holder.users_username.setText(Stores.ucWords(messages.getFullname()));
            holder.users_subtitle.setText(cv);
            setHolderOnClick(holder, position);
            if(!showFriendsIcon){
                holder.users_frnd.setVisibility(View.GONE);
            }else{
                holder.users_frnd.setVisibility(View.VISIBLE);
                Stores2.setFrndText(holder.users_frnd, messages.getFrndsData(), context);
            }
            String like=messages.getLike();
            if(like!=null && !like.isEmpty()){
                holder.users_like.setImageResource(Stores.getLikeImageRes(like));
            }
            boolean admin=messages.getIsAdmin();
            if(admin){
                holder.users_like.setImageResource(R.drawable.admin);
            }
            Piccassa.load(context, messages.image, R.drawable.user_sample, holder.users_dp);
        }
    }

    private void setHolderOnClick(final ContactViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsItemClicked.onMsgCLicked(position);
            }
        });
        holder.users_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsItemClicked.onUsernameCLicked(position);
            }
        });
        holder.users_frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsItemClicked.onFriendCLicked(position);
                holder.users_frnd.setText(context.getString(R.string.elipsize));
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
