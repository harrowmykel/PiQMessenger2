package ng.com.coursecode.piqmessenger.adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.interfaces.ConvoInterface;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.adapters__.ViewHolders.ConvoViewHolder;

/**
 * Created by harro on 12/10/2017.
 */

public class ConvoAdapter extends RecyclerView.Adapter<ConvoViewHolder> {

    List<Messages> messages_list;
    Context context;
    ConvoInterface convoInterface;

    public ConvoAdapter(List<Messages> messages_, ConvoInterface convoInterface1) {
        super();
        messages_list=messages_;
        convoInterface=convoInterface1;
    }

    @Override
    public ConvoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.convo_layout, parent, false);
        return new ConvoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ConvoViewHolder holder, int position) {
        if(messages_list.size()>0){
            Messages messages=messages_list.get(position);
            String msgh=messages.getMess_age();
            if(msgh.trim().isEmpty()){
                int unicode = 0x1F4F7;
                msgh=Stores.getEmojiByUnicode(unicode);
            }
            holder.convo_subtitle.setText(msgh);
            holder.convo_username.setText(Stores.ucWords(messages.getFullname()));
            holder.convo_time.setText((new TimeModel(context)).getDWM3(""+messages.getTim_e()));
            if(!messages.image.isEmpty()) {
                Piccassa.loadDp(context, messages.image, holder.convo_dp);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pok=holder.getAdapterPosition();
                    convoInterface.startConvoAct(pok);
                }
            });
            holder.convo_dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pok=holder.getAdapterPosition();
                    convoInterface.startProfileAct(pok);
                }
            });
            boolean thrown=messages.getConfirm().equalsIgnoreCase(Stores.UNREAD_MSG);
            Stores.addCircleBorderMsg(holder.convo_dp, context, thrown);
        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
