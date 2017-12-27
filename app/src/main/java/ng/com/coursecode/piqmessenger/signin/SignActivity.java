package ng.com.coursecode.piqmessenger.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import ng.com.coursecode.piqmessenger.extLib.FullScreenActivity;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.StartUp;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.MainActivity;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignActivity extends FullScreenActivity {

    public static final String IS_LOGIN = "jdsndfklns";
    boolean login=false;
    Context context;
    Stores stores;
    MaterialEditText mETxt, mETxtPass;
    String username_, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_sign);
        (findViewById(R.id.log_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLeave();
            }
        });
        context=SignActivity.this;
        login=getIntent().getBooleanExtra(IS_LOGIN, false);
        ImageView bckgrnd=((ImageView)findViewById(R.id.img_src));
        if(!login){
            ((TextView)(findViewById(R.id.log_in))).setText(R.string.signup);
        }
        Piccassa.load(context, R.drawable.mlogin, bckgrnd);

        mETxt=(MaterialEditText)findViewById(R.id.username_sign);
        mETxtPass=(MaterialEditText)findViewById(R.id.pass_sign);
    }

    private void checkAndLeave() {
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call;
        username_=mETxt.getText().toString().trim();
        pass=mETxtPass.getText().toString().trim();

        if(username_.length()<3 || pass.length()<3 ){
            if(username_.length()<3){
                mETxt.setError(getString(R.string.gtThan, 3));
            } else if(pass.length()<3 ){
                mETxtPass.setError(getString(R.string.gtThan, 3));
            }
            return;
        }

        if(!login)
            call = apiInterface.createUser(username_, pass, stores.getApiKey(), "");
        else
            call = apiInterface.checkUser(username_, pass, stores.getApiKey(), "djfn");

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ user_data_=model_lis.get(0);

                if(user_data_.getError()!=null) {
                    stores.handleError(user_data_.getError(), context, new ServerError() {
                        @Override
                        public void onEmptyArray() {
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            mETxt.setError(res__);
                        }
                    }, true);
                }else if(user_data_.getSuccess() !=null){
                    Prefs.putString(Profile.USERS_NAME, username_);
                    Prefs.putString(Profile.USERS_PASS, pass);
                    leave();
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    private void leave(){
        (new StartUp(context)).start();
        Intent intent=new Intent(SignActivity.this, MainActivity.class);
        intent.putExtra(SignActivity.IS_LOGIN, true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SignActivity.this, LoginActivity.class);
        intent.putExtra(SignActivity.IS_LOGIN, true);
        startActivity(intent);
        finish();
    }
}
