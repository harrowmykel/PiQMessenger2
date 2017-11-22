package ng.com.coursecode.piqmessenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ng.com.coursecode.piqmessenger.Adapters__.SectionsPagerAdapter;
import ng.com.coursecode.piqmessenger.Contacts_.ContactAct;
import ng.com.coursecode.piqmessenger.Groupss.JoinGroups;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Searches.ConvoSearchAct;
import ng.com.coursecode.piqmessenger.Searches.SearchAct;
import ng.com.coursecode.piqmessenger.Statuses.CreatePost;
import ng.com.coursecode.piqmessenger.Statuses.CreateStatus;

public class MainActivity extends AppCompatActivity {

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                        intent=new Intent(context, JoinGroups.class);
                        break;
                    default:
                        intent=new Intent(context, SearchAct.class);
                        break;
                }
                intent.putExtra(Stores.CurrentPage, curr);
                startActivity(intent);
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
                intent=new Intent(this, SearchAct.class);
                intent.putExtra(Stores.CurrentPage, curr);
                startActivity(intent);
                return true;
            case R.id.action_more:
                intent=new Intent(this, ConvoSearchAct.class);
                startActivity(intent);
                return true;
            case R.id.action_people:
                startActivity(new Intent(context, ContactAct.class));
                 return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
