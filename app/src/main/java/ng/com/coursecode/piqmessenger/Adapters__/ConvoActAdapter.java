package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.ConvoActViewHolder;
import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.ConvoViewHolder;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ConvoInterface;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class ConvoActAdapter extends RecyclerView.Adapter<ConvoActViewHolder> {

    List<Messages> messages_list;
    Context context;
    ConvoInterface convoInterface;
    String thisUser="";
    private int RECIP=2212;
    private int AUTH=23213;
    String former_day="";
    TimeModel timeModel;

    public ConvoActAdapter(List<Messages> messages_, Context con, ConvoInterface convoInterface1) {
        super();
        context=con;
        messages_list=messages_;
        convoInterface=convoInterface1;
        if(thisUser.isEmpty()) {
            thisUser = (new Stores(context)).getUsername();
        }
        timeModel=new TimeModel(context);
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
    public void onBindViewHolder(ConvoActViewHolder holder, final int position) {
        if(messages_list.size()>0){
            Messages messages=messages_list.get(position);
            String former_=timeModel.getDWMDay(""+messages.getTim_e());
            String time_msg=timeModel.getDWMTime(""+messages.getTim_e());

            if(!former_.equalsIgnoreCase(former_day)){
                holder.crdview.setVisibility(View.VISIBLE);
                holder.convo_date.setVisibility(View.VISIBLE);
                holder.convo_date.setText(former_);
                former_day=former_;
            }else {
                holder.crdview.setVisibility(View.GONE);
                holder.convo_date.setVisibility(View.GONE);
            }
            holder.convo_subtitle.setText(time_msg);
            holder.convo_username.setText(Stores.ucWords(messages.getMess_age()));
            String img=messages.image;
            if(!img.isEmpty() && !img.equals("0") && !img.equals("1")) {
                Piccassa.load(context, img, R.drawable.nosong, holder.convo_dp);
            }
/*TODO
            boolean thrown=messages.getConfirm().equalsIgnoreCase(Stores.UNREAD_MSG);
            Stores.addCircleBorder(holder.convo_dp, context, thrown);*/
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
