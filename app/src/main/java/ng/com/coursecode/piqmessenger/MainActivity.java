package ng.com.coursecode.piqmessenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import ng.com.coursecode.piqmessenger.dialog_.LearnDialog;
import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.adapters__.SectionsPagerAdapter;
import ng.com.coursecode.piqmessenger.groups.CreateGroup;
import ng.com.coursecode.piqmessenger.mmenu.Menu_;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.searches.ConvoSearchAct;
import ng.com.coursecode.piqmessenger.searches.SearchAct;
import ng.com.coursecode.piqmessenger.statuses.CreatePost;
import ng.com.coursecode.piqmessenger.statuses.CreateStatus;
import ng.com.coursecode.piqmessenger.statuses.StatusAll;

public class MainActivity extends PiccMaqCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Context context;

    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), context);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        (findViewById(R.id.menu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Menu_.class));
            }
        });

        int goTo=0;
        int whereTo=getIntent().getIntExtra(Stores.TYPE_OF_ACTION, R.string.posts);
        int[] scv=SectionsPagerAdapter.fragmentTitles;
        for(int scvT : scv){
            if(whereTo==scvT){
                break;
            }
            goTo++;
        }
        mViewPager.setCurrentItem(goTo);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int curr) {
                setTitle(SectionsPagerAdapter.fragmentTitles[curr]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curr=mViewPager.getCurrentItem();
                int sect=SectionsPagerAdapter.fragmentTitles[curr];
                Intent intent;
                switch (sect){
                    case R.string.posts:
                        intent=new Intent(context, CreatePost.class);
                        break;
                    case R.string.chats:
                        intent=new Intent(context, SearchAct.class);
                        break;
                    case R.string.status:
                        intent=new Intent(context, CreateStatus.class);
                        break;
                    case R.string.groups:
                        intent=(new Intent(context, CreateGroup.class));
                        break;
                    default:
                        intent=new Intent(context, SearchAct.class);
                        break;
                }
                intent.putExtra(Stores.CurrentPage, curr);
                intent.putExtra(Stores.TYPE_OF_ACTION, sect);
                startActivity(intent);
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int curr=mViewPager.getCurrentItem();
                int sdf=SectionsPagerAdapter.fragmentTitles[curr];
                Intent intent = new Intent(Stores.REFRESH_ACTIVITY);
                if(sdf==R.string.groups){
                    intent = new Intent(Stores.REFRESH_ACTIVITY_GROUP);
                }else if (sdf==R.string.status){
                    intent = new Intent(Stores.REFRESH_ACTIVITY_STATUS);
                }else if (sdf==R.string.posts){
                    intent = new Intent(Stores.REFRESH_ACTIVITY_POST);
                }
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setRefreshing(false);
        if(!Prefs.getBoolean(LearnDialog.DONT_SHOW_LEARN_HOW, false)){
            Snackbar.make(mViewPager, R.string.learn_how_to_use, Snackbar.LENGTH_SHORT).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (new LearnDialog(context)).show();
                }
            });
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int curr=mViewPager.getCurrentItem();
                int sdf=SectionsPagerAdapter.fragmentTitles[curr];
                int fabDrwbl=R.drawable.ic_add_white_24dp;
                if(sdf==R.string.groups){
                    fabDrwbl=R.drawable.ic_group_add_white_24dp;
                }else if (sdf==R.string.status){
                    fabDrwbl=R.drawable.ic_edit_white_24dp;
                }else if (sdf==R.string.posts){
                    fabDrwbl=R.drawable.ic_add_white_24dp;
                }
                fab.setIcon(fabDrwbl);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;//Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_search:
                int curr=mViewPager.getCurrentItem();
                int sect=SectionsPagerAdapter.fragmentTitles[curr];
                intent=new Intent(this, SearchAct.class);
                intent.putExtra(Stores.CurrentPage, curr);
                intent.putExtra(Stores.TYPE_OF_ACTION, sect);
                startActivity(intent);
                return true;
            case R.id.action_more:
                intent=new Intent(this, ConvoSearchAct.class);
                startActivity(intent);
                return true;
            case R.id.action_status:
                startActivity(new Intent(context, StatusAll.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem()!=0){
            mViewPager.setCurrentItem(0, true);
        }else{
            super.onBackPressed();
        }
    }
}
