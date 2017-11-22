package ng.com.coursecode.piqmessenger.GifReplace.mViewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/20/2017.
 */
public class Sectionp extends FragmentPagerAdapter {

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.**/

    List<String> strings=new ArrayList<>();

    public Sectionp(FragmentManager fm) {
        super(fm);
    }

    public Sectionp(FragmentManager fm, List<String> stringsh) {
        super(fm);
        this.strings=stringsh;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceHolder (defined as a static inner class below).
        return PlaceHolder.newInstance(strings.get(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return strings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}