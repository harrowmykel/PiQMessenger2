package ng.com.coursecode.piqmessenger.Statuses;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.goncalves.pugnotification.notification.PugNotification;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.File.CFile;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.ImageActivity;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Gif__;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {


    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;
    Context context;
    EditText emojiconEditText;
    ImageView camera_Post;
    TextView counter;
    SwitchCompat handle_toggle;
    LinearLayout user_prof;
    public static int NUMBER_OF_WORDS=300;
    //    SharedPref sharedPref;
    String show_act="show_act_snack";
    Uri tempUri=Uri.EMPTY;
    ImageView img;
    Intent intent;
    Stores stores;
    String privacy_public="3931939";
    String privacy_private="329393333";
    String privacy_friends="23939939";
    String privacy=privacy_public;
    String recipient;
    String text;
    //so it is less than 0 when null;
    int len=-1;
    private int NOT_INT=231425;
    private int small_icon=R.drawable.profile_add_photo;
    private String POST_FOLDER;//="solder/";
    boolean isReady=false;

    String postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=CreatePost.this;
        postid=getIntent().getStringExtra(PostsAct.POSTID);
        stores=new Stores(context);
        POST_FOLDER=stores.getFolderPosts();

        handle_toggle=(SwitchCompat)findViewById(R.id.newpost_switch);
        counter=(TextView)findViewById(R.id.newpost_counter);
        user_prof=(LinearLayout)findViewById(R.id.newpost_user);
        emojiconEditText=(EditText)findViewById(R.id.newpost_edit);
        camera_Post=img=(ImageView)findViewById(R.id.newpost_img_show);
        TextView fulln=(TextView)findViewById(R.id.crt_fullname);
        TextView username=(TextView)findViewById(R.id.crt_user);
        ImageView user_dp=(CircleImageView)findViewById(R.id.crt_dp);
        String uiser=stores.getUsername();
        Users_prof users_prof=Users_prof.getInfo(context, uiser);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        fulln.setText(users_prof.getFullname());
        username.setText(uiser);

        counter.setText("" + NUMBER_OF_WORDS);

        camera_Post.setOnClickListener(this);
        handle_toggle.setOnClickListener(this);
        handle_toggle.setVisibility(View.GONE);
        (findViewById(R.id.private_p)).setOnClickListener(this);
        (findViewById(R.id.public_p)).setOnClickListener(this);
        (findViewById(R.id.friends_p)).setOnClickListener(this);
        setToDefault(R.id.public_p);
        switchView();
        emojiconEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                len = NUMBER_OF_WORDS - emojiconEditText.getText().toString().length();
                counter.setText("" + len);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return super.onCreateOptionsMenu(menu);
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
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img);
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }

    private void switchView() {
        if(handle_toggle.isChecked()){
            if(user_prof.getVisibility()!=View.VISIBLE)
                user_prof.setVisibility(View.VISIBLE);
        }
        else{
            if(user_prof.getVisibility()!=View.INVISIBLE)
                user_prof.setVisibility(View.INVISIBLE);
        }
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

    public void validateBeforeSend(){
        text=emojiconEditText.getText().toString();
        if(isReady){
            Toasta.makeText(context, R.string.posting, Toast.LENGTH_SHORT);
            finish();
            String urltoImage=tempUri.toString();
            if(stores.isExtUrl(urltoImage)){
                sendToServer(urltoImage);
            }else{
                sendToGoogle();
            }
        }else{
            if(!text.isEmpty()){
                Toasta.makeText(context, R.string.posting, Toast.LENGTH_SHORT);
                finish();
                sendToServer("");
            }else{
                Toasta.makeText(context, R.string.text_and_image_must_not_be_empty, Toast.LENGTH_SHORT);
            }
        }
    }

    public void sendToGoogle() {
        GoogleUpload googleUpload=new GoogleUpload(context, Stores.POST_STORE, NOT_INT, small_icon, tempUri, new GoogleUpload.GoogleUploadListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Uri url) {
                tempUri=url;
                sendToServer(url.toString());
            }
        });
        googleUpload.sendToGoogle();
    }

    public void sendToServer(String urltoImage){
        text=emojiconEditText.getText().toString();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__>  call = apiInterface.newPost(stores.getUsername(), stores.getPass(), stores.getApiKey(), text, urltoImage, privacy, recipient, postid);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                final TextView tx=(TextView)findViewById(R.id.warning);
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
                }else if(modelll.getSuccess() !=null){
                    Toasta.makeText(context, R.string.post_sent, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "contactlist");
            }
        });
    }

    @Override
    public void onClick(View v){
        int id=v.getId();
        switch (id){
            case R.id.newpost_switch:
                if(!handle_toggle.isChecked()){
//                    if (!sharedPref.getBoolean(show_act)){
                    Snackbar.make(v, R.string.hndl_nt, Snackbar.LENGTH_SHORT).setAction(R.string.never_disp, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                sharedPref=sharedPref.saveBoolean(show_act, true);
                        }
                    }).show();
//                    }
                }
                switchView();
                break;
            case R.id.newpost_img_show:
                //TO DO startCameraActivityForResult done
                showSelector();
                break;
            case R.id.private_p:
                setToDefault(id);
                privacy=privacy_private;
                break;
            case R.id.public_p:
                setToDefault(id);
                privacy=privacy_public;
                break;
            case R.id.friends_p:
                setToDefault(id);
                privacy=privacy_friends;
                break;
        }
    }


    public void setToDefault(int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((FancyButton)findViewById(R.id.private_p)).setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color, getTheme()));
            ((FancyButton)findViewById(R.id.public_p)).setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color, getTheme()));
            ((FancyButton)findViewById(R.id.friends_p)).setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color, getTheme()));
            ((FancyButton)findViewById(id)).setBackgroundColor(getResources().getColor(R.color.privacy_selected, getTheme()));
            ((FancyButton)findViewById(id)).setBackgroundColor(getResources().getColor(R.color.privacy_selected_border, getTheme()));

        }else{
            ((FancyButton)findViewById(R.id.private_p)).setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
            ((FancyButton)findViewById(R.id.public_p)).setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color ));
            ((FancyButton)findViewById(R.id.friends_p)).setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
            ((FancyButton)findViewById(id)).setBackgroundColor(getResources().getColor(R.color.privacy_selected));
            ((FancyButton)findViewById(id)).setBackgroundColor(getResources().getColor(R.color.privacy_selected_border));
        }
        ((FancyButton)findViewById(R.id.private_p)).setTextColor(R.color.cardview_dark_background);
        ((FancyButton)findViewById(R.id.public_p)).setTextColor(R.color.cardview_dark_background);
        ((FancyButton)findViewById(R.id.friends_p)).setTextColor(R.color.cardview_dark_background);
        ((FancyButton)findViewById(id)).setTextColor(R.color.white);
    }
}