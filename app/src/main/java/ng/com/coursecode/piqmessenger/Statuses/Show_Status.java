package ng.com.coursecode.piqmessenger.Statuses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Adapters__.StatusPagerAdapter;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Model__3;
import ng.com.coursecode.piqmessenger.R;

public class Show_Status extends AppCompatActivity {

    public static final String TYPE_ = "HJkwsdjknjkfsj";
    public static final String POSITION = "kejwdasnrfznkfl";
    public static final String NEXT_STATUS = "jdkjfsnkj";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private StatusPagerAdapter mStatusPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final String DATA_="jhknbvg vhjjh";
    ArrayList<Model__3> data=new ArrayList<>();
    String type_= Status_tab.SEEN;
    int position_=0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__status);
        context=Show_Status.this;
        Intent intent=getIntent();
        if(intent!=null){
            data=intent.getParcelableArrayListExtra(DATA_);
            type_=intent.getStringExtra(TYPE_);
            position_=intent.getIntExtra(POSITION, 0);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mStatusPagerAdapter = new StatusPagerAdapter(getSupportFragmentManager(), data, type_, position_, context);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mStatusPagerAdapter);
        mViewPager.setCurrentItem(position_, false);
        if(data.size()<1){
            finir();
        }

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(NEXT_STATUS));
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int position=mViewPager.getCurrentItem();
            int max=data.size();
            position++;
            if(position<max && position>-1){
                mViewPager.setCurrentItem(position, true);
            }else{
                finir();
            }
        }
    };

    public  void finir(){
        finish();
    }
}
