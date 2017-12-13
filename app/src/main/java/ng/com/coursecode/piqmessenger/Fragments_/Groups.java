package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.GroupAdapter;
import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Groups.CreateGroup;
import ng.com.coursecode.piqmessenger.Groups.GroupsAct;
import ng.com.coursecode.piqmessenger.Interfaces.ContactsItemClicked;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 09/10/2017.
 */

public class Groups extends Fragment {
    private static final String AVAIL_GROUPS = "AVAIL_GROUP";
    private static final String ALL_MESSAGES = "ALL_MESSAGES";
    public static final String DELETE_FRND = Stores.DELETE_FRND;
    public static final String ACCEPT_FRND = Stores.ACCEPT_FRND;
    public static final String SEND_FRND = Stores.SEND_FRND;
    public static String listAll="1234";
    View view;
    Context context;
    Stores stores;
    RecyclerView recyclerView;
    String  query="";
    int page=1;
    boolean moreCanBeLoaded=true;
    LinearLayoutManager mLayoutManager;
    List<Group_tab> messages;
    GroupAdapter statusAdapter;
    TextView tx;

    public Groups(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_group, container, false);
        context=getContext();
        recyclerView=(RecyclerView)view.findViewById(R.id.main_recycle);

        tx=(TextView)view.findViewById(R.id.warning);

        (view.findViewById(R.id.create)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CreateGroup.class));
            }
        });
        tx.setVisibility(Stores.initView);
        stores=new Stores(context);

        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(Stores.flingEdit)
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);
        boolean showDiscover=false;
        if(getArguments()!=null) {
            query = (getArguments().getString(Stores.SearchQuery, ""));
            showDiscover=(getArguments().getBoolean(Stores.DISCOVER, false));
        }
        if(query.isEmpty() && !showDiscover){
            setLists();
        }else{
            setSearchLists();
        }
//        HashMap
        return view;
    }

    public void setLists(){
        Group_tab status= new Group_tab();
        messages=status.listAll(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadRecycler();
    }

    public void setSearchLists(){
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call=apiInterface.searchGroups(stores.getUsername(), stores.getPass(), stores.getApiKey(), query, ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();
                int num=model_lis.size();
                messages=new ArrayList<>();

                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);

                    Group_tab group_tab=new Group_tab();
                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, serverError);
                        closeLoader();
                        break;
                    }
                    int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                    page++;
                    if(pgLeft<1){
                        moreCanBeLoaded=false;
                        closeLoader();
                    }

                    String user_name = modelll.getAuth_username();
                    String member = modelll.getConfirm();
                    String group_id = modelll.getAuth_username();
                    String image=modelll.getAuth_data().getAuth_img();
                    String fullname=modelll.getAuth_data().getAuth();

                    group_tab.setUser_name(user_name);
                    group_tab.setFullname(fullname);
                    group_tab.setUser_name(group_id);
                    group_tab.setImage(image);
                    group_tab.setFriends(member);
                    messages.add(group_tab);

                }
                loadRecycler();
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "groups.class");
            }
        });
    }

    private void loadRecycler() {
        statusAdapter=new GroupAdapter(messages, true, contactsItemClicked);
        recyclerView.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();
        recyclerView.addOnScrollListener(createInfiniteScrollListener());
        closeLoader();
    }

    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToBottom() {
                super.onScrolledToBottom();
                startLoader();
                if(moreCanBeLoaded){
                    setLists();
                }else{
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

    public static Groups newInstance(){
        return  new Groups();
    }

    public static Fragment newInstance(String query) {
        return newInstance(query, false);
    }
    public static Fragment newInstance(String query, boolean showDiscover) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        bundle.putBoolean(Stores.DISCOVER, showDiscover);
        Groups chat=new Groups();
        chat.setArguments(bundle);
        return chat;
    }

    ContactsItemClicked contactsItemClicked=new ContactsItemClicked() {
        @Override
        public void onUsernameCLicked(int position) {
            String username=messages.get(position).getUser_name();
            Intent intent=new Intent(context, GroupsAct.class);
            intent.putExtra(GroupsAct.USERNAME, username);
            startActivity(intent);
        }

        @Override
        public void onFriendCLicked(int position) {
            String frndsData= messages.get(position).getFriends();
            String type;
            if(Stores.isTrue(frndsData)){
                //delete frndship
                type=DELETE_FRND;
            }else {
                //send
                type=SEND_FRND;
            }
            alterRequest(position, type);
        }

        @Override
        public void onMsgCLicked(int position) {
        }
    };


    public void alterRequest(final int position, final String type){
        Retrofit retrofit = ApiClient.getClient();
        final String postid=messages.get(position).getUser_name();
        final String fullname_=messages.get(position).getFullname();
        final String grp_img=messages.get(position).getImage();

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

                    if(type.equalsIgnoreCase(DELETE_FRND) || type.equalsIgnoreCase("0")){
                        messages.get(position).setFriends("0");
                        subscribeTo(context, usertab, false);
                    }else {
                        messages.get(position).setFriends("1");
                        subscribeTo(context, usertab, true);
                    }
                }
                loadRecycler();
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
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

    public static void subscribeTo(Context context, Group_tab users_prof, boolean b) {
        if(b){
            FirebaseMessaging.getInstance().subscribeToTopic((users_prof.getUser_name() + Stores.GroupTopicEND).toLowerCase());//status
            users_prof.save(context);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic((users_prof.getUser_name() + Stores.GroupTopicEND).toLowerCase());//status
            users_prof.delete(context);
        }
    }
}
