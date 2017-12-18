package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.StatusViewHolderRecom;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Fragments_.StatusFragment;
import ng.com.coursecode.piqmessenger.Interfaces.StatusClicked;
import ng.com.coursecode.piqmessenger.Model__. Model__3;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class StatusAdapterRecommended extends RecyclerView.Adapter<StatusViewHolderRecom> {

    ArrayList< Model__3> messages_list;
    Context context;
    StatusClicked statusClicked;
    String statType;
    int position1;
    boolean has_not;
    int dele= 1;

    public StatusAdapterRecommended(ArrayList< Model__3> messagesseen, StatusClicked statusClicked1) {
        super();
        messages_list=messagesseen;
        statusClicked=statusClicked1;
        has_not=!Prefs.getBoolean(StatusFragment.HAS_SEEN_DEF_STAT, false);
    }

    public StatusAdapterRecommended(Context context, ArrayList< Model__3> messagesseen, StatusClicked statusClicked1) {
        super();
        messages_list=messagesseen;
        statusClicked=statusClicked1;
        has_not=!Prefs.getBoolean(StatusFragment.HAS_SEEN_DEF_STAT, false);
        this.context=context;
        dele=(has_not)?2:1;
    }

    @Override
    public StatusViewHolderRecom onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.status_layout_recom, parent, false);
        return new StatusViewHolderRecom(v);
    }

    @Override
    public void onBindViewHolder(final StatusViewHolderRecom holder, int position) {
        if(position==0){
            Piccassa.load(context, R.drawable.profile_tab, holder.status_dp);
            statType=Status_tab.CREATE;
        }else if(has_not && position==1) {
            Piccassa.load(context, R.drawable.profile_btn_superlike, holder.status_dp);
            statType=Status_tab.INTRO;
        }else{
            if (messages_list.size() > 0) {
                Model__3 users_posts = messages_list.get(position-dele);
                String image_to_load=null, user_dp=null;
                statType=Status_tab.FAV;

                ArrayList< Model__3> status_tabs = users_posts.getStatData();
                int num_of_status_tabs = status_tabs.size();

                if (num_of_status_tabs > 0) {
                    Model__3 messages = status_tabs.get(num_of_status_tabs - 1);
                    image_to_load = user_dp = messages.getImage();
                }

                if(image_to_load==null || image_to_load.isEmpty()){
                    image_to_load = users_posts.getUser_img();
                }

                if(context.getString(R.string.app_name).equalsIgnoreCase(""+users_posts.getUsername())){
                    Piccassa.load(context, R.drawable.profile_btn_superlike, holder.status_dp);
                }else {
                    Piccassa.loadStatus(context, image_to_load, user_dp, holder.status_dp);
                }
                int valueInPixels;

                if (!users_posts.getSeen()) {
                    holder.status_dp.setBorderColor(ContextCompat.getColor(context, R.color.border_color_));
                    valueInPixels = (int) context.getResources().getDimension(R.dimen.border_width);
                    holder.status_dp.setBorderWidth(valueInPixels);
                } else {
                    valueInPixels = (int) context.getResources().getDimension(R.dimen.zero);
                    holder.status_dp.setBorderWidth(valueInPixels);
                }

            }
        }
        holder.status_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1=holder.getAdapterPosition();
                if(position1<1){
                    statType=Status_tab.CREATE;
                }else if(has_not && position1==1) {
                    statType=Status_tab.INTRO;
                }else{
                    statType=Status_tab.FAV;
                    position1=position1-dele;
                }
                statusClicked.onStatClicked(position1, statType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages_list.size() +dele;
    }
}
