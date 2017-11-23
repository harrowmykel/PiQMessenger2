package ng.com.coursecode.piqmessenger;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

import br.com.goncalves.pugnotification.notification.PugNotification;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Fragments_.Posts;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfile extends AppCompatActivity {

    private static final String PROFILE_STORE = "rjfsnlknfskslfnifsk";
    public static String USERNAME="djkbzjriudbfudjbiud";
    boolean fromSavedState;
    String username_;
    Context context;

    TextView  username;
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
    private int small_icon=R.drawable.profile_add_photo;
    private String POST_FOLDER;//="solder/";
    boolean isReady=false;

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
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
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
                            public void onShowOtherResult(int res__) {
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
        if(tempUri!=Uri.EMPTY){
            sendToGoogle();
        }else{
            sendToServer();
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



    public void sendToServer() {
        if(tempUri==Uri.EMPTY){
            sendToServer();
        }else{
            sendToServer(tempUri.toString());
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
                        public void onShowOtherResult(int res__) {
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
        // File or Blob
        final Uri file = tempUri;

// Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(Stores.CLOUD_URL);

// Upload file and metadata to the path 'images/mountains.jpg'
        UploadTask uploadTask = storageRef.child(stores.getFirebaseStore(Stores.PROFILE_STORE) + file.getLastPathSegment()).putFile(file, metadata);

// Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), tempUri);

                    PugNotification.with(context)
                            .load()
                            .identifier(NOT_INT)
                            .title(R.string.uploading_status)
                            .smallIcon(small_icon)
                            .largeIcon(bitmap)
                            .progress()
                            .value(progress, 100, false)
                            .build();
                } catch (Exception e) {
                    Stores._reportException(e, "Createstatus", context);
                }
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                alert(R.string.upload_paused);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads

                alert(R.string.upload_failed);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                alert(R.string.upload_successful);
                sendToServer(downloadUrl.toString());
            }
        });
    }

    public void alert(final int Resid) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), tempUri);
            PugNotification.with(context)
                    .load()
                    .identifier(NOT_INT)
                    .title(Resid)
                    .smallIcon(small_icon)
                    .largeIcon(bitmap)
                    .flags(Notification.DEFAULT_ALL)
                    .simple()
                    .build();
        } catch (Exception e) {
            Stores._reportException(e, "editprof", context);
        }
    }
}