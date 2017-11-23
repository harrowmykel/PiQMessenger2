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
import ng.com.coursecode.piqmessenger.Model__.Model__;
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

public class PostsAct2 extends AppCompatActivity {

    public static String POSTID="lkresdkltj;kf";

    Context context;
    Stores stores;
    RecyclerView seenrecyclerview;
    int page=1;
    boolean moreCanBeLoaded=true;
    LinearLayoutManager mLayoutManagerseen;
    int toSkip=0;
    Model__ main_post;

    List<Posts_tab> model_list_= new ArrayList<>();

    ApiInterface apiInterface;
    PostsAdapter postsAdapter2;

    Posts_tab posts_tab_;
    boolean do_once=true;

    String[] send= Posts.send;

    public TextView posts_username, posts_subtitle, posts_text, post_likes;
    public CircleImageView posts_dp;
    public ImageView posts_image, posts_fav, posts_msg, posts_more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        context=PostsAct2.this;
        stores=new Stores(context);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startLoader();
        seenrecyclerview=(RecyclerView)findViewById(R.id.main_recycle);
        (findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_list_= new ArrayList<>();
                toSkip=0;
                page=1;
                moreCanBeLoaded=true;
                doNetworkCall();
            }
        });
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
        toSkip=model_list_.size();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
        String postid=getIntent().getStringExtra(POSTID);
        if(postid==null){
            //TODO report ERror here
            return;
        }
        if(postid.isEmpty()){
            //TODO report ERror here
            return;
        }
/*
        Call<Model__> call=apiInterface.getPostsReplies(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                Model__ model_l=model_lisj.getPagination();

                String any = model_l.getPagesLeft();
                int pgLeft = Stores.parseInt(any);

                moreCanBeLoaded = (pgLeft > 0);
                if (!moreCanBeLoaded) {
                    closeLoader();
                }

                main_post=model_lisj.getMain_post();
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
                    closeLoader();
                }
                if(main_post!=null){
                    setPostView();
                    if(main_post.getAuth_username()!=null){
                        setPostView();
                    }
                }
                setRecyclerView(model_lisj);
                if(do_once) {
                    do_once=false;
                }
                page++;
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
                closeLoader();
            }
        });*/
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
                    Intent intent=new Intent(context, PostsAct2.class);
                    intent.putExtra(PostsAct2.POSTID, replypostid);
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
                intent.putExtra(PostsAct2.POSTID, postid);
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

    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToBottom() {
                startLoader();
                if(moreCanBeLoaded){
                    doNetworkCall();
                }else {
                    closeLoader();
                }
                super.onScrolledToBottom();
            }

        };
    }

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }


    public void likedd(final int position, final String liketobeset){

        Retrofit retrofit = ApiClient.getClient();
        final String type=model_list_.get(position).getLiked();
        posts_fav.setImageResource(Stores.getLikeImageRes(type));

        String postid=model_list_.get(position).getPosts_id();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;

        if(type.equals("0")){
            call = apiInterface.unlikePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid);
        }else{
            call = apiInterface.likePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, type);
        }

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    posts_fav.setImageResource(Stores.getLikeImageRes(type));
                }else if(modelll.getSuccess() !=null){
                    model_list_.get(position).setLiked(liketobeset);
                    postsAdapter2 = new PostsAdapter(model_list_, pPostItemClicked);
                    seenrecyclerview.setAdapter(postsAdapter2);
                    postsAdapter2.notifyDataSetChanged();
                    seenrecyclerview.scrollToPosition(position);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    private void setRecyclerView(Model__ model_lisj) {
        if(do_once){
            seenrecyclerview.setItemAnimator(new DefaultItemAnimator());
            mLayoutManagerseen = new LinearLayoutManager(context);
            seenrecyclerview.setLayoutManager(mLayoutManagerseen);

            if (Stores.flingEdit) {
                seenrecyclerview.fling(Stores.flingVelX, Stores.flingVelY);
            }
            seenrecyclerview.addOnScrollListener(createInfiniteScrollListener());
        }
        doModelList(model_lisj);
        postsAdapter2 = new PostsAdapter(model_list_, pPostItemClicked);
        seenrecyclerview.setAdapter(postsAdapter2);
        postsAdapter2.notifyDataSetChanged();

        if(!do_once){
            seenrecyclerview.scrollToPosition(toSkip);
        }
        closeLoader();
    }

    private void doModelList(Model__ model_lisj) {
        List<Model__> model_list=model_lisj.getData();
        int num=model_list.size();
        for(int i=0; i<num; i++){
            Model__ modelll=model_list.get(i);
            posts_tab_=new Posts_tab();

            Users_prof users_prof=new Users_prof();
            Users_prof users_prof1=new Users_prof();

            final TextView tx=(TextView)findViewById(R.id.warning);
            tx.setVisibility(Stores.initView);
            if(modelll.getError()!=null) {
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
                closeLoader();
                break;
            }
            users_prof.setUser_name(modelll.getReciv_username());
            users_prof.setFullname(modelll.getReciv_data().getReciv());
            users_prof.setImage(modelll.getReciv_data().getReciv_img());
            users_prof1.setUser_name(modelll.getAuth_username());
            users_prof1.setFullname(modelll.getAuth_data().getAuth());
            users_prof1.setImage(modelll.getAuth_data().getAuth_img());

            users_prof.save(context);
            users_prof1.save(context);

            posts_tab_.setUser_name(getString__(modelll.getAuth_username()));
            posts_tab_.setReciv(getString__(modelll.getReciv_username()));
            posts_tab_.setPosts_id(getString__(modelll.getId()));
            posts_tab_.setTime(getString__(modelll.getTimestamp()));
            posts_tab_.setText(getString__(modelll.getSubtitle()));
            posts_tab_.setImage(getString__(modelll.getImage()));
            posts_tab_.setFav(getString__(modelll.getFav()));
            posts_tab_.setLiked(getString__(modelll.getLiked()));
            posts_tab_.setLikes(getString__(modelll.getLikes()));
            posts_tab_.setComment(getString__(modelll.getComment()));
            posts_tab_.setFullname(modelll.getAuth_data().getAuth());
            posts_tab_.setUser_image(modelll.getAuth_data().getAuth_img());

            model_list_.add(posts_tab_);
        }
    }


    PostItemClicked pPostItemClicked=new PostItemClicked(){
        @Override
        public void onReplyClicked(int position) {
            String postid=model_list_.get(position).getPosts_id();
            Intent intent=new Intent(context, PostsAct2.class);
            intent.putExtra(PostsAct2.POSTID, postid);
            startActivity(intent);
        }

        @Override
        public void onDMessageClicked(int position) {
            String postid=model_list_.get(position).getUser_name();
            Intent intent=new Intent(context, Converse.class);
            intent.putExtra(Converse.USERNAME, postid);
            startActivity(intent);
        }

        @Override
        public void onUsernameClicked(int position) {
            String username=model_list_.get(position).getUser_name();
            Intent intent=new Intent(context, Profile.class);
            intent.putExtra(Profile.USERNAME, username);
            startActivity(intent);
        }

        @Override
        public void onMoreClicked(int position) {
            Toasta.makeText(context, "more", Toast.LENGTH_SHORT);
        }

        @Override
        public void onShowClicked(int position) {
            String postid=model_list_.get(position).getPosts_id();
            Intent intent=new Intent(context, PostsAct2.class);
            intent.putExtra(PostsAct2.POSTID, postid);
            startActivity(intent);
        }

        @Override
        public void onShowMoreLikeOptions(int position) {
            Toasta.makeText(context, "showmore", Toast.LENGTH_SHORT);
            String liked=model_list_.get(position).getLiked();

            if(liked.equals("0")){
                likedd(position, Stores.POST_LIKE);
            }else{
                likedd(position, "0");
            }
        }

        @Override
        public void onLikeClicked(final int position) {
            Toasta.makeText(context, "like", Toast.LENGTH_SHORT);
            String liked=model_list_.get(position).getLiked();
            if(liked.equals("0")){
                likedd(position, Stores.POST_LIKE);
            }else{
                likedd(position, "0");
            }
        }
    };

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
}
