package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 02/12/2017.
 */

public class ListAdapter_ extends RecyclerView.Adapter<ListAdapter_.ListHolder>{

    String[] strArray;
    Context context;
    ArrayList<Integer> intArrAsString1;
    int[] intArrImg2;
    SendDatum sendDatum_;
    int posit, total;

    public ListAdapter_(Context context1, ArrayList<Integer> intArrAsString, SendDatum sendDatum_1) {
        super();
        intArrAsString1=intArrAsString;
        context=context1;
        sendDatum_=sendDatum_1;
        total=intArrAsString1.size();
    }

    public ListAdapter_(Context context1, ArrayList<Integer> intArrAsString, int[] list1List3img, SendDatum sendDatum_1) {
        super();
        intArrAsString1=intArrAsString;
        intArrImg2=list1List3img;
        context=context1;
        sendDatum_=sendDatum_1;
        total=intArrAsString1.size();
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.menu_layout, parent, false);
        return new ListHolder(v);
    }

    @Override
    public int getItemCount() {
        return total;
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.objectName.setText(context.getString(intArrAsString1.get(position)));
//        holder.img.setImageResource(intArrImg2[position]);
        holder.itemView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posit=holder.getAdapterPosition();
                        sendDatum_.send(intArrAsString1.get(posit));
                    }
                });
        if(position==(total-1)){
            holder.divider.setVisibility(View.GONE);
        }else{
            holder.divider.setVisibility(View.VISIBLE);
        }
    }

    class ListHolder extends RecyclerView.ViewHolder{
        TextView objectName;
        View itemView1, divider;
        ImageView img;
        ListHolder(View itemView) {
            super(itemView);
            objectName=((TextView)itemView.findViewById(R.id.menu_subtitle));
            divider=(itemView.findViewById(R.id.menu_divider));
//            img=((ImageView)itemView.findViewById(R.id.menu_dp));
            itemView1=itemView;
        }
    }
}
