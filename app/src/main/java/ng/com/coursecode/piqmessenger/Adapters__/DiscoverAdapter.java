package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ng.com.coursecode.piqmessenger.Adapters__.ViewHolders.DiscoverViewHolder;
import ng.com.coursecode.piqmessenger.Database__.Discover_tab;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 12/10/2017.
 */

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverViewHolder> {

    List<Discover_tab> messages_list;
    Context context;

    public DiscoverAdapter(List<Discover_tab> messages_) {
        super();
        messages_list=messages_;
    }

    @Override
    public DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.discover_layout, parent, false);
        return new DiscoverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DiscoverViewHolder holder, int position) {
        if(messages_list.size()>0){
            Discover_tab messages=messages_list.get(position);
            holder.discover_subtitle.setText((new TimeModel(context)).getDWM3(messages.getTime()));
            holder.discover_username.setText(messages.getUser_name());

            Piccassa.load(context, messages.image, R.drawable.user_sample, holder.discover_dp);
            Piccassa.load(context, messages.image, R.drawable.post_sample, holder.discover_image);
        }
    }

    @Override
    public int getItemCount() {
        return 4;//messages_list.size();
    }
}
