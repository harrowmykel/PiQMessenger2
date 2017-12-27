package ng.com.coursecode.piqmessenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;

import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.contacts_.ContactLists;
import ng.com.coursecode.piqmessenger.conversate.Converse;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Posts;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.mmenu.Menu_;
import ng.com.coursecode.piqmessenger.model__.FrndsData;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.Stores2;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.statuses.CreatePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends PiccMaqCompatActivity {

    public static final String USERS_FULLNAME = "jkdbckjfsbkdfjkcb";
    public static final String USERS_IMAGE = "Kndlksndaljf";
    public static final String USERS_NAME = "JKlnedfjsjkds;cb";
    public static final String USERS_PASS = "okldnneldkne";
    public static String USERNAME="djkbzjriudbfudjbiud";
    boolean fromSavedState;
    String username_;
    Context context;

    MaterialFancyButton frnds_req, message;
    TextView fullname, username, bio;
    CircleImageView user_dp;
    Stores stores;
    Model__ user_data;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = Profile.this;
        stores = new Stores(context);
        username_ = getIntent().getStringExtra(USERNAME);
        username_ = (username_ == null) ? (new Stores(context)).getUsername() : username_;

        fullname = (TextView) findViewById(R.id.fullname);
        username = (TextView) findViewById(R.id.username);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        bio = (TextView) findViewById(R.id.bio_content);
        user_dp = (CircleImageView) findViewById(R.id.prof_pic);
        frnds_req = (MaterialFancyButton) findViewById(R.id.frnds);
        message = (MaterialFancyButton) findViewById(R.id.prof_msg);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Converse.class);
                intent.putExtra(Converse.USERNAME, username_);
                startActivity(intent);
            }
        });
        frnds_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frnds_req.setText(getString(R.string.elipsize));
                if (user_data != null) {
                    sendFriendReq();
                } else {
                    Toasta.makeText(context, R.string.profile_loading, Toast.LENGTH_SHORT);
                }
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUp();
            }
        });

        String ab = "@" + username_;
        username.setText(ab);

        Users_prof users_prof = Users_prof.getInfo(context, username_);
        Piccassa.loadDp(context, users_prof.getImage(), user_dp);
        fullname.setText(users_prof.getFullname());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreatePost.class);
                intent.putExtra(PostsAct.RECIPIENT, username_);
                startActivity(intent);
            }
        });
        setTitle(users_prof.getFullname());
        loadUp();
    }

    public void loadUp(){
        // Create a new Fragment to be placed in the activity layout
        Posts firstFragment = Posts.newInstance(username_, false, Posts.PROFILE);
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

        setUpProfile();
    }

    private void sendFriendReq() {
        String type;

        FrndsData frndsData= user_data.getFrndsData();
        if(frndsData.getRFrnds()){
            //delete frndship
            type=ContactLists.DELETE_FRND;
        }else if(frndsData.getRRcvd()){
            //accept
            type=ContactLists.ACCEPT_FRND;
        }else if (frndsData.getRSent()){
            //delete
            type=ContactLists.DELETE_FRND;
        }else {
            //send
            type=ContactLists.SEND_FRND;
        }

        Retrofit retrofit = ApiClient.getClient();
        String postid=username_;
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;

        call = apiInterface.friendReq(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, type);

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ user_data_=model_lis.get(0);

                final TextView tx=(TextView)findViewById(R.id.warning_);
                tx.setVisibility(Stores.initView);
                if(user_data_.getError()!=null) {
                    stores.handleError(user_data_.getError(), context, new ServerError() {
                        @Override
                        public void onEmptyArray() {
                            tx.setVisibility(View.VISIBLE);
                            tx.setText(R.string.empty_result);
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            tx.setVisibility(View.VISIBLE);
                            tx.setText(res__);
                        }
                    });
                }else if(user_data_.getSuccess() !=null){
                    FrndsData frndsData= user_data.getFrndsData();
                    FrndsData frndsData1= Stores2.getFrndsData(frndsData);
                    user_data.setFrndsData(frndsData1);
                }
                Stores2.setFrndText(frnds_req, user_data.getFrndsData(), context);

            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    private void setUpProfile() {
        swipeRefresh.setRefreshing(true);
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call=apiInterface.getUser(stores.getUsername(), stores.getPass(), stores.getApiKey(), username_);

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {

                Model__ model_lisj = response.body();
                List<Model__> model_list = model_lisj.getData();
                Model__ model_l = model_lisj.getPagination();
                int num = model_list.size();

                String any = model_l.getPagesLeft();
                int pgLeft = Stores.parseInt(any);
                if (num > 0) {
                    user_data = model_list.get(0);
                    final TextView tx = (TextView) findViewById(R.id.warning_);
                    tx.setVisibility(Stores.initView);
                    if (user_data.getError() != null) {
                        stores.handleError(user_data.getError(), context, new ServerError() {
                            @Override
                            public void onEmptyArray() {
                                tx.setVisibility(View.VISIBLE);
                                tx.setText(R.string.empty_result);
                            }

                            @Override
                            public void onShowOtherResult(String res__) {
                                tx.setVisibility(View.VISIBLE);
                                tx.setText(res__);
                            }
                        });
                    }else{
                        String user_name = user_data.getAuth_username();
                        String image=user_data.getAuth_data().getAuth_img();
                        String fullnames=user_data.getAuth_data().getFullname();
                        String friends=user_data.getAuth_data().getFullname();
                        String subtitle=user_data.getSubtitle();
                        String online=user_data.getOnline();

                        if(online.trim().equalsIgnoreCase("1")){
                            (findViewById(R.id.online)).setVisibility(View.VISIBLE);
                        }

                        final String bioo=user_data.getBio();
                        boolean verified=user_data.getVerified();

                        fullname.setText(fullnames);
                        Piccassa.loadDp(context, image, user_dp);
                        bio.setText(bioo);

                        if(verified){
                            (findViewById(R.id.verified)).setVisibility(View.VISIBLE);
                        }

                        bio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showMessage(bioo);
                            }
                        });

                        Stores2.setFrndText(frnds_req, user_data.getFrndsData(), context);
                        if(user_name.toLowerCase().equalsIgnoreCase(stores.getUsername().toLowerCase())){
                            Prefs.putString(USERS_FULLNAME, fullnames);
                            Prefs.putString(USERS_IMAGE, image);
                        }

                        setTitle(fullnames);
                    }
                }
                swipeRefresh.setRefreshing(false);
            }


            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
                swipeRefresh.setRefreshing(false);
            }
        });
    }


    public  void showMessage(String biok){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("");
        alert.setMessage(biok);
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.profile:
                String username=stores.getUsername();
                intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, username);
                startActivity(intent);
                break;/*;
            case R.id.edit_profile:
                intent=new Intent(context, EditProfile.class);
                startActivity(intent);
                break;*/
            case R.id.more_:
                intent=new Intent(context, Menu_.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
