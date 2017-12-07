package ng.com.coursecode.piqmessenger.GifReplace.mViewer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.GifReplace.MediaViewer;
import ng.com.coursecode.piqmessenger.Interfaces.DataPass;
import ng.com.coursecode.piqmessenger.R;

public class MedViewer extends PiccMaqCompatActivity implements DataPass{

    public static final String DATA_ = "mndktknkg";
    int position=0;
    private List<String> allSaved;
    List<String> list=new ArrayList<>();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private Sectionp mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        position=getIntent().getIntExtra(Intent.EXTRA_PHONE_NUMBER, 0);

        context=MedViewer.this;

        list=getIntent().getStringArrayListExtra(Intent.ACTION_BATTERY_LOW);
        list=(list==null)? (new ArrayList<String>()):list;

        mSectionsPagerAdapter = new Sectionp(getSupportFragmentManager(), list);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(getIntent().getIntExtra(MediaViewer.DATA_, 0), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mediaviewr, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_done:
//                copy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public String getFile() {
        return list.get(mViewPager.getCurrentItem());
    }

    public Uri getUri() {
        return Uri.parse(getFile());
    }

    @Override
    public void onDataPass(String data) {
        Intent result = new Intent();
        result.setData(Uri.parse(data));
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
