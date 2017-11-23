package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Contacts_.StatusAct;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.File.CFile;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.Interfaces.PostItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Interfaces.sendData;
import ng.com.coursecode.piqmessenger.Model__.Datum;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Pagination;
import ng.com.coursecode.piqmessenger.Model__.PostModelParcel;
import ng.com.coursecode.piqmessenger.Model__.PostsModel;
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

public class Posts extends Fragment {
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
    String postid;

    Posts_tab posts_tab_;
    boolean do_once=true;
    Datum main_post;
    public static String[] send=new String[]{Stores.POST_LOVE, Stores.POST_LIKE, Stores.POST_SAD, Stores.POST_WOW,
            Stores.POST_ANGRY, Stores.POST_HAHA};

    public Posts(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_posts, container, false);
        if (getArguments() != null){
            query = (getArguments().getString(Stores.SearchQuery, ""));
            who = (getArguments().getString(Stores.USERNAME, ""));
            postid = (getArguments().getString(PostsAct.POSTID, ""));
        }
        loadPostFrag();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadPostFrag() {
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
        Ntwrkcall();
    }

    public void Ntwrkcall(){

        post_more.setVisibility(View.GONE);
        toSkip=model_list_.size();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
        String where=query;

        Call<PostsModel> call;
        if(!who.isEmpty()){
            call=apiInterface.getUsersPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), ""+page, who);
        }else if(!postid.isEmpty()){
            call=apiInterface.getPostsReplies(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, ""+page);
        }else{
            call=apiInterface.getAllPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), where, ""+page);
        }
        call.enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Call<PostsModel> call, final Response<PostsModel> response) {


                handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {
                        sendToUi(response);
                    }
                };

                handler.postDelayed(r, Stores.WAIT_PERIOD);// 1000);
            }

            @Override
            public void onFailure(Call<PostsModel> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
                closeLoader();
                post_more.setVisibility(View.VISIBLE);
            }
        });

    }

    private void sendToUi(Response<PostsModel> response) {

        PostsModel model_lisj=response.body();
        List<Datum> model_list=model_lisj.getData();
        Pagination model_l=model_lisj.getPagination();
        int num=model_list.size();

        String any = model_l.getPagesLeft();
        int pgLeft = Stores.parseInt(any);

        moreCanBeLoaded = (pgLeft > 0);
        post_more.setVisibility(View.VISIBLE);

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
            if(main_post.getAuthUsername()!=null){
                setPostView();
            }
        }

        for(int i=0; i<num; i++){
            Datum modelll=model_list.get(i);
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
                    public void onShowOtherResult(int res__) {
                        tx.setVisibility(View.VISIBLE);
                        tx.setText(res__);
                    }
                });
                closeLoader();
                break;
            }
            users_prof.setUser_name(modelll.getRecivUsername());
            users_prof.setFullname(modelll.getRecivData().getReciv());
            users_prof.setImage(modelll.getRecivData().getRecivImg());

            users_prof1.setUser_name(modelll.getAuthUsername());
            users_prof1.setFullname(modelll.getAuthData().getAuth());
            users_prof1.setImage(modelll.getAuthData().getAuthImg());

            users_prof.save(context);
            users_prof1.save(context);

            posts_tab_.setUser_name(getString__(modelll.getAuthUsername()));
            posts_tab_.setReciv(getString__(modelll.getRecivUsername()));
            posts_tab_.setPosts_id(getString__(modelll.getId()));
            posts_tab_.setTime(getString__(modelll.getTimestamp()));
            posts_tab_.setText(getString__(modelll.getSubtitle()));
            posts_tab_.setImage(getString__(modelll.getImage()));
//            posts_tab_.setFav(getString__(modelll.getFav()));
            posts_tab_.setLiked(getString__(modelll.getLiked()));
            posts_tab_.setLikes(getString__(modelll.getLikes()));
            posts_tab_.setComment(getString__(modelll.getComments()));
            posts_tab_.setFullname(modelll.getAuthData().getAuth());
            posts_tab_.setUser_image(modelll.getAuthData().getAuthImg());

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

    private void setPostView() {
        //TODO mainpost
        PostModelParcel model__2=new PostModelParcel();
        model__2.setUsername(getString__(main_post.getAuthUsername()));
        model__2.setReciv(getString__(main_post.getRecivUsername()));
        model__2.setId(getString__(main_post.getId()));
        model__2.setTime(getString__(main_post.getTimestamp()));
        model__2.setText(getString__(main_post.getSubtitle()));
        model__2.setImage(getString__(main_post.getImage()));
//      model__2s_tab_.setFav(getString__(main_post.getFav()));
        model__2.setLiked(getString__(main_post.getLiked()));
        model__2.setLikes(getString__(main_post.getLikes()));
        model__2.setComment(getString__(main_post.getComments()));
        model__2.setFullname(main_post.getAuthData().getAuth());
        model__2.setUser_image(main_post.getAuthData().getAuthImg());

        sendData sendData=(sendData) context;
        if(sendData!=null){
            sendData.send(model__2);
        }
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

    public static Posts newInstance(){
        return  new Posts();
    }

    public static Posts newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Posts chat=new Posts();
        chat.setArguments(bundle);
        return chat;
    }

    public static Posts newInstance(String query, boolean df) {
        Bundle bundle = new Bundle();
        if(df){
            bundle.putString(PostsAct.POSTID, query);
        }else{
            bundle.putString(Stores.USERNAME, query);
        }
        Posts chat=new Posts();
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
            View v=mLayoutManagerseen.findViewByPosition(position);
            v=v.findViewById(R.id.post_more);
            String username_=model_list_.get(position).getUser_name();

            AlertDialog.Builder alert=new AlertDialog.Builder(context);
            int NewPost_array=R.array.nt_user_post;
            if(username_.equalsIgnoreCase(stores.getUsername())){
                NewPost_array=R.array.user_post;
            }
            alert.setItems(NewPost_array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentq;
                    switch(which){
                        case R.id.action_delete:
                            break;
                        case R.id.action_report:
                            break;
                        case R.id.action_edit:
                            break;
                    }
                }
            });
            alert.show();
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
                        public void onShowOtherResult(int res__) {
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
}
