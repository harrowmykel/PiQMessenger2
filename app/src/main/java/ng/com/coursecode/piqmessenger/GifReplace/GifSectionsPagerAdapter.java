package ng.com.coursecode.piqmessenger.GifReplace;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 30/10/2017.
 */

public class GifSectionsPagerAdapter extends FragmentStatePagerAdapter {
    Model__2 query;
    Context context;
    int skip;

    public static int[] currPageTitle={R.string.images, R.string.gifs};
    public static String[] currPageCode={GifLists.images, GifLists.gifs};

    public GifSectionsPagerAdapter(FragmentManager fm, Model__2 query_, Context context_) {
        super(fm);
        query=query_;
        context=context_;
    }


    public GifSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public GifSectionsPagerAdapter(FragmentManager fm, Model__2 query_, Context context_, int toSkip) {
        super(fm);
        query=query_;
        context=context_;
        skip=toSkip;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        List<String> name;
       switch(position){
           case 0:
               name=query.getImg_list();
               break;
           default:
               name=query.getGif_list();
               break;
       }
        return GifLists.newInstance((ArrayList<String>) name, skip, position);
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
