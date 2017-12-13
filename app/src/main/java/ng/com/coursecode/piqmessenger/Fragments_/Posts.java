package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Posts_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.Dialog_.LikeDialog;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Interfaces.PostItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.Model__.Datum;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Pagination;
import ng.com.coursecode.piqmessenger.Model__.PostsModel;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
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

/**
 * Created by harro on 09/10/2017.
 */

public class Posts extends Fragment {
    public static final String REPLIES = "lddlld";
    public static final String SEARCHPOSTS = "fff";
    public static final String PROFILE = "dwdd";
    private static final String TYPE_OF_ACTION = "dddd";
    public static final String DISCOVER = "ass";
    public static final String GROUPS = "kenflknf";
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
    TextView tx;

    Handler handler;

    List<Posts_tab> messages=new ArrayList<>();
    Posts_tab posts;
    Model__2 model__2;
    List<Posts_tab> model_list_= new ArrayList<>();

    ApiInterface apiInterface;
    PostsAdapter postsAdapter2;
    String postid, what_to_do="";

    Posts_tab posts_tab_;
    boolean do_once=true;
    Datum main_post;
    boolean userIsAuth, userIsRecip, userIsNeither;
    public static String[] send=new String[]{Stores.POST_LOVE, Stores.POST_LIKE, Stores.POST_SAD, Stores.POST_WOW,
            Stores.POST_ANGRY, Stores.POST_HAHA};

    public Posts(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_posts, container, false);
        tx=(TextView)view.findViewById(R.id.warning);
        stores = new Stores(context);
        if (getArguments() != null){
            query = (getArguments().getString(Stores.SearchQuery, ""));
            who = (getArguments().getString(Stores.USERNAME, ""));
            postid = (getArguments().getString(PostsAct.POSTID, ""));
            what_to_do = (getArguments().getString(TYPE_OF_ACTION, ""));
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
        query=(query==null)?"":query;
        postid=(postid==null)?"":postid;
        if (!query.isEmpty()) {
            ((TextView)view.findViewById(R.id.refresh)).setText(R.string.action_search);
        }else if (!postid.isEmpty()) {
            ((TextView)view.findViewById(R.id.refresh)).setText(R.string.replies_);
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

        Call<PostsModel> call=apiInterface.getAllPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), where, ""+page);

        switch(what_to_do){
            case PROFILE:
                if(who!=null && !who.isEmpty()){
                    call=apiInterface.getUsersPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), ""+page, who);
                }
                break;
            case GROUPS:
                if(who!=null && !who.isEmpty()){
                    call=apiInterface.getGroupsPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), ""+page, who);
                }
                break;
            case REPLIES:
                if(postid!=null && !postid.isEmpty()){
                    call=apiInterface.getPostsReplies(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, ""+page);
                }
                break;
            case DISCOVER:
                call=apiInterface.getAllDiscover(stores.getUsername(), stores.getPass(), stores.getApiKey(), where, ""+page);
                break;
            default:
                call=apiInterface.getAllPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), where, ""+page);
                break;
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
                stores.reportThrowable(t, "postscall");
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

        if(main_post!=null){
            SendDatum SendDatum =(SendDatum) context;
            if(SendDatum !=null){
                SendDatum.send(main_post);
            }
        }

        for(int i=0; i<num; i++){
            Datum modelll=model_list.get(i);
            posts_tab_=new Posts_tab();

            Users_prof users_prof=new Users_prof();
            Users_prof users_prof1=new Users_prof();

            tx.setVisibility(Stores.initView);
            if(modelll.getError()!=null) {
                stores.handleError(modelll.getError(), context, serverError);
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

    public static Posts newInstance(String query, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        bundle.putString(TYPE_OF_ACTION, type);
        Posts chat=new Posts();
        chat.setArguments(bundle);
        return chat;
    }

    public static Posts newInstance(String query, boolean df, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(PostsAct.POSTID, query);
        bundle.putString(Stores.USERNAME, query);
        bundle.putString(TYPE_OF_ACTION, type);
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
        public void onMoreClicked(final int position) {
            View v=mLayoutManagerseen.findViewByPosition(position);
            v=v.findViewById(R.id.post_more);
            final String username_=model_list_.get(position).getUser_name();
            final String username_a=model_list_.get(position).getReciv();

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
            alert.setItems(NewPost_array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentq;
                    switch(which){
                        case 0:
                            if(userIsAuth){
                                String postid=model_list_.get(position).getPosts_id();
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
                                        delete(position);
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

        @Override
        public void onShowClicked(int position) {
            String postid=model_list_.get(position).getPosts_id();
            Intent intent=new Intent(context, PostsAct.class);
            intent.putExtra(PostsAct.POSTID, postid);
            startActivity(intent);
        }

        @Override
        public void onShowMoreLikeOptions(final int position) {
           LikeDialog likeDialog=new LikeDialog(context, new SendDatum() {
                @Override
                public void send(Object object) {
                    String tobe=(String) object;
                    likedd(position, tobe);
                }
            });
            likeDialog.show();
        }

        @Override
        public void onLikeClicked(final int position) {
            LikeDialog.playLikeSound(context);
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
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;

        final String liketobeset=type;
        int likes_num=Stores.parseInt(model_list_.get(position).getLikes());
        int plus_or;

        if(type.equals("0")){
            call = apiInterface.unlikePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid);
            plus_or=likes_num-1;
        }else{
            call = apiInterface.likePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, type);
            plus_or=likes_num+1;
        }
        final int plus_or_=plus_or;

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                tx.setVisibility(Stores.initView);
                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, serverError);
                }else if(modelll.getSuccess() !=null){
                    model_list_.get(position).setLiked(liketobeset);
                    model_list_.get(position).setLikes(Stores.notZero(plus_or_));
                    postsAdapter2 = new PostsAdapter(model_list_, pPostItemClicked);
                    seenrecyclerView.setAdapter(postsAdapter2);
                    postsAdapter2.notifyDataSetChanged();
                    seenrecyclerView.scrollToPosition(position);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "contactlist");
            }
        });
    }

    public void delete(final int position){

        Retrofit retrofit = ApiClient.getClient();
        String postid=model_list_.get(position).getPosts_id();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call = apiInterface.deletePost(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid);

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                tx.setVisibility(Stores.initView);
                if(modelll.getError()!=null) {
                    stores.handleError(modelll.getError(), context, serverError);
                }else if(modelll.getSuccess() !=null){
                    model_list_.remove(position);
                    postsAdapter2 = new PostsAdapter(model_list_, pPostItemClicked);
                    seenrecyclerView.setAdapter(postsAdapter2);
                    postsAdapter2.notifyDataSetChanged();
                    seenrecyclerView.scrollToPosition(position);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "contactlist");
            }
        });
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
