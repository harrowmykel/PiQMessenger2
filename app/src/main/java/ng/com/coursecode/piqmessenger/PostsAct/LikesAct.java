package ng.com.coursecode.piqmessenger.PostsAct;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.ContactAdapter;
import ng.com.coursecode.piqmessenger.Contacts_.ContactLists;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Interfaces.ContactsItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LikesAct extends PiccMaqCompatActivity {

    private static final String STAV = "hjefasfkjfjk";
    public static final String POST_ID = PostsAct.POSTID;
    public static final String TYPE_OF_ACTION = Stores.TYPE_OF_ACTION;
    public static final String ONLINE_FRIENDS = "online";//must alwayzs be lowercase
    public static final String BIRTHDAY = "birthday";
    public static final String VIEWSTATUS = ContactLists.STATUSACT;
    private static String contacts="kwrnefeksj;nfsk";
    private TextView mTextMessage;
    FrameLayout frameLayout;
    Toolbar toolbar;
    boolean backstack=false;

    Boolean fromSavedState=false;
    Fragment oldFragment;

    int currentPage=0;

    ContactLists newFragment;
    String status_code;

    List<Users_prof> messages;
    boolean submitted = false;
    MaterialSearchView searchView;
    public String searchQuery;
    public String action_type, jumk, cc_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.cont_framecontent);
        status_code = getIntent().getStringExtra(PostsAct.POSTID);

        action_type = ContactLists.LIKESACT;
        jumk=getIntent().getStringExtra(TYPE_OF_ACTION);
        cc_title=getString(R.string.likes);
        if(jumk!=null){
            jumk=jumk.toLowerCase();
            switch (jumk){
                case ONLINE_FRIENDS:
                    action_type = ONLINE_FRIENDS;
                    cc_title=getString(R.string.online_friends);
                    break;
                case BIRTHDAY:
                    action_type = BIRTHDAY;
                    cc_title=getString(R.string.birthday_celebrants);
                    break;
                case VIEWSTATUS:
                    action_type = VIEWSTATUS;
                    cc_title=getString(R.string.view_users);
                    break;
                default:
                    action_type = ContactLists.LIKESACT;
                    break;
            }
        }

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                backstack = true;
                searchQuery = query;
                submitted = true;
                Stores.saveSuggestions(LikesAct.this, LikesAct.contacts, query);
                setSearchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                backstack = false;
                searchQuery = newText;
                if (!submitted)
                    setSearchQuery(newText);
                submitted = false;
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
        searchView.setSuggestions(Stores.getSuggestions(LikesAct.this, LikesAct.contacts));
        searchView.setCursorDrawable(R.drawable.custom_cursor);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        startFragmentTransactions();
        setTitle(cc_title);
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
        searchQuery = query;
        newFragment = ContactLists.newInstance(false, status_code, query, action_type);
        replaceFrag(newFragment);
    }

    public void replaceFrag(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(oldFragment).commit();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cont_framecontent, fragment);
        if (backstack) transaction.addToBackStack(null);
        transaction.commit();
        oldFragment = fragment;
    }


    private void startFragmentTransactions() {
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.cont_framecontent) != null) {
            try {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.

                Bundle args = new Bundle();
                if (getIntent().getExtras() != null)
                    args = getIntent().getExtras();
                // Create a new Fragment to be placed in the activity layout
                ContactLists firstFragment = ContactLists.newInstance(false, status_code, "", action_type);
                oldFragment = firstFragment;
                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                if (fromSavedState) {
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.cont_framecontent, firstFragment).commit();
                } else {
                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.cont_framecontent, firstFragment).commit();
                }
                fromSavedState = true;
            } catch (Exception f) {
                Toasta.makeText(this, f.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }
}