package ng.com.coursecode.piqmessenger.Contacts_;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.ContactAdapter;
import ng.com.coursecode.piqmessenger.Adapters__.GroupAdapter;
import ng.com.coursecode.piqmessenger.Adapters__.PostsAdapter;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Interfaces.ContactsItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.FrndsData;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.PostsAct.LikesAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactLists extends Fragment {

    public static final String STAV = "hjefasfkjfjk";
    public static final String DELETE_FRND = "delete_frnd";
    public static final String ACCEPT_FRND = "accept_frnd";
    public static final String SEND_FRND = "send_frnd";
    private static final String TYPE_OF_ACTION = Stores.TYPE_OF_ACTION;
    public static final String CONTACTS = "Kldmklm";
    public static final String LIKESACT = "KLdmkdmk;,md";
    public static final String STATUSACT = "ld;dlkdkdk";
    private static final String ONLINES = "hjbkedjffjkxk";
    private static final String BIRTHDAY = "hjbke";
    View view;
    Context context;
    Stores stores;
    RecyclerView recyclerView;
    Bundle bundle;
    String query="";
    String location_;
    boolean argIsAvail=false;
    public static String contacts="1234";
    public static String request="6789";
    public static String all="3456";
    public static String nameData="372r6329djkdsdfz73776";
    String location;
    int page=1;
    int toSkip=0;
    ContactAdapter statusAdapter;
    LinearLayoutManager mLayoutManager;
    boolean moreCanBeLoaded=true;
    String status_code="", post_id="", what_to_do="";

    List<Users_prof> messages;


    public ContactLists() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState_) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_contact_list, container, false);
        context=getContext();
        recyclerView=(RecyclerView)view.findViewById(R.id.main_recycle);
        stores=new Stores(context);

        if(getArguments()!=null) {
            bundle=getArguments();
            query = bundle.getString(Stores.SearchQuery);
            argIsAvail=true;
            location=bundle.getString(Stores.CurrentPage);
            status_code=bundle.getString(StatusAct.STATUS_CODE);
            post_id=bundle.getString(LikesAct.POST_ID);
            what_to_do=bundle.getString(TYPE_OF_ACTION);
        }

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(Stores.flingEdit)
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);

        messages=new ArrayList<>();
        setLists(query);
        return view;
    }


    public void setLists(String query_){
        query=query_;

        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        location_=location;
        toSkip=messages.size();

        Call<Model__> call=apiInterface.searchUsers(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, location_, ""+page);
        switch (what_to_do){
            case LIKESACT:
                if((post_id!=null) && !post_id.isEmpty()){
                    call=apiInterface.getLikes(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, post_id, ""+page);
                }
                break;
            case STATUSACT:
                if((status_code!=null) && !status_code.isEmpty()){
                    call=apiInterface.searchStatusUsers(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, status_code, ""+page);
                }
                break;
            case LikesAct.ONLINE_FRIENDS:
                call=apiInterface.getOnlineFriends(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, ONLINES, ""+page);
                break;
            case LikesAct.BIRTHDAY:
                call=apiInterface.getOnlineFriends(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, BIRTHDAY, ""+page);
                break;
            default:
                call=apiInterface.searchUsers(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, location_, ""+page);
                break;
        }
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();

                int num=model_lis.size();

                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);
                    Users_prof status= new Users_prof();

                    final TextView tx=(TextView)view.findViewById(R.id.warning);
                    tx.setVisibility(Stores.initView);
                    if(modelll.getError()!=null){
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
                        break;//return
                    }

                    String user_name = modelll.getAuth_username();
                    String image=modelll.getAuth_data().getAuth_img();
                    String fullname=modelll.getAuth_data().getFullname();
                    String friends=modelll.getAuth_data().getFullname();
                    String subtitle=modelll.getSubtitle();

                    status.setUser_name(user_name);
                    status.setFullname(fullname);
                    status.setFriends(friends);
                    status.setImage(image);
                    status.setFrndsData(modelll.getFrndsData());

                    status.setLike(modelll.getLiked());
                    if(!messages.contains(status)){
                        messages.add(status);
                    }
                }

                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                moreCanBeLoaded = (pgLeft>0);
                if(!moreCanBeLoaded){
                    closeLoader();
                }

                closeLoader();
                loadRecycler();
                page++;
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
        recyclerView.scrollToPosition(toSkip);
    }

    private void loadRecycler() {
        statusAdapter=new ContactAdapter(messages, true, contactsItemClicked);
        recyclerView.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();
        recyclerView.addOnScrollListener(createInfiniteScrollListener());
        recyclerView.scrollToPosition(toSkip);
    }

    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                startLoader();
                if(moreCanBeLoaded){
                    setLists(query);
                }else {
                    closeLoader();
                }
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

    public static ContactLists newInstance(int position){
        return new ContactLists();
    }

    ContactsItemClicked contactsItemClicked=new ContactsItemClicked() {
        @Override
        public void onUsernameCLicked(int position) {
            String username=messages.get(position).getUser_name();
            Intent intent=new Intent(context, Profile.class);
            intent.putExtra(Profile.USERNAME, username);
            startActivity(intent);
        }

        @Override
        public void onFriendCLicked(int position) {
            FrndsData frndsData= messages.get(position).getFrndsData();
            String type;
            if(frndsData.getRFrnds()){
                //delete frndship
                type=DELETE_FRND;
            }else if(frndsData.getRRcvd()){
                //accept
                type=ACCEPT_FRND;
            }else if (frndsData.getRSent()){
                //delete
                type=DELETE_FRND;
            }else {
                //send
                type=SEND_FRND;
            }
            alterRequest(position, type);
        }

        @Override
        public void onMsgCLicked(int position) {
            String username=messages.get(position).getUser_name();
            Intent intent=new Intent(context, Converse.class);
            intent.putExtra(Converse.USERNAME, username);
            startActivity(intent);
        }
    };

    public void alterRequest(final int position, String type){

        Retrofit retrofit = ApiClient.getClient();
        String postid=messages.get(position).getUser_name();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;

        call = apiInterface.friendReq(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid, type);

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
                    FrndsData frndsData= messages.get(position).getFrndsData();
                    FrndsData frndsData1= Stores2.getFrndsData(frndsData);
                    messages.get(position).setFrndsData(frndsData1);
                }
                loadRecycler();
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    public static ContactLists newInstance(boolean b, String status_code, String query, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        bundle.putString(StatusAct.STATUS_CODE, status_code);
        bundle.putString(LikesAct.POST_ID, status_code);
        bundle.putString(TYPE_OF_ACTION, type);
        ContactLists contactLists=new ContactLists();
        contactLists.setArguments(bundle);
        return contactLists;
    }

    public static ContactLists newInstance(String s, String query, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.CurrentPage, s);
        bundle.putString(Stores.SearchQuery, query);
        bundle.putString(TYPE_OF_ACTION, type);
        ContactLists contactLists=new ContactLists();
        contactLists.setArguments(bundle);
        return contactLists;
    }
}
