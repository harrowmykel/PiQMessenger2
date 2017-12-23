package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;  import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Adapters__.StatusAdapter;
import ng.com.coursecode.piqmessenger.Adapters__.StatusAdapterRecommended;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqFragment;
import ng.com.coursecode.piqmessenger.Interfaces.StatusClicked;
import ng.com.coursecode.piqmessenger.Model__.Model__3;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.NetworkCalls.StatusCall;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Statuses.CreateStatus;
import ng.com.coursecode.piqmessenger.Statuses.Show_Status;

/**
 * Created by harro on 09/10/2017.
 */

public class Status extends PiccMaqFragment {
    private static final String OLD_INSTANCE = "ejnsf;ekln";
    private static final String OLD_BUNDLE = "JKlnwlk;dnlkdl;";
    public static int NOTIFICATION_ID=33344;
    View view;
    Context context;
    Stores stores;
    RecyclerView recyclerView, seenrecyclerView, recommendedrecyclerView;
    String query="";
    Bundle savedInstance;
    Bundle oldsavedInstanceState;

    ArrayList<Model__3> messages=new ArrayList<>();
    ArrayList<Model__3> messagesrecommended=new ArrayList<>();
    ArrayList<Model__3> messagesseen=new ArrayList<>();
//    SwipeRefreshLayout swipeRefreshLayout;

    Status_tab status = new Status_tab();
    ArrayList<Model__3> statuses;
    boolean requestCreate=true;
    public static String REFRESH_VIEW_STATUS="refresh_status_view";

    public Status(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_status, container, false);
        context=getContext();
        stores=new Stores(context);
        status = new Status_tab();
        oldsavedInstanceState=savedInstanceState;
        recyclerView=(RecyclerView)view.findViewById(R.id.main_recycle);
        seenrecyclerView=(RecyclerView)view.findViewById(R.id.main_recycle1);
        recommendedrecyclerView=(RecyclerView)view.findViewById(R.id.main_recycle2);
        if(getArguments()!=null)
            query=(getArguments().getString(Stores.SearchQuery, ""));
        if(requestCreate)
            setLists();
        else
            setOLists();
        setMessageReceiver(mMessageReceiver);
        listenToBroadCast(REFRESH_VIEW_STATUS);
        listenToBroadCast(Stores.REFRESH_ACTIVITY_STATUS);
        return view;
    }

    private void setOLists() {
        messages = new ArrayList<>();
        messagesrecommended = new ArrayList<>();
        messagesseen = new ArrayList<>();

        if(query.isEmpty() && oldsavedInstanceState!=null){
            statuses=oldsavedInstanceState.getParcelableArrayList(OLD_INSTANCE);
            if(statuses!=null){
                setVlist();
                return;
            }
        }
        setLists();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(requestCreate)
            return;
        if(outState==null){
            outState=new Bundle();
        }
        outState.putParcelableArrayList(OLD_INSTANCE, statuses);
        super.onSaveInstanceState(outState);
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String action=intent.getAction();
            if(action.equalsIgnoreCase(REFRESH_VIEW_STATUS)) {
                if (!requestCreate) {
                    setOLists();
                } else {
                    setLists();
                }
            }else if(action.equalsIgnoreCase(Stores.REFRESH_ACTIVITY_STATUS)){
                StatusCall statusCall=new StatusCall(context);
                statusCall.getAllMessages();
            }
        }
    };

    public void setLists() {

        messages = new ArrayList<>();
        messagesrecommended = new ArrayList<>();
        messagesseen = new ArrayList<>();


        if (query.isEmpty()) {
            statuses = status.listAll(context);
        } else {
            statuses = status.listAllSearch(context, query);
        }
        setVlist();
    }

    public void setVlist(){

        Model__3 model__2;
        int num=statuses.size();

        for (int i=0; i<num; i++){
            model__2=statuses.get(i);
            if(model__2.isStatFav()){
                messagesrecommended.add(model__2);
            }else  if(model__2.getSeen()){
                messagesseen.add(model__2);
            }else{
                messages.add(model__2);
            }
        }

        StatusAdapter statusAdapter=new StatusAdapter(messages, Status_tab.UNSEEN, statusClicked);
        StatusAdapter statusAdapterseen=new StatusAdapter(messagesseen, Status_tab.SEEN, statusClicked);
        StatusAdapterRecommended statusAdapterrecommended=new StatusAdapterRecommended(context, messagesrecommended, statusClicked);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        RecyclerView.LayoutManager mLayoutManagerseen = new LinearLayoutManager(context);
        RecyclerView.LayoutManager mHoriLazout=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        seenrecyclerView.setLayoutManager(mLayoutManagerseen);
        recommendedrecyclerView.setLayoutManager(mHoriLazout);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        seenrecyclerView.setItemAnimator(new DefaultItemAnimator());
        recommendedrecyclerView.setItemAnimator(new DefaultItemAnimator());


        if(Stores.flingEdit){
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);
            seenrecyclerView.fling(Stores.flingVelX, Stores.flingVelY);
            recommendedrecyclerView.fling(Stores.flingVelX, Stores.flingVelY);
        }

        recyclerView.setAdapter(statusAdapter);
        seenrecyclerView.setAdapter(statusAdapterseen);
        recommendedrecyclerView.setAdapter(statusAdapterrecommended);

        statusAdapter.notifyDataSetChanged();
        statusAdapterseen.notifyDataSetChanged();
        statusAdapterrecommended.notifyDataSetChanged();
    }

    public static Status newInstance(){
        return  new Status();
    }

    public static Status newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Status chat=new Status();
        chat.setArguments(bundle);
        return chat;
    }

    StatusClicked statusClicked=new StatusClicked() {
        @Override
        public void onStatClicked(int position, String typer) {
            Intent intent=new Intent(context, Show_Status.class);
            switch (typer){
                case Status_tab.CREATE:
                    intent=new Intent(context, CreateStatus.class);
                    break;
                case Status_tab.FAV:
                    intent.putExtra(Show_Status.DATA_, (messagesrecommended));
                    break;
                case Status_tab.INTRO:
                    ArrayList<Model__3> messagesintro=(new Status_tab()).fetchIntro(context);
                    intent.putExtra(Show_Status.DATA_, (messagesintro));
                    break;
                case Status_tab.UNSEEN:
                    intent.putExtra(Show_Status.DATA_, (messages));
                    break;
                case Status_tab.SEEN:
                default:
                    intent.putExtra(Show_Status.DATA_, (messagesseen));
                    break;
            }
//            intent.putExtra(Show_Status.DATA_, statuses);
            intent.putExtra(Show_Status.TYPE_, typer);
            intent.putExtra(Show_Status.POSITION, position);

            startActivity(intent);
        }
    };
}
