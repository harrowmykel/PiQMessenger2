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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Interfaces.SendDatum;

/**
 * Created by harro on 02/12/2017.
 */

public class ListAdapter_ extends RecyclerView.Adapter<ListAdapter_.ListHolder>{

    String[] strArray;
    Context context;
    ArrayList<Integer> intArrAsString1;
    SendDatum sendDatum_;
    int posit;

    public ListAdapter_(Context context1, ArrayList<Integer> intArrAsString) {
        super();
        intArrAsString1=intArrAsString;
        context=context1;
    }

    public ListAdapter_(Context context1, ArrayList<Integer> intArrAsString, SendDatum sendDatum_1) {
        super();
        intArrAsString1=intArrAsString;
        context=context1;
        sendDatum_=sendDatum_1;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ListHolder(v);
    }

    @Override
    public int getItemCount() {
        return intArrAsString1.size();
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, int position) {
        holder.objectName.setText(context.getString(intArrAsString1.get(position)));
        holder.itemView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posit=holder.getAdapterPosition();
                        sendDatum_.send(intArrAsString1.get(posit));
                        Toast.makeText(context, ""+context.getString(intArrAsString1.get(posit)), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class ListHolder extends RecyclerView.ViewHolder{
        TextView objectName;
        View itemView1;
        ListHolder(View itemView) {
            super(itemView);
            objectName=((TextView)itemView.findViewById(android.R.id.text1));
            itemView1=itemView;
        }
    }
}
