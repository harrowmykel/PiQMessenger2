package ng.com.coursecode.piqmessenger.gif_replace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.ScaleImageView2;
import ng.com.coursecode.piqmessenger.interfaces.Gifselected;
import ng.com.coursecode.piqmessenger.R;

public class StaggeredAdapter extends ArrayAdapter<String> {

    int page;
    Context context_;
    Gifselected gifselected;

    public StaggeredAdapter(Context context, List<String> objects, int page_, Gifselected gifselected_) {
        super(context, R.layout.row_staggered_demo, objects);
        page=page_;
        context_=context;
        gifselected=gifselected_;
    }

    public StaggeredAdapter(Context context, List<String> messages, int page_) {
        super(context, R.layout.row_staggered_demo, messages);
        page=page_;
        context_=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(getContext());
            convertView = layoutInflator.inflate(R.layout.row_staggered_demo, null);
        }

        holder = new ViewHolder();
        holder.imageView = (ScaleImageView2) convertView.findViewById(R.id.imageView1);
        convertView.setTag(holder);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifselected.onGifSelected(position);
            }
        });
//        holder.img_src=(TextView)convertView.findViewById(R.id.img_src);
//        holder.img_src.setText(getItem(position));

        if(page==0){
            holder.imageView.setVisibility(View.VISIBLE);
            Piccassa.load(context_, getItem(position), R.drawable.nosong, holder.imageView);
        }else{
            Piccassa.loadGlide(context_, getItem(position), R.drawable.nosong, holder.imageView);
        }
        return convertView;
    }

    static class ViewHolder {
        ScaleImageView2 imageView;
        TextView img_src;

    }
}
