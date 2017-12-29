package ng.com.coursecode.piqmessenger.statuses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pixplicity.easyprefs.library.Prefs;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.adapters__.SectionsPagerAdapter;
import ng.com.coursecode.piqmessenger.fragments_.Status;
import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.networkcalls.StatusCall;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.searches.SearchAct;

public class StatusAll extends PiccMaqCompatActivity {

    FrameLayout frameLayout;
    Toolbar toolbar;
    Boolean fromSavedState=false;
    public String cc_title;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_frg_holder);
        context=StatusAll.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.cont_framecontent);
        cc_title=getString(R.string.status);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateStatus.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new StatusCall(context)).getAllDelMessages();
                Intent intent = new Intent(Stores.REFRESH_ACTIVITY_STATUS);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        (new StatusCall(context)).getAllDelMessages();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startFragmentTransactions();
        setTitle(cc_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onOptionsMenuClosed(menu);
        getMenuInflater().inflate(R.menu.menu_search_act, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search://todo here
                Intent intent=new Intent(context, SearchAct.class);
                intent.putExtra(Stores.TYPE_OF_ACTION, R.string.status);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startFragmentTransactions() {
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.cont_framecontent) != null) {
            try {
                Status firstFragment = Status.newInstance();
                if (fromSavedState) {
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.cont_framecontent, firstFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.cont_framecontent, firstFragment).commit();
                }
                fromSavedState = true;
            } catch (Exception f) {
                Toasta.makeText(this, f.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }
}