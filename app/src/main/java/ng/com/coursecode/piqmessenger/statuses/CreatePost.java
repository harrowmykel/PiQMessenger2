package ng.com.coursecode.piqmessenger.statuses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;

import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;
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

import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.ImageActivity;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePost extends PiccMaqCompatActivity implements View.OnClickListener {


    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;
    private static final String LAST_POST_WAS_SENT = "fihfihfi";
    private static final String LAST_POST = "jdhjfhfjlhlj";
    private static final String LAST_IMG = "hdfoufhu";
    private static final String LAST_PRIVACY = "gfkcjbj";
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
    private int privacyId=R.id.public_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=CreatePost.this;
        intent=getIntent();
        postid=unNull(intent.getStringExtra(PostsAct.POSTID));
        recipient=unNull(intent.getStringExtra(PostsAct.RECIPIENT));

        stores=new Stores(context);
        POST_FOLDER=stores.getFolderPosts();

        handle_toggle=(SwitchCompat)findViewById(R.id.newpost_switch);
        counter=(TextView)findViewById(R.id.newpost_counter);
        TextView replying=(TextView)findViewById(R.id.show_previous_post);
        user_prof=(LinearLayout)findViewById(R.id.newpost_user);
        emojiconEditText=(EditText)findViewById(R.id.newpost_edit);
        camera_Post=img=(ImageView)findViewById(R.id.newpost_img_show);
//        TextView fulln=(TextView)findViewById(R.id.crt_fullname);
//        TextView username=(TextView)findViewById(R.id.crt_user);
        ImageView user_dp=(CircleImageView)findViewById(R.id.crt_dp);
        String uiser=stores.getUsername();
        Users_prof users_prof=Users_prof.getInfo(context, uiser);
        Piccassa.loadDp(context, users_prof.getImage(), user_dp);
//        fulln.setText(users_prof.getFullname());
//        username.setText(uiser);

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

        if(!recipient.isEmpty() && recipient.equalsIgnoreCase(stores.getUsername())){
            replying.setText(getString(R.string.write_on_profile, recipient));
        }else if(!postid.isEmpty()){
            replying.setText(R.string.write_a_reply);
        }else{
            replying.setText(R.string.create_a_new_post);
        }
        replying.setVisibility(View.VISIBLE);

        setDefaults();
    }

    private void setDefaults() {
        String defaultText= "";
        String defaultImgUrl="";
        if(intent!=null) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            defaultText=intent.getStringExtra(Intent.EXTRA_TEXT);
            if(uri!=null){
                defaultImgUrl=uri.toString();
            }
        }else if(!Prefs.getBoolean(LAST_POST_WAS_SENT, false)){
            defaultText= Prefs.getString(LAST_POST, "");
            defaultImgUrl=Prefs.getString(LAST_IMG, "");
            setToDefault(Prefs.getInt(LAST_PRIVACY, R.id.public_p));
        }
        emojiconEditText.setText(defaultText);
        if(!defaultImgUrl.isEmpty()){
            tempUri=Uri.parse(defaultImgUrl);
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img, true);
        }
    }

    private String unNull(String stringExtra) {
        return (stringExtra==null)?"":stringExtra;
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
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img, true);
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

    public void saveUp(){
        text=emojiconEditText.getText().toString();
        String urltoImage=tempUri.toString();

        Prefs.putString(LAST_POST, text);
        Prefs.putString(LAST_IMG, urltoImage);
        Prefs.putBoolean(LAST_POST_WAS_SENT, false);
        Prefs.putInt(LAST_PRIVACY, privacyId);
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

    @Override
    protected void onStop() {
        saveUp();
        super.onStop();
    }

    public void sendToGoogle() {
        saveUp();
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
        saveUp();
        text=emojiconEditText.getText().toString();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__>  call;

        if(recipient!=null && !recipient.isEmpty()){
            call = apiInterface.newPostToUser(stores.getUsername(), stores.getPass(), stores.getApiKey(), text, urltoImage, privacy, recipient, postid);
        }else{
            call = apiInterface.newPost(stores.getUsername(), stores.getPass(), stores.getApiKey(), text, urltoImage, privacy, recipient, postid);
        }

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
                        public void onShowOtherResult(String res__) {
                            tx.setVisibility(View.VISIBLE);
                            tx.setText(res__);
                        }
                    });
                }else if(modelll.getSuccess() !=null){
                    Toasta.makeText(context, R.string.post_sent, Toast.LENGTH_SHORT);
                    Prefs.putBoolean(LAST_POST_WAS_SENT, true);
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
            case R.id.public_p:
            case R.id.friends_p:
                setToDefault(id);
                break;
        }
    }


    public void setToDefault(int id){
        switch (id){
            case R.id.private_p:
                privacy=privacy_private;
                break;
            case R.id.public_p:
                privacy=privacy_public;
                break;
            case R.id.friends_p:
                privacy=privacy_friends;
                break;
        }
        privacyId=id;
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