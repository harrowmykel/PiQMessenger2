package ng.com.coursecode.piqmessenger.Groups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Fragments_.Groups;
import ng.com.coursecode.piqmessenger.Fragments_.Posts;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Mmenu.Menu_;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Statuses.CreatePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GroupsAct extends PiccMaqCompatActivity {

    public static final String USERS_FULLNAME = "jkdbckjfsbkdfjkcb";
    public static final String USERS_IMAGE = "Kndlksndaljf";
    public static final String USERS_NAME = "JKlnedfjsjkds;cb";
    public static final String USERS_PASS = "okldnneldkne";
    public static final String DELETE_FRND = Stores.DELETE_FRND;
    public static final String ACCEPT_FRND = Stores.ACCEPT_FRND;
    public static final String SEND_FRND = Stores.SEND_FRND;
    public static String USERNAME="djkbzjriudbfudjbiud";
    boolean fromSavedState;
    String username_;
    Context context;

    MaterialFancyButton frnds_req;
    TextView fullname, username, bio;
    CircleImageView user_dp;
    Stores stores;
    Model__ user_data;
    SwipeRefreshLayout swipeRefresh;
    TextView tx;
    String type;

    String fullname_, grp_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tx = (TextView) findViewById(R.id.warning_);
        setSupportActionBar(toolbar);
        context = GroupsAct.this;
        stores = new Stores(context);
        username_ = getIntent().getStringExtra(USERNAME);
        username_ = (username_ == null) ? (new Stores(context)).getUsername() : username_;

        fullname = (TextView) findViewById(R.id.fullname);
        username = (TextView) findViewById(R.id.username);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        bio = (TextView) findViewById(R.id.bio_content);
        user_dp = (CircleImageView) findViewById(R.id.prof_pic);
        frnds_req = (MaterialFancyButton) findViewById(R.id.frnds);
        frnds_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frnds_req.setText(getString(R.string.elipsize));
                if (user_data != null) {
                    sendFriendReq();
                } else {
                    Toast.makeText(context, R.string.profile_loading, Toast.LENGTH_SHORT).show();
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

        Group_tab users_prof = Group_tab.getInfo(context, username_);
        fullname_=users_prof.getFullname();
        grp_img=users_prof.getImage();

        Piccassa.load(context, grp_img, R.drawable.user_sample, user_dp);
        fullname.setText(fullname_);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreatePost.class);
                intent.putExtra(PostsAct.RECIPIENT, username_);
                startActivity(intent);
            }
        });
        setTitle(fullname_);
        loadUp();
    }

    public void loadUp(){
        // Create a new Fragment to be placed in the activity layout
        Posts firstFragment = Posts.newInstance(username_, false, Posts.GROUPS);
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
        String frndsData= user_data.getConfirm();
        if(Stores.isTrue(frndsData)){
            //delete frndship
            type=DELETE_FRND;
        }else {
            //send
            type=SEND_FRND;
        }

        Retrofit retrofit = ApiClient.getClient();
        final String postid=username_;
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call = apiInterface.joinGroup(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, type);

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, serverError);
                }else if(modelll.getSuccess() !=null){

                    Group_tab usertab=new Group_tab();
                    usertab.setUser_name(postid);
                    usertab.setFullname(fullname_);
                    usertab.setImage(grp_img);

                    if(type.equalsIgnoreCase(DELETE_FRND)){
                        user_data.setConfirm("0");
                        Groups.subscribeTo(context, usertab, false);
                    }else {
                        user_data.setConfirm("1");
                        Groups.subscribeTo(context, usertab, true);
                    }
                }
                Stores2.setGroupText(frnds_req, user_data.getConfirm(), context);
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

        Call<Model__> call=apiInterface.getGroup(stores.getUsername(), stores.getPass(), stores.getApiKey(), username_);

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
                    if (user_data.getError() != null) {
                        stores.handleError(user_data.getError(), context, serverError);
                    }else{
                        String user_name = user_data.getAuth_username();
                        String image=user_data.getAuth_data().getAuth_img();
                        String fullnames=user_data.getAuth_data().getFullname();
                        final String bioo=user_data.getBio();
                        boolean verified=user_data.getVerified();

                        fullname_=fullnames;
                        grp_img=image;

                        fullname.setText(fullnames);
                        Piccassa.load(context, image, R.drawable.user_sample, user_dp);
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

                        type=user_data.getConfirm();
                        if(user_name.toLowerCase().equalsIgnoreCase(stores.getUsername().toLowerCase())){
                            Prefs.putString(USERS_FULLNAME, fullnames);
                            Prefs.putString(USERS_IMAGE, image);
                        }
                        Stores2.setGroupText(frnds_req, type, context);
                        setTitle(fullname_);
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
                intent=new Intent(context, GroupsAct.class);
                intent.putExtra(GroupsAct.USERNAME, username);
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

    ServerError serverError=new ServerError() {
        @Override
        public void onEmptyArray() {
            tx.setVisibility(View.VISIBLE);
            tx.setText(R.string.empty_result);
        }

        @Override
        public void onShowOtherResult(int res__) {
            tx.setVisibility(View.VISIBLE);
            tx.setText(res__);
        }
    };
}
