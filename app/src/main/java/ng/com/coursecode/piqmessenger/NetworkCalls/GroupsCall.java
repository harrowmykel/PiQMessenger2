package ng.com.coursecode.piqmessenger.NetworkCalls;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
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
 * Created by harro on 24/10/2017.
 */

public class GroupsCall {


    Context context;
    List<Model__> model_list;
    Stores stores;
    int page=1;

    ApiInterface apiInterface;

    public GroupsCall(Context context1) {
        context = context1;
        model_list = new ArrayList<>();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void getAllMessages(){
        Call<Model__> call=apiInterface.getAllGroups(stores.getUsername(), stores.getPass(), stores.getApiKey(), stores.getTime(), ""+page);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ model_l=model_lisj.getPagination();

                int num=model_lis.size();
                for(int i=0; i<num; i++){
                    Model__ modelll=model_lis.get(i);
                    Group_tab messages_=new Group_tab();
                    Users_prof users_prof=new Users_prof();
                    Users_prof users_prof1=new Users_prof();

                    if(modelll.getError()!=null) {
                        stores.handleError(modelll.getError(), context, new ServerError() {
                            @Override
                            public void onEmptyArray() {
                            }

                            @Override
                            public void onShowOtherResult(int res__) {
                                if(Stores.serviceError.contains(res__)){
                                    Toasta.makeText(context, res__, Toast.LENGTH_SHORT);
                                }
                            }
                        });
                        break;
                    }
                    if(modelll.getReciv_data()==null){
                        continue;
                    }

                    users_prof.setUser_name(modelll.getReciv_username());
                    users_prof.setFullname(modelll.getReciv_data().getReciv());
                    users_prof.setImage(modelll.getReciv_data().getReciv_img());
                    users_prof1.setUser_name(modelll.getAuth_username());
                    users_prof1.setFullname(modelll.getAuth_data().getAuth());
                    users_prof1.setImage(modelll.getAuth_data().getAuth_img());

                    users_prof.save(context);
                    users_prof1.save(context);

                    messages_.setConfirm(getString__(modelll.getConfirm()));
                    messages_.setImage(getString__(modelll.getImage()));
                    messages_.setMess_age(getString__(modelll.getSubtitle()));
                    messages_.setTim_e(getString__(modelll.getTimestamp()));
                    messages_.setTime_stamp(getString__(modelll.getTimestamp()));
                    messages_.setFullname(getString__(modelll.getReciv_data().getReciv()));
                    messages_.setAuth(getString__(modelll.getAuth_data().getAuth()));
                    messages_.setGroup_id(getString__(modelll.getReciv_username()));
                    messages_.setAuth(getString__(modelll.getAuth_username()));
                    messages_.setmsg_id(getString__(modelll.getId()));
                    messages_.setContext(context);
                    boolean fg=messages_.save(context);
                }
                int pgLeft=Stores.parseInt(model_l.getPagesLeft());
                page++;
                if(pgLeft>0){
                    getAllMessages();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                stores.reportThrowable(t, "messagescall.class");
            }
        });
    }


    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

}
