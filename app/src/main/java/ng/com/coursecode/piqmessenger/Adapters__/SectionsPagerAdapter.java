package ng.com.coursecode.piqmessenger.Adapters__;

/**
 * Created by harro on 09/10/2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqFragmentPagerAdapter;
import ng.com.coursecode.piqmessenger.Fragments_.Groups;
import ng.com.coursecode.piqmessenger.Fragments_.Posts;
import ng.com.coursecode.piqmessenger.Fragments_.Status;
import ng.com.coursecode.piqmessenger.R;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends PiccMaqFragmentPagerAdapter {

    Context context;
//    public static Fragment[] fragments={Posts.newInstance(),  Status.newInstance(), Groups.newInstance()};
    public static int[] fragmentTitles={R.string.posts,  R.string.status, R.string.groups};

    public SectionsPagerAdapter(FragmentManager fm, Context context1) {
        super(fm);
        context=context1;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment stringg;
        switch(position){
            case 1:
                stringg=Status.newInstance();
                break;
            case 2:
                stringg=Groups.newInstance();
                break;
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