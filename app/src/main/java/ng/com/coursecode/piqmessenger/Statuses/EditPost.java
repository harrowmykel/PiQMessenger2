package ng.com.coursecode.piqmessenger.Statuses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Datum;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.PostsModel;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditPost extends PiccMaqCompatActivity implements View.OnClickListener {


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
    boolean isStillLoading=true;

    String postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=EditPost.this;

        postid=unNull(getIntent().getStringExtra(PostsAct.POSTID));
        recipient=unNull(getIntent().getStringExtra(PostsAct.RECIPIENT));

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
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
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
        replying.setText(R.string.edit_post);
        replying.setVisibility(View.VISIBLE);
        Ntwrkcall();
    }

    private String unNull(String stringExtra) {
        return (stringExtra==null)?"":stringExtra;
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

    public void validateBeforeSend() {
        text = emojiconEditText.getText().toString();
        if (!isStillLoading) {
            if (!text.isEmpty()) {
                Toasta.makeText(context, R.string.posting, Toast.LENGTH_SHORT);
                finish();
                sendToServer("");
            } else {
                Toasta.makeText(context, R.string.text_and_image_must_not_be_empty, Toast.LENGTH_SHORT);
            }
        }else {
            Toasta.makeText(context, R.string.loading, Toast.LENGTH_SHORT);
        }
    }

    public void sendToServer(String urltoImage){
        text=emojiconEditText.getText().toString();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__>  call = apiInterface.editPost(stores.getUsername(), stores.getPass(), stores.getApiKey(), text, privacy, postid);
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


    public void Ntwrkcall(){
        startLoader();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<PostsModel> call=apiInterface.getThisPosts(stores.getUsername(), stores.getPass(), stores.getApiKey(), postid);

        call.enqueue(new Callback<PostsModel>() {
            @Override
            public void onResponse(Call<PostsModel> call, final Response<PostsModel> response) {

                PostsModel model_lisj=response.body();
                List<Datum> model_list=model_lisj.getData();
                int num=model_list.size();

                for(int i=0; i<num; i++) {
                    Datum modelll = model_list.get(i);

                    if (modelll.getError() != null) {
                        stores.handleError(modelll.getError(), context, serverError);
                        closeLoader();
                        break;
                    }
                    emojiconEditText.setText(getString__(modelll.getSubtitle()));
                    String imglink=getString__(modelll.getImage());
                    if (!imglink.isEmpty()) {
                        Piccassa.load(context, imglink, img);
                    }else{
                        img.setVisibility(View.GONE);
                    }

                    isStillLoading=false;
                }
                closeLoader();
            }

            @Override
            public void onFailure(Call<PostsModel> call, Throwable t) {
                stores.reportThrowable(t, "postscall");
                closeLoader();
            }
        });
    }

    public void closeLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(View.GONE);
    }


    public void startLoader(){
        SmoothProgressBar smoothProgressBar=(SmoothProgressBar)findViewById(R.id.smooth_prog);
        smoothProgressBar.progressiveStart();
        smoothProgressBar.setVisibility(View.VISIBLE);
    }

    ServerError serverError=new ServerError() {
        @Override
        public void onEmptyArray() {

        }

        @Override
        public void onShowOtherResult(int res__) {
        }
    };

    private String getString__(String confirm) {
        return (confirm==null)?"":confirm;
    }

}