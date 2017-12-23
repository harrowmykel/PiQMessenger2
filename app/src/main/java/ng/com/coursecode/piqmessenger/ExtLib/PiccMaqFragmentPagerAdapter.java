package ng.com.coursecode.piqmessenger.ExtLib;

import android.support.v4.app.Fragment;  import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by harro on 23/12/2017.
 */

public class PiccMaqFragmentPagerAdapter extends FragmentPagerAdapter {
    public PiccMaqFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
