package ng.com.coursecode.piqmessenger.Adapters__;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Fragments_.StatusFragment;
import ng.com.coursecode.piqmessenger.Model__. Model__3;

/**
 * Created by harro on 15/11/2017.
 */

public class StatusPagerAdapter  extends FragmentPagerAdapter {

    ArrayList< Model__3> messages_list;
    ArrayList< Model__3> query;

    public StatusPagerAdapter(FragmentManager supportFragmentManager, ArrayList< Model__3> data, String type_, int position_, Context context) {
        super(supportFragmentManager);
        messages_list=query=data;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return StatusFragment.newInstance(messages_list.get(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return messages_list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}