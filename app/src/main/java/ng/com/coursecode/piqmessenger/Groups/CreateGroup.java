package ng.com.coursecode.piqmessenger.Groups;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import ng.com.coursecode.piqmessenger.EditProfile;
import ng.com.coursecode.piqmessenger.ExtLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Fragments_.Groups;
import ng.com.coursecode.piqmessenger.ImageActivity;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.PostsAct.LikesAct;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
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
    private boolean isLoaded=false, isEditing=false;
    View prof_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        context=CreateGroup.this;
        stores = new Stores(context);
        username_= getIntent().getStringExtra(GroupsAct.USERNAME);
        username_=(username_==null)?(new Stores(context)).getUsername():username_;

        fullname=(EditText)findViewById(R.id.fullname);
        username=(EditText)findViewById(R.id.username);
        prof_msg=findViewById(R.id.prof_msg);
        bio=(EditText)findViewById(R.id.bio_content);
        user_dp=(CircleImageView) findViewById(R.id.prof_pic);
        isLoaded=true;
        user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector();
            }
        });
        prof_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(context, LikesAct.class);
                intent.putExtra(LikesAct.TYPE_OF_ACTION, LikesAct.VIEW_GROUP);
                intent.putExtra(PostsAct.POSTID, username_);
                startActivity(intent);
            }
        });
        Intent gIntent=getIntent();//class);
        String type_of=gIntent.getStringExtra(Stores.TYPE_OF_ACTION);
        type_of=(type_of==null)?"":type_of;
        isEditing=type_of.equalsIgnoreCase(Stores.EDIT);

        if(isEditing){
            username.setEnabled(false);
            username.setText(username_);
            prof_msg.setVisibility(View.VISIBLE);
            setUpProfile();
        }else{
            prof_msg.setVisibility(View.GONE);
            username.setEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isEditing){
            setTitle(R.string.edit);
        }
    }

    private void setUpProfile() {
isLoaded=false;
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call=apiInterface.getGroup(stores.getUsername(), stores.getPass(), stores.getApiKey(), username_);

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
                                tx.setText(R.string.no_result_found);
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
                        Piccassa.load(context, image, R.drawable.user_sample, user_dp);
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
        }else {
            Toasta.makeText(context, R.string.profile_loading, Toast.LENGTH_SHORT);
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
            Toasta.makeText(context, R.string.inv_user, Toast.LENGTH_SHORT);
            Toasta.makeText(context, getString(R.string.usergtThan, 3), Toast.LENGTH_SHORT);
            return;
        }

        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.createGroup(stores.getUsername(), stores.getPass(), stores.getApiKey(), name, g_username, text, urltoImage);

        if(isEditing){
            call = apiInterface.editGroup(stores.getUsername(), stores.getPass(), stores.getApiKey(), name, g_username, text, urltoImage);
        }
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
                    if(isEditing){
                        Toasta.makeText(context, getString((R.string.group_edited)), Toast.LENGTH_SHORT);
                    }else{
                        Toasta.makeText(context, getString((R.string.group_created)), Toast.LENGTH_SHORT);
                    }
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
