package ng.com.coursecode.piqmessenger.NotificationsA;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ng.com.coursecode.piqmessenger.Adapters__.NotifyAdapter;
import ng.com.coursecode.piqmessenger.Database__.Notify;
import ng.com.coursecode.piqmessenger.Interfaces.NotifyItemClicked;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.NetworkCalls.NotifyCall;
import ng.com.coursecode.piqmessenger.R;

public class NotificationsAct extends AppCompatActivity {

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
        
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NotifyCall NotifyCall=new NotifyCall(context);
                NotifyCall.getAllNotifys();
            }
        });

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(REFRESH_VIEW_STATUS));
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
        if(!do_once){
            NotifyAdapter2 = new NotifyAdapter(notify_list, nNotifyInterface);
            mainRecycle.setAdapter(NotifyAdapter2);
            NotifyAdapter2.notifyDataSetChanged();
        }

        if(do_once) {

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            mainRecycle.setLayoutManager(mLayoutManager);
            mainRecycle.setItemAnimator(new DefaultItemAnimator());

            if(Stores.flingEdit){
                mainRecycle.fling(Stores.flingVelX, Stores.flingVelY);
            }
            
            NotifyAdapter2 = new NotifyAdapter(notify_list, nNotifyInterface);
            mainRecycle.setAdapter(NotifyAdapter2);
            NotifyAdapter2.notifyDataSetChanged();
            do_once=false;
        }
    }

    @OnClick({R.id.delete_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_all:
                NotifyCall NotifyCall=new NotifyCall(context);
                NotifyCall.clear();
                break;
        }
    }

    NotifyItemClicked nNotifyInterface=new NotifyItemClicked() {
        @Override
        public void onUsernameCLicked(int position) {

        }

        @Override
        public void onNotifyCLicked(int position) {

        }

        @Override
        public void onDeleteCLicked(int position) {

        }
    };

}
