package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
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
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ng.com.coursecode.piqmessenger.Adapters__.ConvoAdapter;
import ng.com.coursecode.piqmessenger.Adapters__.GroupAdapter;
import ng.com.coursecode.piqmessenger.Adapters__.StatusAdapter;
import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
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
    public static String listAll="1234";
    View view;
    Context context;
    Stores stores;
    RecyclerView recyclerView;
    String  query="";
    int page=1;
    boolean moreCanBeLoaded=true;
    LinearLayoutManager mLayoutManager;

    public Groups(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_group, container, false);
        context=getContext();
        recyclerView=(RecyclerView)view.findViewById(R.id.main_recycle);
        stores=new Stores(context);

        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(Stores.flingEdit)
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);

        if(getArguments()!=null)
            query=(getArguments().getString(Stores.SearchQuery, ""));
        if(query.isEmpty()){
            setLists();
        }else{
            setSearchLists();
        }
//        HashMap
        return view;
    }

    public void setLists(){
        Group_tab status= new Group_tab();
        List<Group_tab> messages=status.listAll(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GroupAdapter statusAdapter=new GroupAdapter(messages);
        recyclerView.setAdapter(statusAdapter);
        statusAdapter.notifyDataSetChanged();
        closeLoader();
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
                List<Group_tab> messages=new ArrayList<>();

                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);

                    Group_tab group_tab=new Group_tab();

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
                    int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                    page++;
                    if(pgLeft<1){
                        moreCanBeLoaded=false;
                        closeLoader();
                    }

                    String user_name = modelll.getAuth();
                    String group_id = modelll.getId();
                    String image=modelll.getAuth_data().getAuth_img();
                    String fullname=modelll.getAuth_data().getAuth();

                    group_tab.setUser_name(user_name);
                    group_tab.setMess_age(fullname);
                    group_tab.setGroup_id(group_id);
                    group_tab.setImage(image);
                    messages.add(group_tab);

                }
                closeLoader();

                GroupAdapter statusAdapter=new GroupAdapter(messages, true);
                recyclerView.setAdapter(statusAdapter);
                statusAdapter.notifyDataSetChanged();
                recyclerView.addOnScrollListener(createInfiniteScrollListener());
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "groups.class");
            }
        });
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
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Groups chat=new Groups();
        chat.setArguments(bundle);
        return chat;
    }
}
