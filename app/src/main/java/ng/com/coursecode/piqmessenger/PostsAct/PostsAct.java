package ng.com.coursecode.piqmessenger.PostsAct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Fragments_.Posts;
import ng.com.coursecode.piqmessenger.Interfaces.PostItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Interfaces.sendData;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.PostModelParcel;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Statuses.CreatePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PostsAct extends AppCompatActivity implements sendData {

    public static String POSTID="lkresdkltj;kf";

    Context context;
    Stores stores;
    int toSkip=0;
    PostModelParcel main_post;

    boolean fromSavedState;
    Posts_tab posts_tab_;

    String[] send= Posts.send;

    public TextView posts_username, posts_subtitle, posts_text, post_likes;
    public CircleImageView posts_dp;
    public ImageView posts_image, posts_fav, posts_msg, posts_more;
    String postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        context=PostsAct.this;
        stores=new Stores(context);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postid=getIntent().getStringExtra(POSTID);
        if(postid==null || postid.isEmpty()){
            //TODO report ERror here
            return;
        }
        startLoader();
        loadMainPostViews();
    }

    private void loadMainPostViews() {
        posts_username=(TextView)findViewById(R.id.posts_act_2username);
        posts_subtitle=(TextView)findViewById(R.id.posts_act_2subtitle);
        posts_text=(TextView)findViewById(R.id.posts_act_2text);
        posts_dp=(CircleImageView) findViewById(R.id.posts_act_2dp);
        posts_image=(ImageView) findViewById(R.id.posts_act_2image);
        posts_fav=(ImageView) findViewById(R.id.posts_act_2fav);
        posts_msg=(ImageView) findViewById(R.id.posts_act_2msg);
        posts_more=(ImageView) findViewById(R.id.posts_act_2more);
        post_likes=(TextView)findViewById(R.id.posts_act_2likes);
        doNetworkCall();
    }

    private void doNetworkCall() {
        // Create a new Fragment to be placed in the activity layout
        Posts firstFragment = Posts.newInstance(postid, true);
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

    }

    private void setPostView() {
        posts_subtitle.setText((new TimeModel(context)).getDWM3(main_post.getTime()));
        posts_username.setText(main_post.getAuth_data().getAuth());
        posts_text.setText(main_post.getSubtitle());
        post_likes.setText(getString(R.string.likes_, main_post.getLikes()));
        String images_=main_post.getAuth_data().getAuth_img();

        final TextView txo=(TextView)findViewById(R.id.show_previous_post);
        txo.setVisibility(Stores.initView);

        final String replypostid=main_post.getReply_to();
        if(!(replypostid.trim().isEmpty()) && !replypostid.equals("0")){
            txo.setVisibility(View.VISIBLE);
            txo.setText(R.string.show_previous_post);
            txo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PostsAct.class);
                    intent.putExtra(PostsAct.POSTID, replypostid);
                    startActivity(intent);
                }
            });
        }

        String image=main_post.getImage();

        if(!image.isEmpty()){
            posts_image.setVisibility(View.VISIBLE);
            Piccassa.load(context, image, R.drawable.nosong, R.drawable.empty, posts_image);
        }else{
            posts_image.setVisibility(View.GONE);
        }

        if(!(main_post.getLiked()).equals("0")){
            posts_fav.setImageResource(Stores.getLikeSymbol());
        }else{
            posts_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        Piccassa.load(context, images_, R.drawable.user_sample, posts_dp);
        runClicks();
    }

    private void runClicks() {
        post_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid=main_post.getId();
                Intent intent=new Intent(context, LikesAct.class);
                intent.putExtra(PostsAct.POSTID, postid);
                startActivity(intent);
            }
        });
        posts_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo do more
            }
        });
        posts_fav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //todo postItemClicked.onShowMoreLikeOptions(position);
                return false;
            }
        });
        posts_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid=main_post.getAuth_username();
                Intent intent=new Intent(context, Converse.class);
                intent.putExtra(Converse.USERNAME, postid);
                startActivity(intent);
            }
        });
        posts_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(main_post.getLiked().equals("0")){
                    likeMainPost(Stores.POST_LIKE);
                }else{
                    likeMainPost(Stores.POST_NONE);
                }
            }
        });
        posts_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=main_post.getAuth_username();
                Intent intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, username);
                startActivity(intent);
            }
        });
        posts_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=main_post.getAuth_username();
                Intent intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, username);
                startActivity(intent);
            }
        });
    }

    private void likeMainPost(final String type) {
        posts_fav.setImageResource(Stores.getLikeImageRes(type));

        Retrofit retrofit = ApiClient.getClient();
        String postid=main_post.getId();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;
        final int like_amt=Stores.parseInt(main_post.getLikes());

        final String liketobeset=(main_post.getLiked());

        if(type.equals("0")){
            call = apiInterface.unlikePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid);
            post_likes.setText(getString(R.string.likes_, ""+(like_amt-1)));
        }else{
            call = apiInterface.likePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, type);
            post_likes.setText(getString(R.string.likes_, ""+(like_amt+1)));
        }

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    if(type.equals("0")) {
                        main_post.setLikes("" + (like_amt + 1));
                    }else{
                        main_post.setLikes("" + (like_amt - 1));
                    }
                    posts_fav.setImageResource(Stores.getLikeImageRes(liketobeset));
                }else if(modelll.getSuccess() !=null){
                    main_post.setLiked(type);
                    if(type.equals("0")) {
                        main_post.setLikes("" + (like_amt - 1));
                    }else{
                        main_post.setLikes("" + (like_amt + 1));
                    }
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    public void closeLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(GONE);
    }

    public void startLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStart();
        smoothProgressBar.setVisibility(VISIBLE);
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reply, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.ic_action_reply){
            String postid=getIntent().getStringExtra(POSTID);
            Intent ontent=new Intent(context, CreatePost.class);
            ontent.putExtra(POSTID, postid);
            startActivity(ontent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void send(Object object) {
        main_post=(PostModelParcel)object;
        setPostView();
    }
}
