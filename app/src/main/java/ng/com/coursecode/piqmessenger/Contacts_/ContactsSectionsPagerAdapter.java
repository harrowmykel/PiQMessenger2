package ng.com.coursecode.piqmessenger.Contacts_;

import android.content.Context;
import android.support.v4.app.Fragment;  import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 30/10/2017.
 */

public class ContactsSectionsPagerAdapter extends FragmentStatePagerAdapter {
    String query = "";

    public static int[] currPageTitle={R.string.contacts, R.string.new_requests, R.string.all};
    public static String[] currPageCode={ContactLists.contacts, ContactLists.request, ContactLists.all};
    Context context;

    public ContactsSectionsPagerAdapter(FragmentManager fm, String query_, Context context_) {
        super(fm);
        query=query_;
        context=context_;
    }

    public ContactsSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return ContactLists.newInstance(currPageCode[position], query, ContactLists.CONTACTS);

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return currPageCode.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(currPageTitle[position]);
    }
}
