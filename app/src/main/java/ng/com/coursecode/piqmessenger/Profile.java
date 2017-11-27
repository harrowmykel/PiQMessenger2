package ng.com.coursecode.piqmessenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Contacts_.ContactLists;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.Fragments_.Posts;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.FrndsData;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity {

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

        Users_prof users_prof = Users_prof.getInfo(context, username_);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        fullname.setText(users_prof.getFullname());

        setTitle(users_prof.getFullname());
        loadUp();
    }

    public void loadUp(){
        // Create a new Fragment to be placed in the activity layout
        Posts firstFragment = Posts.newInstance(username_, false);
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
                        public void onShowOtherResult(int res__) {
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
                            public void onShowOtherResult(int res__) {
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
                        final String bioo=user_data.getBio();

                        fullname.setText(fullnames);
                        Piccassa.load(context, image, R.drawable.user_sample, user_dp);
                        bio.setText(bioo);

                        bio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showMessage(bioo);
                            }
                        });

                        Stores2.setFrndText(frnds_req, user_data.getFrndsData(), context);

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
                break;
            case R.id.edit_profile:
                intent=new Intent(context, EditProfile.class);
                startActivity(intent);
                break;
            case R.id.more_:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
