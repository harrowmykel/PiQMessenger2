package ng.com.coursecode.piqmessenger.adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.adapters__.ViewHolders.StatusViewHolderRecom;
import ng.com.coursecode.piqmessenger.database__.Status_tab;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.fragments_.StatusFragment;
import ng.com.coursecode.piqmessenger.interfaces.StatusClicked;
import ng.com.coursecode.piqmessenger.model__. Model__3;
import ng.com.coursecode.piqmessenger.model__.Stores;
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
                Stores.addCircleBorder(holder.status_dp, context, !users_posts.getSeen());
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
