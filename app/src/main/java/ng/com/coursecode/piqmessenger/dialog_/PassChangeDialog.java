package ng.com.coursecode.piqmessenger.dialog_;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 01/12/2017.
 */

public class PassChangeDialog implements View.OnClickListener{


    Context context;
    Dialog dialog;
    boolean currentIsCurrentPass=true;
    MaterialEditText materialEditText;
    String curr_pass;
    TextView title;
    Stores stores;
    String editText;

    public PassChangeDialog(Context context) {
        this.context=context;
        stores=(new Stores(context));
        curr_pass=stores.getPass();
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.change_pass);
        View view=dialog.findViewById(R.id.parent_view);

        View lCancel=view.findViewById(R.id.cancel);
        View lDone=view.findViewById(R.id.action_done);
        materialEditText=(MaterialEditText)view.findViewById(R.id.pass_editext);
        title=(TextView) view.findViewById(R.id.title);

        lDone.setOnClickListener(this);
        lCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.action_done:
                doNext();
                break;
        }
    }

    private void doNext() {
        editText=materialEditText.getText().toString().trim();
        if(currentIsCurrentPass){
            if(curr_pass.equals(editText)){
                currentIsCurrentPass=false;
                materialEditText.setText("");
                title.setText(R.string.input_new_pass);
                materialEditText.setError("");
            }else{
                materialEditText.setError(context.getString(R.string.inv_pass));
            }
        }else{
            sendToServer(editText);
        }
    }

    public void sendToServer(String name){
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.editPass(stores.getUsername(), stores.getPass(), stores.getApiKey(), name);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null){
                    stores.handleError(modelll.getError(), context, new ServerError() {
                        @Override
                        public void onEmptyArray() {
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            materialEditText.setError(res__);
                        }
                    });
                }else if(modelll.getSuccess() !=null){
                    stores.setPass(editText);
                    currentIsCurrentPass=true;
                    Toasta.makeText(context, R.string.pass_changed, Toast.LENGTH_SHORT);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "editprofile");
            }
        });
    }

    public void show(){
        dialog.show();
    }

}
