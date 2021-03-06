package ng.com.coursecode.piqmessenger.adapters__;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.database__.Status_tab;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.interfaces.StatusClicked;
import ng.com.coursecode.piqmessenger.model__.Model__3;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.adapters__.ViewHolders.StatusViewHolder;

/**
 * Created by harro on 12/10/2017.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {

    ArrayList< Model__3> messages_list;
    Context context;
    String statType="";
    StatusClicked statusClicked;

    public StatusAdapter(ArrayList<Model__3> messagesseen, String seen, StatusClicked statusClicked1) {
        super();
        messages_list=messagesseen;
        statType=seen;
        statusClicked=statusClicked1;
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.status_layout, parent, false);
        return new StatusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final StatusViewHolder holder, int position) {
        if(messages_list.size()>0){
            Model__3 users_posts=messages_list.get(position);
            String image_to_load, user_dp, time;
            String cv="@"+ users_posts.getUsername();
            holder.status_username.setText(Stores.ucWords(users_posts.getFullname()));
            holder.status_subtitle.setText(cv);

            ArrayList<Model__3> status_tabs=users_posts.getStatData();
            int num_of_status_tabs=status_tabs.size();

            image_to_load=user_dp=users_posts.getUser_img();

            if(num_of_status_tabs>0){
                Model__3 messages=status_tabs.get(num_of_status_tabs-1);
                image_to_load=messages.getImage();
                time=(new TimeModel(context)).getDWM3(messages.getTime());
            }else{
                time="0";
            }

            if(image_to_load==null || image_to_load.isEmpty()){
                image_to_load = users_posts.getUser_img();
            }

            holder.status_time.setText(time);
            int valueInPixels;

            Piccassa.loadStatus(context, image_to_load, user_dp, holder.status_dp);
            if(statType.equals(Status_tab.UNSEEN)){
                holder.status_username.setTypeface(Typeface.DEFAULT_BOLD);
                Stores.addCircleBorder(holder.status_dp, context, true);
            }else{
                Stores.addCircleBorder(holder.status_dp, context, false);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positiona=holder.getAdapterPosition();
                    statusClicked.onStatClicked(positiona, statType);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messages_list.size();
    }
}
