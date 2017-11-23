package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.ConvoAdapter;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Interfaces.ConvoInterface;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 09/10/2017.
 */

public class Chats extends Fragment {
    View view;
    Context context;
    Stores stores;
    RecyclerView recyclerView;
    String  query="";
    int page=1;
    LinearLayoutManager mLayoutManager;
    List<Messages> messages_list=new ArrayList<>();
    boolean moreCanBeLoaded;
    public static String REFRESH_NEW_MESSAGE="refresh_new_messages";

    ConvoInterface convoInterface=new ConvoInterface() {
        @Override
        public void startConvoAct(int position) {
            String string=messages_list.get(position).getAuth();
            Intent intent=new Intent(context, Converse.class);
            intent.putExtra(Converse.USERNAME, string);
            startActivity(intent);
        }

        @Override
        public void startProfileAct(int position) {
            String username=messages_list.get(position).getAuth();
            Intent intent=new Intent(context, Profile.class);
            intent.putExtra(Profile.USERNAME, username);
            startActivity(intent);
        }
    };

    public Chats(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_chat, container, false);
        context=getContext();
        recyclerView=(RecyclerView)view.findViewById(R.id.main_recycle);
        stores=new Stores(context);
        setLists();

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(REFRESH_NEW_MESSAGE));
        return view;
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            setLists();
        }
    };

    public static Chats newInstance(){
        return  new Chats();
    }

    public static Chats newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Chats chat=new Chats();
        chat.setArguments(bundle);
        return chat;
    }

    public void setLists() {
        Messages messages = new Messages();
        int numg=messages_list.size();
        if (getArguments() != null)
            query = (getArguments().getString(Stores.SearchQuery, ""));
        if (query.isEmpty()) {
            messages_list = messages.listAll(context);
        } else {
            Model__2 model__=messages.search(context, query,  page);
            messages_list.addAll(model__.getDb_result());
            String any=model__.getPagesLeft();
            int pgLeft=Stores.parseInt(any);
            page++;
            moreCanBeLoaded=(pgLeft>0);
            if(!moreCanBeLoaded){
                closeLoader();
            }
            recyclerView.setHasFixedSize(true);
        }
        ConvoAdapter convoAdapter = new ConvoAdapter(messages_list, convoInterface);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        if(Stores.flingEdit)
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(convoAdapter);
        convoAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(numg);

        if (!query.isEmpty()) {
            recyclerView.addOnScrollListener(createInfiniteScrollListener());
        }else{
            closeLoader();
        }

        if(messages_list.size()<1){
            closeLoader();
        }

        closeLoader();
    }


    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                startLoader();
                if(moreCanBeLoaded){
                    setLists();
                }else {
                    closeLoader();
                }
            }
        };
    }

    public void closeLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)view.findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(View.GONE);
    }


    public void startLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)view.findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStart();
        smoothProgressBar.setVisibility(View.VISIBLE);
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }
}