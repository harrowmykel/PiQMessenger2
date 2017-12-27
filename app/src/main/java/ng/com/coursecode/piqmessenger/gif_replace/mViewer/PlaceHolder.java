package ng.com.coursecode.piqmessenger.gif_replace.mViewer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;  import ng.com.coursecode.piqmessenger.extLib.PiccMaqFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.interfaces.DataPass;
import ng.com.coursecode.piqmessenger.R;


/**
 * Created by User on 5/20/2017.
 */
public class PlaceHolder  extends PiccMaqFragment {
    /**
     * A placeholder fragment containing a simple view.
     */
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ScaleImageView imageView;
    View v;
    String datat;
    View rootView;
    Context context;//on
    DataPass dataPass;

    public PlaceHolder() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPass=(DataPass)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();
        rootView = inflater.inflate(R.layout.activity_media_viewer, container, false);
        imageView=(ScaleImageView)rootView.findViewById(R.id.img_);
        datat=getArguments().getString(ARG_SECTION_NUMBER);
        v=rootView.findViewById(R.id.select_image);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPass.onDataPass(datat);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView=(ScaleImageView)rootView.findViewById(R.id.img_);
        datat=getArguments().getString(ARG_SECTION_NUMBER);
        Piccassa.loadGlide(context, getUri(), imageView);
    }


    /**
     * Returns a new instance of this PiccMaqFragment for the given section
     * number.
     */

    public static Fragment newInstance(String s) {
        PlaceHolder fragment = new PlaceHolder();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, s);
        fragment.setArguments(args);
        return fragment;
    }

    public Uri getUri() {
        return Uri.parse(getArguments().getString(ARG_SECTION_NUMBER));
    }
}
