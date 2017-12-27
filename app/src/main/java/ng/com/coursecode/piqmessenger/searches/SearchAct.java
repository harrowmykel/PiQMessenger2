package ng.com.coursecode.piqmessenger.searches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;

import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.fragments_.Groups;
import ng.com.coursecode.piqmessenger.fragments_.Posts;
import ng.com.coursecode.piqmessenger.fragments_.Status;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.R;

public class SearchAct extends PiccMaqCompatActivity {

    FrameLayout frameLayout;
    Toolbar toolbar;
    boolean backstack=false;

    Boolean fromSavedState=false;

    MaterialSearchView searchView;
    public String searchQuery="";

    int currentPage=0;
    int sect;

    // Create a new Fragment to be placed in the activity layout
    Fragment firstFragment;
    boolean submitted=false;
    SwipeRefreshLayout swiper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(getIntent()!=null)
            currentPage=getIntent().getIntExtra(Stores.TYPE_OF_ACTION, 0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sect=currentPage;
        toolbar.setTitle(sect);
        context=SearchAct.this;
        frameLayout=(FrameLayout)findViewById(R.id.search_framecontent);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                setSearchQuery(query);
                submitted=true;
                Stores.saveSuggestions(SearchAct.this, sect, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if(!submitted)
                    setSearchQuery(newText);
                submitted=false;
                return false;
            }
        });

        searchView.setVoiceSearch(true); //or false
        searchView.setSuggestions(Stores.getSuggestions(SearchAct.this, sect));
        searchView.setCursorDrawable(R.drawable.custom_cursor);

        firstFragment=frageSearch("");
        swiper=((SwipeRefreshLayout)findViewById(R.id.swipeRefresh));

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setSearchQuery(searchQuery);
                swiper.setRefreshing(false);
            }
        });
        startFragmentTransactions();
    }

    private Fragment frageSearch(String query) {
        Fragment frag;
        toolbar.setTitle(sect);
        switch (sect){
            case R.string.posts:
                if(query.isEmpty()){
                    frag= Posts.newInstance(query, Posts.DISCOVER);
                }else{
                    frag= Posts.newInstance(query, Posts.SEARCHPOSTS);
                }
                break;
            case R.string.chats:
                frag= Chats.newInstance(query);
                break;
            case R.string.status:
                frag= Status.newInstance(query);
                break;
            case R.string.groups:
                if(query.isEmpty()){
                    frag= Groups.newInstance(Groups.listAll, true);
                }else{
                    frag= Groups.newInstance(query);
                }
                break;
            default:
                frag= Chats.newInstance(query);
                break;
        }
        return frag;
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
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onOptionsMenuClosed(menu);
        getMenuInflater().inflate(R.menu.menu_search_act, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void setSearchQuery(String query) {
        searchQuery =query;
        Fragment fragment=frageSearch(query);
        replaceFrag(fragment, sect);
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void replaceFrag(Fragment fragment, int stringResId){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this PiccMaqFragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.search_framecontent, fragment);
        if(backstack)transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }


    private void startFragmentTransactions() {
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.search_framecontent) != null) {
            try {

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                if (fromSavedState) {
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.search_framecontent, firstFragment).commit();
                }else{
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.search_framecontent, firstFragment).commit();
                }
                fromSavedState=true;
            }catch ( Exception f){
                Toasta.makeText(this, f.getMessage(), Toast.LENGTH_SHORT);
                Toasta.makeText(this, "oncreate", Toast.LENGTH_SHORT);
            }
        }
        swiper.setRefreshing(false);
    }

}
