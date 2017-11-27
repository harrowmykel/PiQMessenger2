package ng.com.coursecode.piqmessenger.Conversate;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import br.com.goncalves.pugnotification.notification.PugNotification;
import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Adapters__.ConvoActAdapter;
import ng.com.coursecode.piqmessenger.Adapters__.ConvoAdapter;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.Database__.Users_prof;
import ng.com.coursecode.piqmessenger.ExtLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.File.CFile;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.ImageActivity;
import ng.com.coursecode.piqmessenger.Interfaces.ConvoInterface;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Converse extends AppCompatActivity {
    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;
    Uri tempUri=Uri.EMPTY;
    boolean isReady=false;

    private int NOT_INT=23178425;
    private int small_icon=R.drawable.profile_add_photo;
    private String POST_FOLDER;

    public static String USERNAME="hdjaebjkjrslfaljdskd";
    public String username, recipient, privacy="kf";
    Context context;
    List<Messages> messages_list=new ArrayList<>();
    RecyclerView recyclerView;
    Stores stores;
    MaterialEditText materialEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=Converse.this;
        stores=new Stores(context);

        POST_FOLDER=stores.getFolderMSG();

        recyclerView=(RecyclerView)findViewById(R.id.main_recycle);
        recipient=username=getIntent().getStringExtra(USERNAME);
        username=(username==null)?"":username;

        TextView username_=(TextView)findViewById(R.id.action_bar_title);
        ImageView user_dp=(CircleImageView)findViewById(R.id.crt_dp);
        materialEditText=(MaterialEditText)findViewById(R.id.msg_edit);
        user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, username);
                startActivity(intent);
            }
        });

        (findViewById(R.id.attach_msg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelector();
            }
        });

        (findViewById(R.id.send_msg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
            }
        });

        Users_prof users_prof=Users_prof.getInfo(context, username);
        Piccassa.load(context, users_prof.getImage(), R.drawable.user_sample, user_dp);
        username_.setText(users_prof.getFullname());

        startInit();
    }

    private void startInit() {

        Messages messages = new Messages();
        messages_list = messages.listAllFromUser(context, username);

        ConvoActAdapter convoActAdapter=new ConvoActAdapter(messages_list, context, new ConvoInterface() {
            @Override
            public void startConvoAct(int position) {

            }

            @Override
            public void startProfileAct(int position) {

            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        if(Stores.flingEdit)
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(createInfiniteScrollListener());
        recyclerView.setAdapter(convoActAdapter);
        convoActAdapter.notifyDataSetChanged();

        MessagesCall messagesCall=new MessagesCall(context);
        messagesCall.getAllMessages();
    }



    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToTop() {
                super.onScrolledToTop();
            }

        };
    }

    public void sendToServer(){
        String text= materialEditText.getText().toString();
        String urltoImage=tempUri!=Uri.EMPTY?tempUri.toString():"";
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call = apiInterface.newMsg(stores.getUsername(), stores.getPass(), stores.getApiKey(), text, urltoImage, privacy, recipient);
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
                }

                materialEditText.setText("");
                tempUri=Uri.EMPTY;
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                (new Stores(context)).reportThrowable(t, "converse");
            }
        });

        MessagesCall messagesCall=new MessagesCall(context);
        messagesCall.getAllMessages();
        startInit();
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
            String urltoImage=tempUri.toString();

            if(stores.isExtUrl(urltoImage)){
                sendToServer();
            }else{
                sendToGoogle();
            }
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }

    public void sendToGoogle() {
        GoogleUpload googleUpload=new GoogleUpload(context, Stores.MSG_STORE, NOT_INT, small_icon, tempUri, new GoogleUpload.GoogleUploadListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Uri url) {
                tempUri=url;
                sendToServer();
            }
        });
        googleUpload.sendToGoogle();
    }
}
