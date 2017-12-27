package ng.com.coursecode.piqmessenger.networkcalls;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.database__.Posts_tab;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 24/10/2017.
 */

public class PostsCall {


    Context context;
    List<Model__> model_list;
    Stores stores;
    int page=1;

    ApiInterface apiInterface;

    public PostsCall(Context context1) {
        context = context1;
        model_list = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void getAllNewFavs(){
        fetchOnline("b");
    }

    public void getAllNewPosts(){
        fetchOnline("hk");
    }

    public void fetchOnline(String where){
        /*Call<Model__> call=apiInterface.getAllPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), where, ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
               saveToPostDb(response);
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
            }
        });*/
    }

    private void saveToPostDb(Response<Model__> response) {
        Model__ model_lisj=response.body();
        List<Model__> model_list=model_lisj.getData();
        Model__ model_l=model_lisj.getPagination();
        int num=model_list.size();
        for(int i=0; i<num; i++){
            Model__ modelll=model_list.get(i);
            Posts_tab posts_tab_=new Posts_tab();

            Users_prof users_prof=new Users_prof();
            Users_prof users_prof1=new Users_prof();

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

            try {
                posts_tab_.save(context);
            }catch (Exception r){
                Toasta.makeText(context, r.getMessage(), Toast.LENGTH_SHORT);
            }
        }
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

}
