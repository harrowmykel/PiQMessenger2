package ng.com.coursecode.piqmessenger.Statuses;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import br.com.goncalves.pugnotification.notification.PugNotification;
import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.FullScreenActivity;
import ng.com.coursecode.piqmessenger.ExtLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.File.CFile;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
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

public class CreateStatus extends FullScreenActivity implements View.OnClickListener {


    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 2345;
    private static final String LAST_POST_WAS_SENT = "fidfshfihfi";
    private static final String LAST_POST = "jdhjfhfddjlhlj";
    private static final String LAST_IMG = "hdfouddsfhu";
    Context context;
    EditText emojiconEditText;
    ImageView camera_Post;
    TextView counter;
    public static int NUMBER_OF_WORDS = 300;
    //    SharedPref sharedPref;
    String show_act = "show_act_snack";
    Uri tempUri = Uri.EMPTY;
    ImageView img;
    Intent intent;
    int small_icon=R.drawable.compose_status;
    final int NOT_INT = 2233;
    //so it is less than 0 when null;
    int len = -1;
    private String Status_Folder;


    Stores stores;
    String privacy="329393333";
    String recipient;
    String text;
    boolean isReady=false;//image is set

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_create_status);

        context = CreateStatus.this;
        stores=new Stores(context);

        Status_Folder=stores.getStatusFolder();
        counter = (TextView) findViewById(R.id.stat_amt);
        emojiconEditText = (EditText) findViewById(R.id.status_edit);
        camera_Post = img = (ImageView) findViewById(R.id.stat_img_p);

        TextView username=(TextView)findViewById(R.id.stat_sub);
        ImageView user_dp=(CircleImageView)findViewById(R.id.stat_img);
        String uiser=stores.getUsername();
        Users_prof users_prof=Users_prof.getInfo(context, uiser);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        username.setText(uiser);
        ((TextView)findViewById(R.id.stat_name)).setText(users_prof.getFullname());
        counter.setText("" + NUMBER_OF_WORDS);

        camera_Post.setOnClickListener(this);
        (findViewById(R.id.stat_reply)).setOnClickListener(this);
        emojiconEditText.setMaxEms(NUMBER_OF_WORDS);
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

        setDefaults();
    }

    private void setDefaults() {
        intent=getIntent();
        String defaultText= "";
        String defaultImgUrl="";
        if(intent!=null) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            defaultText=intent.getStringExtra(Intent.EXTRA_TEXT);
            if(uri!=null){
                defaultImgUrl=uri.toString();
//                tempUri=
            }
        }else if(!Prefs.getBoolean(LAST_POST_WAS_SENT, false)){
            defaultText= Prefs.getString(LAST_POST, "");
            defaultImgUrl=Prefs.getString(LAST_IMG, "");
        }
        emojiconEditText.setText(defaultText);
        if(!defaultImgUrl.isEmpty()){
            tempUri=Uri.parse(defaultImgUrl);
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img);
        }else{
            tempUri=ImageActivity.getLastImage(context);
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img);
        }
    }


    public void saveUp(){
        text=emojiconEditText.getText().toString();
        String urltoImage=tempUri.toString();

        Prefs.putString(LAST_POST, text);
        Prefs.putString(LAST_IMG, urltoImage);
        Prefs.putBoolean(LAST_POST_WAS_SENT, false);
    }

    public void sendToServer(String urltoImage){
        saveUp();
        text=emojiconEditText.getText().toString();
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.newStatus(stores.getUsername(), stores.getPass(), stores.getApiKey(), text, urltoImage);
        call.enqueue(new Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                final TextView tx=(TextView)findViewById(R.id.warning);
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

    public void validateBeforeSend() {
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
            Toasta.makeText(context, R.string.text_and_image_must_not_be_empty, Toast.LENGTH_SHORT);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.stat_img_p:
                //TO DO startCameraActivityForResult done
                showSelector();
                break;
            case R.id.stat_reply:
                validateBeforeSend();
                break;
        }
    }

    private void showSelector() {
        Intent intentq;
        intentq=new Intent(context, ImageActivity.class);
        intentq.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intentq, IMGREQUESTCODE);
    }

    @Override
    protected void onStop() {
        saveUp();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setFullscreen();
        if (resultCode == RESULT_OK) {
            isReady=true;
            tempUri = data.getData();
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img);
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
            Piccassa.load(context, R.drawable.ic_add_black_24dp, img);
        }
    }

    public void sendToGoogle() {
        saveUp();
        GoogleUpload googleUpload=new GoogleUpload(context, Stores.STATUS_STORE, NOT_INT, small_icon, tempUri, new GoogleUpload.GoogleUploadListener() {
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
}