package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.NotifyViewHolder;
import ng.com.coursecode.piqmessenger.Database__.Notify;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Interfaces.NotifyItemClicked;
import ng.com.coursecode.piqmessenger.Model__.RecivData;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class NotifyAdapter extends RecyclerView.Adapter<NotifyViewHolder> {

    List<Notify> messages_list;
    Context context;
    boolean showFriendsIcon=false;
    NotifyItemClicked notifysItemClicked;

    public NotifyAdapter(List<Notify> messages_, NotifyItemClicked notify) {
        super();
        messages_list=messages_;
        notifysItemClicked=notify;
    }

    @Override
    public NotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.notif_layout, parent, false);
        return new NotifyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotifyViewHolder holder, int position) {
        if(messages_list.size()>0){
            Notify messages=messages_list.get(position);
            String typee=messages.getType();
            String username=messages.getSubj();
            String time=messages.getTime_stamp();
            Users_prof users_prof=Users_prof.getInfo(context, username);

            String fullnm=users_prof.getFullname();
            RecivData stringset=Stores.getRecivNotif(typee);
            int resString=stringset.getResString();
            if(resString!=0){
                holder.notif_text.setText(context.getString(resString, fullnm));
                setHolderOnClick(holder);
            }
            Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, holder.users_dp);
        }
    }

    private void setHolderOnClick(final NotifyViewHolder holder) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                notifysItemClicked.onNotifyCLicked(position);
            }
        });
        holder.users_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                notifysItemClicked.onUsernameCLicked(position);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=holder.getAdapterPosition();
                notifysItemClicked.onDeleteCLicked(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
