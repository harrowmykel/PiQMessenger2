package ng.com.coursecode.piqmessenger.adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ng.com.coursecode.piqmessenger.adapters__.ViewHolders.ConvoActViewHolder;
import ng.com.coursecode.piqmessenger.conversate.Converse;
import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class ConvoActAdapter extends RecyclerView.Adapter<ConvoActViewHolder> {

    List<Messages> messages_list;
    Context context;
    SendDatum convoInterface;
    String thisUser="", other_user;
    private int RECIP=2212;
    private int AUTH=23213;
    boolean former_unread=false;
    String former_day="asd";
    TimeModel timeModel;

    public ConvoActAdapter(List<Messages> messages_, Context con, SendDatum convoInterface1, String other_user1) {
        super();
        context=con;
        messages_list=messages_;
        convoInterface=convoInterface1;
        if(thisUser.isEmpty()) {
            thisUser = (new Stores(context)).getUsername();
        }
        timeModel=new TimeModel(context);
        other_user=other_user1;
    }

    @Override
    public ConvoActViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v;
        if(viewType==RECIP){
            v= LayoutInflater.from(context).inflate(R.layout.convo_act_layout_reciv, parent, false);
        }else{
            v= LayoutInflater.from(context).inflate(R.layout.convo_act_layout, parent, false);
        }
        return new ConvoActViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ConvoActViewHolder holder, int position) {
        if(messages_list.size()>0){
            Messages messages=messages_list.get(position);
            String time_msg=timeModel.getDWMTime(""+messages.getTim_e());
            boolean unread=messages.isUnread();

            holder.convo_subtitle.setText(time_msg);
            holder.convo_username.setText(Stores.ucWords(messages.getMess_age()));
            String img=messages.getImage();
            if(!img.isEmpty() && !img.equals("0") && !img.equals("1")) {
                holder.convo_dp.setVisibility(View.VISIBLE);
                Piccassa.load(context, img, R.drawable.nosong, holder.convo_dp);
            }else{
                holder.convo_dp.setVisibility(View.GONE);
            }

            String former_=timeModel.getDWMDay(""+messages.getTim_e());
            holder.convo_date.setText(former_);

            String auth = messages.getAuth();
            boolean userIsAuth=false;
            if (auth.equalsIgnoreCase(thisUser)) {
                userIsAuth=true;
            }

            if(position!=0){
                Messages messg1=messages_list.get(position-1);
                former_day=timeModel.getDWMDay(""+messg1.getTim_e());
                former_unread=messg1.isUnread();
            }else{
                former_day="asd";
                former_unread=false;
            }

            if(userIsAuth){
                if(messages.isRcvd()){
                    holder.convo_icon.setImageResource(R.drawable.msg_status_client_received);
                }else if(messages.isRead()){
                    holder.convo_icon.setImageResource(R.drawable.msg_status_client_read);
                }else if(messages.isUnread()){
                    holder.convo_icon.setImageResource(R.drawable.msg_status_server_receive);
                }else{
                    holder.convo_icon.setImageResource(R.drawable.msg_status_gray_waiting_2);
                }
            }
            boolean show_unread = !userIsAuth && unread && !former_unread;
            if (show_unread) {
                holder.crdview_unread.setVisibility(View.VISIBLE);
                holder.txtView_unread.setVisibility(View.VISIBLE);
                former_unread = true;
            } else {
                holder.crdview_unread.setVisibility(View.GONE);
                holder.txtView_unread.setVisibility(View.GONE);
            }

            if(!former_.equalsIgnoreCase(former_day)){
                visibilize(holder, true);
            }else {
                visibilize(holder, false);
            }

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View vieww=holder.crdview;
                    boolean show=(vieww.getVisibility()!=View.VISIBLE);
                    visibilize(holder, show);
                }
            });
            holder.convo_dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View vieww=holder.crdview;
                    boolean show=(vieww.getVisibility()!=View.VISIBLE);
                    visibilize(holder, show);
                }
            });

            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int posit=holder.getAdapterPosition();
                    convoInterface.send(new int[]{Converse.LONG_CLICK, posit});
                    return false;
                }
            });
        }
    }

    private void visibilize(ConvoActViewHolder holder, boolean show) {
        if(show) {
            holder.crdview.setVisibility(View.VISIBLE);
            holder.convo_date.setVisibility(View.VISIBLE);
        }else{
            holder.crdview.setVisibility(View.GONE);
            holder.convo_date.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(messages_list.size()>0) {
            Messages messages = messages_list.get(position);
            String auth = messages.getAuth();
            if (!auth.equalsIgnoreCase(thisUser)) {
                return RECIP;
            }else{
                return AUTH;
            }
        }
        return super.getItemViewType(position);
    }
}
