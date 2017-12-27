package ng.com.coursecode.piqmessenger.adapters__;

/**
 * Created by harro on 09/10/2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ng.com.coursecode.piqmessenger.extLib.PiccMaqFragmentPagerAdapter;
import ng.com.coursecode.piqmessenger.fragments_.Groups;
import ng.com.coursecode.piqmessenger.fragments_.Posts;
import ng.com.coursecode.piqmessenger.fragments_.Status;
import ng.com.coursecode.piqmessenger.R;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends PiccMaqFragmentPagerAdapter {

    Context context;
    //    public static Fragment[] fragments={Posts.newInstance(),  Status.newInstance(), Groups.newInstance()};
    public static int[] fragmentTitles={R.string.posts, R.string.groups};//  R.string.status};//,

    public SectionsPagerAdapter(FragmentManager fm, Context context1) {
        super(fm);
        context=context1;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        int fragh=fragmentTitles[position];
        Fragment stringg;
        switch(fragh){
            case R.string.status:
                stringg=Status.newInstance();
                break;
            case R.string.groups:
                stringg=Groups.newInstance();
                break;
            case R.string.posts:
            default:
                stringg=Posts.newInstance();
                break;
        }
        return stringg;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return fragmentTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int stringg= fragmentTitles[position];
        return context.getString(stringg);
    }
}