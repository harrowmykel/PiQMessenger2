package ng.com.coursecode.piqmessenger.PostsAct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Dialog_.LikeDialog;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Fragments_.Posts;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Interfaces.sendData;
import ng.com.coursecode.piqmessenger.Model__.Datum;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.PostModelParcel;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Statuses.CreatePost;
import ng.com.coursecode.piqmessenger.Statuses.EditPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PostsAct extends AppCompatActivity implements sendData {

    public static final String RECIPIENT = "";
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
    SwipeRefreshLayout swipeRefresh;
    boolean userIsAuth, userIsRecip, userIsNeither;
    String thisUser="";

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
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doNetworkCall();                
            }
        });
        doNetworkCall();
    }

    private void doNetworkCall() {
        swipeRefresh.setRefreshing(true);
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
        postid=main_post.getId();
        thisUser=main_post.getUsername();
        posts_subtitle.setText((new TimeModel(context)).getDWM3(main_post.getTime()));
        posts_username.setText(main_post.getFullname());
        main_post.setAuth_username(thisUser);
        posts_text.setText(main_post.getText());
        post_likes.setText(getString(R.string.likes_, main_post.getLikes()));
        String images_= main_post.getUser_image();

        final TextView txo=(TextView)findViewById(R.id.show_previous_post);
        txo.setVisibility(Stores.initView);

        final String replypostid= main_post.getReply_to();
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

        String image= main_post.getImage();

        if(!image.isEmpty()){
            posts_image.setVisibility(View.VISIBLE);
            Piccassa.load(context, image, R.drawable.nosong, R.drawable.empty, posts_image);
        }else{
            posts_image.setVisibility(View.GONE);
        }

        posts_fav.setImageResource(Stores.getLikeImageRes(main_post.getLiked()));
        Piccassa.load(context, images_, R.drawable.user_sample, posts_dp);
        runClicks();
        swipeRefresh.setRefreshing(false);
    }

    private void runClicks() {
        post_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LikesAct.class);
                intent.putExtra(PostsAct.POSTID, postid);
                startActivity(intent);
            }
        });
        swipeRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoader();
                loadMainPostViews();
            }
        });
        posts_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_, username_a;
                username_=main_post.getUsername();
                username_a=main_post.getReciv();

                userIsAuth=username_.equalsIgnoreCase(stores.getUsername());
                userIsRecip=username_a.equalsIgnoreCase(stores.getUsername());
                userIsNeither=(!userIsAuth && !userIsRecip);

                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                int NewPost_array=R.array.nt_user_post;
                if(userIsAuth){
                    NewPost_array=R.array.user_post;
                } else if(userIsRecip){
                    NewPost_array=R.array.user_post;
                } else {
                    NewPost_array=R.array.nt_user_post;
                }
                if(thisUser.equalsIgnoreCase(stores.getUsername())){
                    NewPost_array=R.array.user_post;
                }
                alert.setItems(NewPost_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentq;
                        switch(which){
                            case 0:
                                if(userIsAuth){
                                    Intent ontent=new Intent(context, EditPost.class);
                                    ontent.putExtra(PostsAct.POSTID, postid);
                                    startActivity(ontent);
                                }else{
//                                case R.id.action_report:

                                }
                                break;
                            case 1:
                                if(userIsAuth || userIsRecip){
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                    alertDialogBuilder.setTitle(R.string.action_delete).setMessage(R.string.delete_confirm)
                                            .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).setNegativeButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            delete();
                                        }
                                    }).show();
                                }else{
                                    //neither

                                }
                                break;
                        }
                    }
                });
                alert.show();
            }
        });
        posts_fav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //todo postItemClicked.onShowMoreLikeOptions(position);
                LikeDialog likeDialog=new LikeDialog(context, new sendData() {
                    @Override
                    public void send(Object object) {
                        likeMainPost((String) object);
                    }
                });
                likeDialog.show();
                return false;
            }
        });
        posts_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Converse.class);
                intent.putExtra(Converse.USERNAME, thisUser);
                startActivity(intent);
            }
        });
        posts_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeDialog.playLikeSound(context);
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
                Intent intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, thisUser);
                startActivity(intent);
            }
        });
        posts_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, thisUser);
                startActivity(intent);
            }
        });
    }

    private void likeMainPost(final String type) {
        posts_fav.setImageResource(Stores.getLikeImageRes(type));

        Retrofit retrofit = ApiClient.getClient();

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
        Datum main_post=(Datum)object;
        if(main_post!=null) {
            if(main_post.getError()!=null) {
                stores.handleError(main_post.getError(), context, new ServerError() {
                    @Override
                    public void onEmptyArray() {
                        Toasta.makeText(context, R.string.post_not_found, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onShowOtherResult(int res__) {
                        Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                    }
                });
            }else{
                replace(main_post);
            }
        }
        closeLoader();
    }
    
    public void replace(Datum main__post){

        //TODO mainpost
        main_post=new PostModelParcel();
        main_post.setUsername(getString__(main__post.getAuthUsername()));
        main_post.setReciv(getString__(main__post.getRecivUsername()));
        main_post.setId(getString__(main__post.getId()));
        main_post.setTime(getString__(main__post.getTimestamp()));
        main_post.setText(getString__(main__post.getSubtitle()));
        main_post.setImage(getString__(main__post.getImage()));
//      main_posts_tab_.setFav(getString__(main__post.getFav()));
        main_post.setLiked(getString__(main__post.getLiked()));
        main_post.setLikes(getString__(main__post.getLikes()));
        main_post.setComment(getString__(main__post.getComments()));
        main_post.setFullname(main__post.getAuthData().getAuth());
        main_post.setUser_image(main__post.getAuthData().getAuthImg());
        main_post.setReply_to(main__post.getReplyTo());

        setPostView();
    }

    public void delete(){

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call = apiInterface.deletePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid);

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, serverError);
                }else if(modelll.getSuccess() !=null){
                    Toasta.makeText(context, R.string.deleted, Toast.LENGTH_SHORT);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "postact");
            }
        });
    }

    ServerError serverError=new ServerError() {
        @Override
        public void onEmptyArray() {

        }

        @Override
        public void onShowOtherResult(int res__) {
        }
    };
}
