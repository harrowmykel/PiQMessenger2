package ng.com.coursecode.piqmessenger.Groups;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Database__.Group_tab;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Fragments_.Groups;
import ng.com.coursecode.piqmessenger.ImageActivity;
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

public class CreateGroup extends PiccMaqCompatActivity {

    private static final String PROFILE_STORE = "rjfsnlknfskslfnifsk";
    public static String USERNAME="djkbzjriudbfudjbiud";
    boolean fromSavedState;
    String username_;
    Context context;

    EditText  username;
    EditText fullname, bio;
    CircleImageView user_dp;
    Stores stores;

    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;
    Uri tempUri=Uri.EMPTY;
    ImageView img;
    Intent intent;
    //so it is less than 0 when null;
    int len=-1;
    private int NOT_INT=231425;
    private int small_icon= R.drawable.profile_add_photo;
    private String POST_FOLDER;//="solder/";
    boolean isReady=false;
    private boolean isLoaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        context=CreateGroup.this;
        stores = new Stores(context);
        username_= stores.getUsername();
        username_=(username_==null)?(new Stores(context)).getUsername():username_;

        fullname=(EditText)findViewById(R.id.fullname);
        username=(EditText)findViewById(R.id.username);
        bio=(EditText)findViewById(R.id.bio_content);
        user_dp=(CircleImageView) findViewById(R.id.prof_pic);
        isLoaded=true;
       /* String ab="@"+ username_;
        username.setText(ab);

        Users_prof users_prof=Users_prof.getInfo(context, username_);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        fullname.setText(users_prof.getFullname());
        setTitle(users_prof.getFullname());*/


        user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector();
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
            Piccassa.load(context, tempUri, user_dp);
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }

    public void sendToServer(final String urltoImage){
        final String text=bio.getText().toString();
        final String g_username=username.getText().toString();
        final String name=fullname.getText().toString();

        if(g_username.length()<3){
            Toast.makeText(context, R.string.inv_user, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, getString(R.string.usergtThan, 3), Toast.LENGTH_SHORT).show();
        }

        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.createGroup(stores.getUsername(), stores.getPass(), stores.getApiKey(), name, g_username, text, urltoImage);
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
                        public void onShowOtherResult(int res__) {
                            tx.setVisibility(View.VISIBLE);
                            tx.setText(res__);
                        }
                    });
                }else if(modelll.getSuccess() !=null){
                    Toasta.makeText(context, getString((R.string.group_created)), Toast.LENGTH_SHORT);
                    Group_tab usertab=new Group_tab();
                    usertab.setUser_name(g_username);
                    usertab.setFullname(name);
                    usertab.setImage(urltoImage);
                    Groups.subscribeTo(context, usertab, true);
                    finish();
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
