package ng.com.coursecode.piqmessenger.adapters__;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.extLib.PiccMaqFragmentPagerAdapter;
import ng.com.coursecode.piqmessenger.fragments_.StatusFragment;
import ng.com.coursecode.piqmessenger.fragments_.StatusFragment2;
import ng.com.coursecode.piqmessenger.model__. Model__3;

/**
 * Created by harro on 15/11/2017.
 */

public class StatusPagerAdapter  extends PiccMaqFragmentPagerAdapter {

    ArrayList< Model__3> messages_list;
    ArrayList< Model__3> query;
    String type;

    public StatusPagerAdapter(FragmentManager supportFragmentManager, ArrayList< Model__3> data, String type_, int position_, Context context) {
        super(supportFragmentManager);
        messages_list=query=data;
        type=type_;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return StatusFragment2.newInstance(messages_list.get(position), type);
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