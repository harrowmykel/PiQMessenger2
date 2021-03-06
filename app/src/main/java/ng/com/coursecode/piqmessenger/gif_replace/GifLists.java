package ng.com.coursecode.piqmessenger.gif_replace;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;  import ng.com.coursecode.piqmessenger.extLib.PiccMaqFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.StaggeredGridView;
import ng.com.coursecode.piqmessenger.gif_replace.mViewer.MedViewer;
import ng.com.coursecode.piqmessenger.interfaces.Gifselected;
import ng.com.coursecode.piqmessenger.interfaces.IntentPass;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GifLists extends PiccMaqFragment {

    private static final String STAV = "hjefasfkjfjk";
    View view;
    Context context;
    Stores stores;
    Bundle bundle;
    String query="";
    String location_;
    boolean argIsAvail=false;
    public static String gifs="1234";
    public static String images="6789";
    String location;
    int page=1;
    int toSkip=0;
    StaggeredAdapter statusAdapter;
    LinearLayoutManager mLayoutManager;
    boolean moreCanBeLoaded=true;

    List<String> messages;
    IntentPass intentPass;


    ContentResolver contentResolver;
    public GifLists() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState_) {
        // Inflate the layout for this PiccMaqFragment

        view=inflater.inflate(R.layout.fragment_gif_list, container, false);
        context=getContext();
        stores=new Stores(context);

        if(getArguments()!=null) {
            bundle=getArguments();
            messages = bundle.getStringArrayList(Stores.Results);
            argIsAvail=true;
            toSkip=bundle.getInt(Stores.toSkip);
            page = bundle.getInt(Stores.CurrentPage);
        }

        StaggeredGridView gridView = (StaggeredGridView) view.findViewById(R.id.staggeredGridView1);
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        gridView.setItemMargin(margin); // set the GridView margin
        gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well
        StaggeredAdapter adapter = new StaggeredAdapter(context, messages, page, gifselected);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

    public static GifLists newInstance(ArrayList<String> query, int skip, int position) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Stores.Results, query);
        bundle.putInt(Stores.toSkip, skip);
        bundle.putInt(Stores.CurrentPage, position);
        GifLists gifLists =new GifLists();
        gifLists.setArguments(bundle);
        return gifLists;
    }

    Gifselected gifselected=new Gifselected() {
        @Override
        public void onGifSelected(int position) {
            Intent intat = new Intent(getContext(), MedViewer.class);
            intat.putExtra(MediaViewer.DATA__, messages.get(position));
            intat.putExtra(MediaViewer.DATA_, position);
            intat.putExtra(Intent.EXTRA_PHONE_NUMBER, position);
            intat.putExtra(Intent.ACTION_BATTERY_LOW, (ArrayList) messages);
            intentPass.passIntent(intat);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intentPass=(IntentPass)context;
    }
}
