package ng.com.coursecode.piqmessenger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfile extends PiccMaqCompatActivity {

    private static final String PROFILE_STORE = "rjfsnlknfskslfnifsk";
    public static String USERNAME="djkbzjriudbfudjbiud";
    boolean fromSavedState;
    String username_;
    Context context;

    TextView  username;
    EditText fullname, bio;
    CircleImageView user_dp;
    Stores stores;//todo password

    private static final int IMGREQUESTCODE = 234;
    Uri tempUri=Uri.EMPTY;
    ImageView img;
    Intent intent;
    //so it is less than 0 when null;
    int len=-1;
    private int NOT_INT=231425;
    private int small_icon=R.drawable.profile_add_photo;
    private String POST_FOLDER;//="solder/";
    boolean isReady=false;
    private boolean isLoaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        context=EditProfile.this;
        stores = new Stores(context);
        username_= stores.getUsername();
        username_=(username_==null)?(new Stores(context)).getUsername():username_;

        fullname=(EditText)findViewById(R.id.fullname);
        username=(TextView)findViewById(R.id.username);
        bio=(EditText)findViewById(R.id.bio_content);
        user_dp=(CircleImageView) findViewById(R.id.prof_pic);

        String ab="@"+ username_;
        username.setText(ab);

        Users_prof users_prof=Users_prof.getInfo(context, username_);
        Piccassa.loadDp(context, users_prof.getImage(), user_dp);
        fullname.setText(users_prof.getFullname());

        setTitle(users_prof.getFullname());

        user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector();
            }
        });

        setUpProfile();
    }

    private void setUpProfile() {

        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call=apiInterface.getUser(stores.getUsername(), stores.getPass(), stores.getApiKey(), username_);

        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {

                Model__ model_lisj = response.body();
                List<Model__> model_list = model_lisj.getData();
                Model__ model_l = model_lisj.getPagination();
                int num = model_list.size();

                String any = model_l.getPagesLeft();
                int pgLeft = Stores.parseInt(any);
                if (num > 0) {
                    Model__ modelll = model_list.get(0);
                    final TextView tx = (TextView) findViewById(R.id.warning_);
                    tx.setVisibility(Stores.initView);
                    if (modelll.getError() != null) {
                        stores.handleError(modelll.getError(), context, new ServerError() {
                            @Override
                            public void onEmptyArray() {
                                tx.setVisibility(View.VISIBLE);
                                tx.setText(R.string.empty_result);
                            }

                            @Override
                            public void onShowOtherResult(String res__) {
                                tx.setVisibility(View.VISIBLE);
                                tx.setText(res__);
                            }
                        });
                    }else{
                        String user_name = modelll.getAuth_username();
                        String image=modelll.getAuth_data().getAuth_img();
                        String fullnames=modelll.getAuth_data().getFullname();
                        String friends=modelll.getAuth_data().getFullname();
                        String subtitle=modelll.getSubtitle();
                        final String bioo=modelll.getBio();

                        fullname.setText(fullnames);
                        Piccassa.loadDp(context, image, user_dp);
                        bio.setText(bioo);
                        isLoaded=true;
                    }
                }
            }


            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "postscall");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_done:
                validateBeforeSend();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validateBeforeSend() {
        if(isLoaded){
            if(tempUri!=Uri.EMPTY && !stores.isExtUrl(tempUri.toString())){
                sendToGoogle();
            }else{
                if(tempUri!=Uri.EMPTY && stores.isExtUrl(tempUri.toString())){
                    sendToServer(tempUri.toString());
                }else{
                    sendToServer("");
                }
            }
        }else{
            Toasta.makeText(context, R.string.loading, Toast.LENGTH_SHORT);
        }
    }

    private void showSelector() {
        Intent intentq;
        intentq=new Intent(context, ImageActivity.class);
        intentq.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intentq, IMGREQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isReady=true;
            tempUri = data.getData();
            Piccassa.loadDp(context, tempUri.toString(), user_dp);
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }

    public void sendToServer(String urltoImage){
        String text=bio.getText().toString();
        String name=fullname.getText().toString();

        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.editProfile(stores.getUsername(), stores.getPass(), stores.getApiKey(), name, text, urltoImage);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                final TextView tx=(TextView)findViewById(R.id.warning_);
                tx.setVisibility(Stores.initView);
                if(modelll.getError()!=null){
                    stores.handleError(modelll.getError(), context, new ServerError() {
                        @Override
                        public void onEmptyArray() {
                            tx.setVisibility(View.VISIBLE);
                            tx.setText(R.string.empty_result);
                        }

                        @Override
                        public void onShowOtherResult(String res__) {
                            tx.setVisibility(View.VISIBLE);
                            tx.setText(res__);
                        }
                    });
                }else if(modelll.getSuccess() !=null){
                    Toasta.makeText(context, getString(R.string.profile_saved), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "editprofile");
            }
        });
    }


    public void sendToGoogle() {
        GoogleUpload googleUpload=new GoogleUpload(context, Stores.PROFILE_STORE, NOT_INT, small_icon, tempUri, new GoogleUpload.GoogleUploadListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Uri url) {
                sendToServer(url.toString());
            }
        });
        googleUpload.sendToGoogle();
    }
}
