package ng.com.coursecode.piqmessenger.Contacts_;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;

public class StatusAct extends AppCompatActivity {

    public static final String STATUS_CODE = "klznklxklfzdnklnlkxcnclkck";
    private static String contacts="kwrnesk";
    private TextView mTextMessage;
    FrameLayout frameLayout;
    Toolbar toolbar;
    boolean backstack=false;

    Boolean fromSavedState=false;

    MaterialSearchView searchView;
    public String searchQuery;
    Fragment oldFragment;

    int currentPage=0;

    ContactLists newFragment;
    boolean submitted=false;
    String status_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout=(FrameLayout)findViewById(R.id.cont_framecontent);
        status_code=getIntent().getStringExtra(STATUS_CODE);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                backstack=true;
                searchQuery=query;
                submitted=true;
                Stores.saveSuggestions(StatusAct.this, StatusAct.contacts, query);
                setSearchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                backstack=false;
                searchQuery=newText;
                if(!submitted)
                    setSearchQuery(newText);
                submitted=false;
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
        searchView.setSuggestions(Stores.getSuggestions(StatusAct.this, StatusAct.contacts));
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        startFragmentTransactions();
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

    public void setSearchQuery(String query) {
        searchQuery =query;
        newFragment=ContactLists.newInstance(true, status_code, query, ContactLists.STATUSACT);
        replaceFrag(newFragment);
    }

    public void replaceFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(oldFragment).commit();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cont_framecontent, fragment);
        if(backstack)transaction.addToBackStack(null);
        transaction.commit();
        oldFragment=fragment;
    }


    private void startFragmentTransactions() {
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.cont_framecontent) != null) {
            try {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.

                Bundle args=new Bundle();
                if(getIntent().getExtras()!=null)
                    args=getIntent().getExtras();
                // Create a new Fragment to be placed in the activity layout
                ContactLists firstFragment = ContactLists.newInstance(true, status_code, "", ContactLists.STATUSACT);
                oldFragment=firstFragment;
                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                if (fromSavedState) {
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.cont_framecontent, firstFragment).commit();
                }else{
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.cont_framecontent, firstFragment).commit();
                }
                fromSavedState=true;
            }catch ( Exception f){
                Toasta.makeText(this, f.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }

}
