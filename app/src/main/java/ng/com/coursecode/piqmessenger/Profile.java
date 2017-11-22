package ng.com.coursecode.piqmessenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
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

    FancyButton frnds_req, message;
    TextView fullname, username, bio;
    CircleImageView user_dp;
    Stores stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context=Profile.this;
        stores = new Stores(context);
        username_= getIntent().getStringExtra(USERNAME);
        username_=(username_==null)?(new Stores(context)).getUsername():username_;

        fullname=(TextView)findViewById(R.id.fullname);
        username=(TextView)findViewById(R.id.username);
        bio=(TextView)findViewById(R.id.bio_content);
        user_dp=(CircleImageView) findViewById(R.id.prof_pic);
        frnds_req=(FancyButton)findViewById(R.id.frnds);
        message=(FancyButton)findViewById(R.id.prof_msg);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Converse.class);
                intent.putExtra(Converse.USERNAME, username_);
                startActivity(intent);
            }
        });

        String ab="@"+ username_;
        username.setText(ab);

        Users_prof users_prof=Users_prof.getInfo(context, username_);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        fullname.setText(users_prof.getFullname());

        setTitle(users_prof.getFullname());
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

    private void setUpProfile() {

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
                    Model__ modelll = model_list.get(0);
                    final TextView tx = (TextView) findViewById(R.id.warning_);
                    tx.setVisibility(Stores.initView);
                    if (modelll.getError() != null) {
                        stores.handleError(modelll.getError(), context, new ServerError() {
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
                        String user_name = modelll.getAuth_username();
                        String image=modelll.getAuth_data().getAuth_img();
                        String fullnames=modelll.getAuth_data().getFullname();
                        String friends=modelll.getAuth_data().getFullname();
                        String subtitle=modelll.getSubtitle();
                        final String bioo=modelll.getBio();

                        fullname.setText(fullnames);
                        Piccassa.load(context, image, R.drawable.user_sample, user_dp);
                        bio.setText(bioo);

                        bio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showMessage(bioo);
                            }
                        });
                    }
                }
            }


            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
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
