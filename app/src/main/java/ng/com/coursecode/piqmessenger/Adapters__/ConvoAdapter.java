package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Interfaces.ConvoInterface;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.ConvoViewHolder;

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
    public void onBindViewHolder(ConvoViewHolder holder, final int position) {
        if(messages_list.size()>0){
            Messages messages=messages_list.get(position);
            holder.convo_subtitle.setText(messages.getMess_age());
            holder.convo_username.setText(Stores.ucWords(messages.getFullname()));
            holder.convo_time.setText((new TimeModel(context)).getDWM3(messages.getTim_e()));
            if(!messages.image.isEmpty()) {
                Piccassa.load(context, messages.image, R.drawable.user_sample, holder.convo_dp);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convoInterface.startConvoAct(position);
                }
            });
            holder.convo_dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    convoInterface.startProfileAct(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
