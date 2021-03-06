package ng.com.coursecode.piqmessenger.statuses;

import android.content.Context;
import android.content.Intent;

import icepick.Icepick;
import icepick.State;
import ng.com.coursecode.piqmessenger.extLib.FullScreenActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.adapters__.StatusPagerAdapter;
import ng.com.coursecode.piqmessenger.database__.Status_tab;
import ng.com.coursecode.piqmessenger.fragments_.StatusFragment;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.model__.Model__3;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.networkcalls.StatusCall;
import ng.com.coursecode.piqmessenger.R;

public class Show_Status extends FullScreenActivity implements SendDatum {

    public static final String TYPE_ = "HJkwsdjknjkfsj";
    public static final String POSITION = "kejwdasnrfznkfl";
    public static final String NEXT_STATUS = "jdkjfsnkj";
    public static final String FULLSCREEN = "jfksnkldgkf";
    public static final String VIEW_USER = "Jeejej";
    public static final String DELETE = "jnfdlfdn";
    public static String FINISH_="jkh;jlkjl";
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
    Stores stores;

    @State
    ArrayList<String> sent_updates;
    @State
    ArrayList<String> unsent_updates; // This will be automatically saved and restored

    ArrayList<String> all_updates;

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_show__status);
        context=Show_Status.this;
        stores=new Stores(context);
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
    }

    public  void finir(){
        finish();
    }

    @Override
    public void send(Object object) {
        String obj=(String) object;
        if(obj.equalsIgnoreCase(NEXT_STATUS)){
            int position=mViewPager.getCurrentItem();
            int max=data.size();
            position++;
            if(position<max && position>-1){
                mViewPager.setCurrentItem(position, true);
            }else{
                finir();
            }
        }else if(obj.equalsIgnoreCase(FULLSCREEN)){
            setFullscreen();
        }else if(obj.equalsIgnoreCase(FINISH_)){
            finir();
        }else{
            //status code
            if(Stores.DEFAULT_STAT.equalsIgnoreCase(obj)){
                Prefs.putBoolean(StatusFragment.HAS_SEEN_DEF_STAT, true);
                (new Status_tab()).addIntro(context, true);
            }
            if(sent_updates==null){
                sent_updates=new ArrayList<>();
            }
            if(unsent_updates==null){
                unsent_updates=new ArrayList<>();
            }
            if(all_updates==null){
                all_updates=new ArrayList<>();
            }

            if(!sent_updates.contains(obj)){
                sendToServer(obj);
            }
        }
    }

    public void sendToServer(final String type){
        unsent_updates.add(type);
        all_updates.add(type);
    }

    @Override
    protected void onStop() {
        StatusCall statusCall=new StatusCall(context);
        statusCall.hasViewed(all_updates);
        super.onStop();
    }
}
