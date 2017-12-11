package ng.com.coursecode.piqmessenger.Searches;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import ng.com.coursecode.piqmessenger.Adapters__.SectionsPagerAdapter;
import ng.com.coursecode.piqmessenger.Contacts_.ContactAct;
import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Fragments_.Chats;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.R;

public class ConvoSearchAct extends PiccMaqCompatActivity {

    FrameLayout frameLayout;
    Toolbar toolbar;
    boolean backstack=false;

    Boolean fromSavedState=false;

    MaterialSearchView searchView;
    public String searchQuery;

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
        currentPage=currentPage=R.string.chats;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sect=currentPage=R.string.chats;
        toolbar.setTitle(sect);
        frameLayout=(FrameLayout)findViewById(R.id.search_framecontent);
        context=ConvoSearchAct.this;
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                setSearchQuery(query);
                submitted=true;
                Stores.saveSuggestions(ConvoSearchAct.this, sect, query);
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
        searchView.setSuggestions(Stores.getSuggestions(ConvoSearchAct.this, sect));
        searchView.setCursorDrawable(R.drawable.custom_cursor);

        firstFragment=frageSearch("");
        swiper=((SwipeRefreshLayout)findViewById(R.id.swipeRefresh));

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new MessagesCall(context)).getAllMessages();
                swiper.setRefreshing(false);
            }
        });
        startFragmentTransactions();
    }

    private Fragment frageSearch(String query) {
        Fragment frag;
        toolbar.setTitle(sect);
        frag= Chats.newInstance(query);
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
        getMenuInflater().inflate(R.menu.menu_contacts_act, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;//Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_people:
                intent = new Intent(this, ContactAct.class);
                startActivity(intent);
                return true;
        }
        return false;
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
        // Replace whatever is in the fragment_container view with this fragment,
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
            }
        }
    }

}
