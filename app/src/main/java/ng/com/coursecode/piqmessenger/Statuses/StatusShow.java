package ng.com.coursecode.piqmessenger.Statuses;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.File.CFile;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
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

public class StatusShow extends AppCompatActivity implements View.OnClickListener {


    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 2345;
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
    private String Status_Folder="status_folder/";


    Stores stores;
    String privacy="329393333";
    String recipient;
    String text;
    boolean isReady=false;//image is set

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = StatusShow.this;
        stores=new Stores(context);

        counter = (TextView) toolbar.findViewById(R.id.action_bar_subtitle);
        emojiconEditText = (EditText) findViewById(R.id.status_edit);
        camera_Post = img = (ImageView) findViewById(R.id.status_img_show);

        TextView username=(TextView)findViewById(R.id.action_bar_title);
        ImageView user_dp=(CircleImageView)findViewById(R.id.crt_dp);
        String uiser=stores.getUsername();
        Users_prof users_prof=Users_prof.getInfo(context, uiser);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        username.setText(users_prof.getFullname());

        counter.setText("" + NUMBER_OF_WORDS);

        camera_Post.setOnClickListener(this);
        (findViewById(R.id.ic_action_done)).setOnClickListener(this);
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

    }

    private void showSelector() {
        tempUri = CFile.getTempUri(CFile.IMG_DIR);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setItems(R.array.NewPost_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentq;
                switch (which) {
                    case 0:
                        CFile.setUp();
                        intentq = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentq.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(intentq, IMGREQUESTCODE);
                        break;
                    case 1:
                        intentq = new Intent(context, GifAct.class);
                        startActivityForResult(intentq, GIFREQUESTCODE);
                        break;
                    case 2:
                        intentq = new Intent(Intent.ACTION_PICK);
                        intentq.setType("image/*");
                        startActivityForResult(intentq, GALLERYREQUESTCODE);
                        //cancel
                        break;
                }
            }
        });
        alert.show();
    }


    public void sendToServer(String text, String urltoImage){
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
            sendToGoogle();
        }else{
            Toasta.makeText(context, R.string.text_and_image_must_not_be_empty, Toast.LENGTH_SHORT);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.status_img_show:
                //TO DO startCameraActivityForResult done
                showSelector();
                break;
            case R.id.ic_action_done:
                validateBeforeSend();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isReady=true;
            if (requestCode == IMGREQUESTCODE) {
                img.setImageURI(tempUri);
            }
            if (requestCode == GALLERYREQUESTCODE) {
                tempUri = data.getData();
                img.setImageURI(tempUri);
            }
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }


    public void sendToGoogle() {
        // File or Blob
        final Uri file = tempUri;

// Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://piccmaq-messenger.appspot.com");

// Upload file and metadata to the path 'images/mountains.jpg'
        UploadTask uploadTask = storageRef.child(Status_Folder + file.getLastPathSegment()).putFile(file, metadata);

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
                    .autoCancel(true)
                    .simple()
                    .build();
        } catch (Exception e) {
            Stores._reportException(e, "Createstatus", context);
        }
    }
}