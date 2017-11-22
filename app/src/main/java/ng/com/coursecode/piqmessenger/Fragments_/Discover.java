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
import android.widget.Toast;

import java.util.List;

import ng.com.coursecode.piqmessenger.Adapters__.DiscoverAdapter;
import ng.com.coursecode.piqmessenger.Database__.Discover_tab;
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

public class Discover extends Fragment {
    View view;
    Context context;
    Stores stores;
    RecyclerView recyclerView;

    public Discover(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recycler_layout_discover, container, false);
        context=getContext();
        Retrofit retrofit= ApiClient.getClient();
        stores=new Stores(context);
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        recyclerView=(RecyclerView)view.findViewById(R.id.main_recycle);

        /* if(moreCanBeLoaded){
                    setLists(query);
                }else {
                    CircularProgressBar circularProgressBar;
                    circularProgressBar=(CircularProgressBar)view.findViewById(R.id.progress_main);
                    circularProgressBar.setVisibility(View.GONE);
                }*/

        if(stores.useInternet){
            NextAction(apiInterface);
        }
        if(stores.useList2) {
            setLists();
        }

        return view;
    }

    public void setLists(){
        Discover_tab discover= new Discover_tab();
        List<Discover_tab> messages=discover.listAll(context);
        DiscoverAdapter discoverAdapter=new DiscoverAdapter(messages);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(discoverAdapter);
    }

    public void NextAction(ApiInterface apiInterface){

       /* Call<Model__> call=apiInterface.getAllDiscover(stores.getUsername(), stores.getPass(), stores.getApiKey(), stores.getTime());
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Toasta.makeText(context, "cool", Toast.LENGTH_SHORT);
                Model__ model_lisj=response.body();
                List<Model__> model_list=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();

                int num=model_list.size();
                for(int i=0; i<num; i++){
                    Model__ modelll=model_list.get(i);
                    Discover_tab discover_tab_=new Discover_tab();

                    discover_tab_.setImage(getString__(modelll.getImage()));
                    discover_tab_.setText(getString__(modelll.getTimestamp()));
                    discover_tab_.setTime(getString__(modelll.getTimestamp()));
                    discover_tab_.setDiscover_id(getString__(modelll.getReciv()));
                    discover_tab_.setUser_name(getString__(modelll.getAuth()));

                    try {
                        discover_tab_.save(context);
                    }catch (Exception r){
                        Toasta.makeText(context, r.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                Toasta.makeText(context, "nt cool", Toast.LENGTH_SHORT);

            }
        });*/
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

    public static Discover newInstance(){
        return  new Discover();
    }

    public static Discover newInstance(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(Stores.SearchQuery, query);
        Discover chat=new Discover();
        chat.setArguments(bundle);
        return chat;
    }
}
