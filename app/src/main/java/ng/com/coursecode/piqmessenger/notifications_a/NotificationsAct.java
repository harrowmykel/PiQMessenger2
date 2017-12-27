package ng.com.coursecode.piqmessenger.notifications_a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ng.com.coursecode.piqmessenger.adapters__.NotifyAdapter;
import ng.com.coursecode.piqmessenger.database__.Notify;
import ng.com.coursecode.piqmessenger.dialog_.DeleteDialog;
import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.interfaces.NotifyItemClicked;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.networkcalls.NotifyCall;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;

public class NotificationsAct extends PiccMaqCompatActivity {

    @BindView(R.id.delete_all)
    MaterialFancyButton deleteAll;
    @BindView(R.id.main_recycle)
    RecyclerView mainRecycle;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
Context context;
    public static String REFRESH_VIEW_STATUS="jdhfdkljsfbhbxkljbgk";
    boolean do_once=true;
    NotifyAdapter NotifyAdapter2;
    List<Notify> notify_list;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
        context=NotificationsAct.this;

        NotifyCall NotifyCall=new NotifyCall(context);
        NotifyCall.getAllNotifys();
        swipeRefresh.setRefreshing(true);
        
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NotifyCall NotifyCall=new NotifyCall(context);
                NotifyCall.getAllNotifys();
                swipeRefresh.setRefreshing(false);
            }
        });

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(REFRESH_VIEW_STATUS));

        refreshView();
    }
    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String action=intent.getAction();
            if(action.equalsIgnoreCase(REFRESH_VIEW_STATUS)) {
               refreshView();
            }
        }
    };

    private void refreshView() {        
        notify_list=(new Notify()).listAll(context);
        if(notify_list.size()==0){
            (findViewById(R.id.warning)).setVisibility(View.VISIBLE);
        }else{
            (findViewById(R.id.warning)).setVisibility(View.GONE);
        }
        if(!do_once){
            NotifyAdapter2 = new NotifyAdapter(notify_list, nNotifyInterface);
            mainRecycle.setAdapter(NotifyAdapter2);
            NotifyAdapter2.notifyDataSetChanged();
        }

        if(do_once) {

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            mainRecycle.setLayoutManager(mLayoutManager);
            mainRecycle.setItemAnimator(new DefaultItemAnimator());

            
            NotifyAdapter2 = new NotifyAdapter(notify_list, nNotifyInterface);
            mainRecycle.setAdapter(NotifyAdapter2);
            NotifyAdapter2.notifyDataSetChanged();
            do_once=false;

            if(Stores.flingEdit){
                mainRecycle.fling(Stores.flingVelX, Stores.flingVelY);
            }
        }
        swipeRefresh.setRefreshing(false);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((new DeleteDialog(context, new SendDatum() {
                    @Override
                    public void send(Object object) {
                        boolean cvc=(boolean)(object);
                        if(cvc) {
                            NotifyCall NotifyCall = new NotifyCall(context);
                            NotifyCall.clear();
                        }
                    }
                }))).show();
            }
        });
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(REFRESH_VIEW_STATUS));
        super.onResume();
    }

    NotifyItemClicked nNotifyInterface=new NotifyItemClicked() {
        @Override
        public void onUsernameCLicked(int position) {
            String msgId=notify_list.get(position).getSubj();
            Intent intent=new Intent(context, Profile.class);
            intent.putExtra(Profile.USERNAME, msgId);
            startActivity(intent);
        }

        @Override
        public void onNotifyCLicked(int position) {
            String msgId=notify_list.get(position).getType();
            String data=notify_list.get(position).getObj_id();
            Intent intent=Stores.getIntentNotif(context, msgId);
            intent.putExtra(PostsAct.POSTID, data);
            intent.putExtra(Profile.USERNAME, data);
            startActivity(intent);
        }

        @Override
        public void onDeleteCLicked(final int position) {
            DeleteDialog deleteDialog=new DeleteDialog(context, new SendDatum() {
                @Override
                public void send(Object object) {
                    boolean sdf=(boolean)object;
                    if(sdf){
                        String msgId=notify_list.get(position).getMsg_id();
                        (new Notify()).delete(context, msgId);
                        notify_list.remove(position);
                        refreshView();
                    }
                }
            });
            deleteDialog.show();
        }
    };

}
