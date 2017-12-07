package ng.com.coursecode.piqmessenger.GifReplace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.giphy.sdk.core.models.Media;
import com.giphy.sdk.core.models.enums.MediaType;
import com.giphy.sdk.core.network.api.CompletionHandler;
import com.giphy.sdk.core.network.api.GPHApi;
import com.giphy.sdk.core.network.api.GPHApiClient;
import com.giphy.sdk.core.network.response.ListMediaResponse;
import com.koushikdutta.ion.Ion;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.FetchMore;
import ng.com.coursecode.piqmessenger.Interfaces.IntentPass;
import ng.com.coursecode.piqmessenger.Model__.Gif__;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GifAct extends PiccMaqCompatActivity implements FetchMore, IntentPass{

    private static final int GIFCODE = 2273;
    private static final String LOAD_HIGH_QUALITY_IMAGE = "lwem.rnlkn;ttksjntrjn";
    private static String TITLE="vbj vhjfjhbfhjb";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private GifSectionsPagerAdapter mGifSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String query="";
    Context context;
    boolean moreCanBeLoaded=true;

    boolean submitted=true;
    Toolbar toolbar;
    boolean backstack=false;

    Stores stores;

    Boolean fromSavedState=false;

    List<String> gifListsArray;
    List<String> imgListsArray;

    MaterialSearchView searchView;
    public String searchQuery="";
    Fragment oldFragment;

    int page=1;
    int toSkip=0;

    boolean gifSrcIsTenor=true;
    public static String srcIsTENOR="srcIsTenor";
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        context=GifAct.this;
        startLoader();
//        gifSrcIsTenor= Prefs.getBoolean(srcIsTENOR, true);
        gifSrcIsTenor=false;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        warning=((TextView)findViewById(R.id.warning));
        warning.setText(R.string.loading);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setSearchQuery();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query_) {
                //Do some magic
                backstack=true;
                searchQuery=query_;
                submitted=true;
                Stores.saveSuggestions(context, GifAct.TITLE, searchQuery);
                setSearchQuery();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                /*backstack=false;
                searchQuery=newText;
                if(!submitted)
                    setSearchQuery(newText);
                submitted=false;*/
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setQuery(searchQuery, false);
            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setVoiceSearch(true); //or false
        searchView.setSuggestions(Stores.getSuggestions(GifAct.this, GifAct.TITLE));
        searchView.setCursorDrawable(R.drawable.custom_cursor);

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                setSearchQuery();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        if(resultCode==RESULT_OK && requestCode==GIFCODE){
            Intent result = new Intent();
            result.setData(data.getData());
            setResult(Activity.RESULT_OK, result);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onOptionsMenuClosed(menu);
        getMenuInflater().inflate(R.menu.menu_contacts_act, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void setSearchQuery() {
        stores = new Stores(context);
        startLoader();
        if(gifSrcIsTenor){
            loadFromTenor();
            warning.setText(R.string.gifs_and_images_by_tenor);
        }else{
            loadFromGiphy();
            warning.setText(R.string.gifs_and_images_by_giphy);
        }
    }

    public void loadSectionIfGifAndImage(){
        if(gifListsArray!=null && imgListsArray!=null){
            Model__2 model=new Model__2();
            model.setGif_list(gifListsArray);
            model.setImg_list(imgListsArray);

            int currItem=mViewPager.getCurrentItem();
            GifSectionsPagerAdapter mGifSectionsPagerAdapter = new GifSectionsPagerAdapter(getSupportFragmentManager(), model, context, toSkip);
            mViewPager.setAdapter(mGifSectionsPagerAdapter);
            mViewPager.setCurrentItem(currItem, false);

            if(gifListsArray.size()<1){
                Toasta.makeText(GifAct.this, R.string.empty_response, Toast.LENGTH_SHORT);
            }
            closeLoader();
        }
    }

    private void loadFromGiphy() {
        GPHApi client = new GPHApiClient(stores.getGiphyApiKey());
        if(!searchQuery.isEmpty()) {
            /// Gif Search
            client.search(searchQuery, MediaType.gif, null, null, null, null, new CompletionHandler<ListMediaResponse>() {
                @Override
                public void onComplete(ListMediaResponse result, Throwable e) {
                    if (result == null) {
                        // Do what you want to do with the error
                        warning.setText(R.string.error_fetchin_item);
                    } else {
                        if (result.getData() != null) {
                            insertIntoArray(0, result.getData());
                        } else {
                            warning.setText(R.string.empty_response);
                        }
                    }
                }
            });

        }else{
            /// Trending Gifs
            client.trending(MediaType.gif, null, null, null, new CompletionHandler<ListMediaResponse>() {
                @Override
                public void onComplete(ListMediaResponse result, Throwable e) {
                    if (result == null) {
                        // Do what you want to do with the error
                        warning.setText(R.string.error_fetchin_item);
                    } else {
                        if (result.getData() != null) {
                            insertIntoArray(0, result.getData());
                        } else {
                            warning.setText(R.string.empty_response);
                        }
                    }
                }
            });
        }
    }

    private void insertIntoArray(int i, List<Media> data) {
        gifListsArray=new ArrayList<>();
        imgListsArray=new ArrayList<>();
        boolean highQuality=Prefs.getBoolean(LOAD_HIGH_QUALITY_IMAGE, false);
        for (Media gify : data) {
            String gif="", gif2="";

            if(highQuality){
                gif=gify.getImages().getOriginal().getGifUrl();
                gif2=gify.getImages().getOriginalStill().getGifUrl();
            }else{
                gif=gify.getImages().getFixedWidth().getGifUrl();
                gif2 = gify.getImages().getFixedWidthStill().getGifUrl();
            }
            if(!gifListsArray.contains(gif)){
                gifListsArray.add(gif);
            }
            if (!imgListsArray.contains(gif2)) {
                imgListsArray.add(gif2);
            }
        }

        loadSectionIfGifAndImage();
    }

    public void loadFromTenor(){

        Retrofit retrofit1 = ApiClient.getGifClient();
        ApiInterface apiInterface = retrofit1.create(ApiInterface.class);
        Call<Gif__> call;
        int limit=49;
        String pos=(page*limit)+"";
        String safesearch="off";

        if(!searchQuery.isEmpty()) {
            call = apiInterface.searchGifs(searchQuery, stores.getGifApiKey(), ""+pos, ""+limit, safesearch);
        }else{
            call = apiInterface.trendingGif(stores.getGifApiKey(), ""+limit);
        }
        call.enqueue(new Callback<Gif__>() {
            @Override
            public void onResponse(Call<Gif__> call, Response<Gif__> response) {
                Gif__ model_lisj=response.body();
                List<Gif__.Result> model_lis=model_lisj.getResults();
                gifListsArray=new ArrayList<>();
                imgListsArray=new ArrayList<>();

                int num=model_lis.size();
                if(num==0){
                    warning.setText(R.string.empty_response);
                }

                boolean highQuality=Prefs.getBoolean(LOAD_HIGH_QUALITY_IMAGE, false);
                for(int i=0; i<num; i++){
                    Gif__.Result modelll=model_lis.get(i);
                    String gif="";
                    String gif2="";
                    if(highQuality){
                        gif=modelll.getMedia().get(0).getGif().getUrl();
                        gif2=modelll.getMedia().get(0).getGif().getPreview();
                    }else{
                        gif2=modelll.getMedia().get(0).getTinygif().getPreview();
                        gif=modelll.getMedia().get(0).getTinygif().getUrl();
                    }

                    if(!imgListsArray.contains(gif2)){
                        imgListsArray.add(gif2);
                    }
                    if(!gifListsArray.contains(gif)){
                        gifListsArray.add(gif);
                    }
                }
                loadSectionIfGifAndImage();
            }

            @Override
            public void onFailure(Call<Gif__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
                closeLoader();
            }
        });
    }

    public void closeLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(View.GONE);
    }


    public void startLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStart();
        smoothProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void fetchNow() {
        setSearchQuery();
    }

    @Override
    public void passIntent(Intent intent) {
        startActivityForResult(intent, GIFCODE);
    }

}