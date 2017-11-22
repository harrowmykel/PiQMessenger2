package ng.com.coursecode.piqmessenger.Fragments_;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.coursecode.piqmessenger.Contacts_.ContactLists;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 09/10/2017.
 */

public class Notification extends Fragment {
    View view;
    public Notification(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_notif, container, false);
        /* if(moreCanBeLoaded){
                    setLists();
                }else {
                    CircularProgressBar circularProgressBar;
                    circularProgressBar=(CircularProgressBar)view.findViewById(R.id.progress_main);
                    circularProgressBar.setVisibility(View.GONE);
                }*/
        return view;
    }

    public static Notification newInstance(){
        return  new Notification();
    }

    public static  Notification newInstance(String query){
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Notification notification=new Notification();
        notification.setArguments(bundle);
        return notification;
    }
}
