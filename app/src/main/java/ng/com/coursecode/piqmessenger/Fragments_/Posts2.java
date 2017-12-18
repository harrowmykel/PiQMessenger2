package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Interfaces.PostItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.PostsAct.LikesAct;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Statuses.CreatePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 09/10/2017.
 */

public class Posts2 extends Fragment {
    View view;
    Context context;
    Stores stores;
    RecyclerView seenrecyclerView, recommendedrecyclerView;
    int page=1;
    boolean moreCanBeLoaded=true;
    LinearLayoutManager mLayoutManagerseen;
    LinearLayoutManager mHoriLazout;
    int toSkip=0;
    String query="", who="";
    FancyButton post_more;

    Handler handler;

    List<Posts_tab> messages=new ArrayList<>();
    Posts_tab posts;
    Model__2 model__2;
    List<Posts_tab> model_list_= new ArrayList<>();

    ApiInterface apiInterface;
    PostsAdapter postsAdapter2;

    Posts_tab posts_tab_;
    boolean do_once=true;

    public static String[] send=new String[]{Stores.POST_LOVE, Stores.POST_LIKE, Stores.POST_SAD, Stores.POST_WOW,
                                Stores.POST_ANGRY, Stores.POST_HAHA};

    public Posts2(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_posts, container, false);
        if (getArguments() != null){
            query = (getArguments().getString(Stores.SearchQuery, ""));
            who = (getArguments().getString(Stores.USERNAME, ""));
        }
//        loadPostFrag();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*public void loadPostFrag() {
        context = getContext();
        stores = new Stores(context);
        startLoader();
        seenrecyclerView = (RecyclerView) view.findViewById(R.id.main_recycle);
        recommendedrecyclerView = (RecyclerView) view.findViewById(R.id.main_recycle2);
        post_more = (FancyButton) view.findViewById(R.id.post_more);
        post_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
                Toasta.makeText(context, ""+page, Toast.LENGTH_SHORT).show();
            }
        });
        (view.findViewById(R.id.refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_list_ = new ArrayList<>();
                messages = new ArrayList<>();
                toSkip = 0;
                page = 1;
                moreCanBeLoaded = true;
                loadPostFrag();
            }
        });
        if (!query.isEmpty()) {
            ((TextView)view.findViewById(R.id.refresh)).setText(R.string.action_search);
        }
        setLists();
    }

    public void setLists(){
        mLayoutManagerseen = new LinearLayoutManager(context);
        mHoriLazout=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        seenrecyclerView.setLayoutManager(mLayoutManagerseen);
        recommendedrecyclerView.setLayoutManager(mHoriLazout);

        setVLists();

        PostsAdapter postsAdapter3=new PostsAdapter(messages, pPostItemClicked);
        if(Stores.flingEdit){
            recommendedrecyclerView.fling(Stores.flingVelX, Stores.flingVelY);
        }
        recommendedrecyclerView.setItemAnimator(new DefaultItemAnimator());
        recommendedrecyclerView.setAdapter(postsAdapter3);


    }

    public void setVLists() {

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                Ntwrkcall();
            }
        };

        handler.postDelayed(r, Stores.WAIT_PERIOD);// 1000);
    }

    public void Ntwrkcall(){
        toSkip=model_list_.size();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
        String where=query;

        post_more.setVisibility(View.GONE);

        Call<Model__> call;
        if(who.isEmpty()){
            call=apiInterface.getAllPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), where, ""+page);
        }else{
            call=apiInterface.getUsersPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), ""+page, who);
        }
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {

                Model__ model_lisj=response.body();
                List<Model__> model_list=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();
                int num=model_list.size();

                String any = model_l.getPagesLeft();
                int pgLeft = Stores.parseInt(any);

                moreCanBeLoaded = (pgLeft > 0);
                post_more.setVisibility(View.VISIBLE);

                for(int i=0; i<num; i++){
                    Model__ modelll=model_list.get(i);
                    posts_tab_=new Posts_tab();

                    Users_prof users_prof=new Users_prof();
                    Users_prof users_prof1=new Users_prof();


                    final TextView tx=(TextView)view.findViewById(R.id.warning);
                    tx.setVisibility(Stores.initView);
                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, new ServerError() {
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
                if (!moreCanBeLoaded) {
                    closeLoader();
                    post_more.setVisibility(View.GONE);
                }

                if(!do_once){
                    postsAdapter2 = new PostsAdapter(model_list_, pPostItemClicked);
                    seenrecyclerView.setAdapter(postsAdapter2);
                    postsAdapter2.notifyDataSetChanged();
                    seenrecyclerView.scrollToPosition(toSkip);
                    closeLoader();
                }

                if(do_once) {
                    seenrecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mLayoutManagerseen = new LinearLayoutManager(context);
                    seenrecyclerView.setLayoutManager(mLayoutManagerseen);

                    if (Stores.flingEdit) {
                        seenrecyclerView.fling(Stores.flingVelX, Stores.flingVelY);
                    }

                    postsAdapter2 = new PostsAdapter(model_list_,pPostItemClicked);
                    seenrecyclerView.setAdapter(postsAdapter2);
                    seenrecyclerView.addOnScrollListener(createInfiniteScrollListener());
                    postsAdapter2.notifyDataSetChanged();
                    closeLoader();
                    do_once=false;
                }
                page++;
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
                closeLoader();
                post_more.setVisibility(View.VISIBLE);
            }
        });
    }

    public void loadMore(){
        startLoader();
        if(moreCanBeLoaded){
            Ntwrkcall();
        }else {
            closeLoader();
            post_more.setVisibility(View.GONE);
        }
    }

    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                loadMore();
            }

        };
    }

    public void closeLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)view.findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(View.GONE);
    }


    public void startLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)view.findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStart();
        smoothProgressBar.setVisibility(View.VISIBLE);
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

    public static Posts2 newInstance(){
        return  new Posts2();
    }

    public static Posts2 newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Posts2 chat=new Posts2();
        chat.setArguments(bundle);
        return chat;
    }

    public static Posts2 newInstance(String query, boolean df) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.USERNAME, query);
        Posts2 chat=new Posts2();
        chat.setArguments(bundle);
        return chat;
    }

    PostItemClicked pPostItemClicked=new PostItemClicked(){
        @Override
        public void onReplyClicked(int position) {
            String postid=model_list_.get(position).getPosts_id();
            Intent ontent=new Intent(context, CreatePost.class);
            ontent.putExtra(PostsAct.POSTID, postid);
            startActivity(ontent);
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
            Intent intent=new Intent(context, PostsAct.class);
            intent.putExtra(PostsAct.POSTID, postid);
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
            String postid=model_list_.get(position).getPosts_id();
            Intent intent=new Intent(context, LikesAct.class);
            intent.putExtra(PostsAct.POSTID, postid);
            startActivity(intent);
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

    public void likedd(final int position, String type){

        Retrofit retrofit = ApiClient.getClient();
        String postid=model_list_.get(position).getPosts_id();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;

        final String liketobeset=type;

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

                final TextView tx=(TextView)view.findViewById(R.id.warning);
                tx.setVisibility(Stores.initView);
                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, new ServerError() {
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
                }else if(modelll.getSuccess() !=null){
                    model_list_.get(position).setLiked(liketobeset);
                    postsAdapter2 = new PostsAdapter(model_list_, pPostItemClicked);
                    seenrecyclerView.setAdapter(postsAdapter2);
                    postsAdapter2.notifyDataSetChanged();
                    seenrecyclerView.scrollToPosition(position);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }
*/
}
