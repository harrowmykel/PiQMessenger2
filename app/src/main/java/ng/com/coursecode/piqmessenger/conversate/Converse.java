package ng.com.coursecode.piqmessenger.conversate;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import butterknife.BindView;
import butterknife.ButterKnife;
import ng.com.coursecode.piqmessenger.dialog_.MsgLongClickDialog;
import ng.com.coursecode.piqmessenger.dialog_.SearchDialog;
import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.stfalcon.chatkit.messages.MessageInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.adapters__.ConvoActAdapter;
import ng.com.coursecode.piqmessenger.database__.Messages;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.extLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.extLib.onVerticalScrollListener;
import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.ImageActivity;
import ng.com.coursecode.piqmessenger.fragments_.Chats;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.networkcalls.MessagesCall;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Converse extends PiccMaqCompatActivity implements MessageInput.InputListener,
        MessageInput.AttachmentsListener{

    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;
    public static final String LAST_READ = "converse_LAST_READ";
    public static final String LAST_READ_USER = "converse_LAST_READ_USER";
    public static final int LONG_CLICK = 233;
    Uri tempUri=Uri.EMPTY;
    boolean isReady=false;

    private int NOT_INT=23178425;
    private int small_icon=R.drawable.profile_add_photo;
    private String POST_FOLDER;

    public static String USERNAME="hdjaebjkjrslfaljdskd";
    public String username, recipient, privacy="kf";
    Context context;
    List<Messages> messages_lista, messages_list=new ArrayList<>();
    RecyclerView recyclerView;
    Stores stores;
    //    MaterialEditText materialEditText;
    TextView username_,subtitle;
    ImageView user_dp;

    Model__ user_data;
    @BindView(R.id.input)
    MessageInput input;
    ScaleImageView img_prvw;
    private boolean nt_reversed=true;
    int last_dp_time=10;
    Messages messages;

    public SendDatum sendData=new SendDatum(){
        @Override
        public void send(Object object) {
            int refresh=(int)object;
            if(refresh==Chats.REFRESH){
                reset();
            }else if(refresh==R.string.action_search){
                (new SearchDialog(context, new SendDatum() {
                    @Override
                    public void send(Object object) {
                        String search=(String)object;
                        if(search.equals(""+Chats.REFRESH)){
                            reset();
                        }else{
                            messages_list = messages.listAllFromUser(context, username, search);
                            nt_reversed = true;
                            dopeAll();
                        }
                    }
                })).show();
            }
        }
    };

    private void reset() {
        (new MessagesCall(context)).getAllMessages();
        Toasta.makeText(context, R.string.refresh, Toast.LENGTH_SHORT);
        messages_list = messages.listAllFromUser(context, username);
        nt_reversed=true;
        dopeAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converse);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=Converse.this;
        stores=new Stores(context);

        POST_FOLDER=stores.getFolderMSG();

        recyclerView=(RecyclerView)findViewById(R.id.main_recycle);
        img_prvw=(ScaleImageView)findViewById(R.id.img_prvw);

        recipient=username=getIntent().getStringExtra(USERNAME);
        username=(username==null)?"":username;

        username_=(TextView)findViewById(R.id.action_bar_title);
        subtitle=(TextView)findViewById(R.id.action_bar_subtitle);
        user_dp=(CircleImageView)findViewById(R.id.crt_dp);
//        materialEditText=(MaterialEditText)findViewById(R.id.msg_edit);

        input.setInputListener(this);
        input.setAttachmentsListener(this);

        user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, username);
                startActivity(intent);
            }
        });
        (findViewById(R.id.action_more)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgLongClickDialog msgLongClickDialog=new MsgLongClickDialog(context, username, sendData);
                msgLongClickDialog.show();
            }
        });
        Users_prof users_prof=Users_prof.getInfo(context, username);
        Piccassa.loadDp(context, users_prof.getImage(), user_dp);
        username_.setText(users_prof.getFullname());
        subtitle.setText(R.string.offline);

        recipient=username;
        messages = new Messages();
        messages_list = messages.listAllFromUser(context, username);
        dopeAll();
        setMessageReceiver(context, mMessageReceiver);
        listenToBroadCast(Chats.REFRESH_NEW_MESSAGE);
    }

    private void dopeAll() {
        startInit();
        setUpProfile();
        messages.setReadAll(context, username);
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String action=intent.getAction();
            if(action.equalsIgnoreCase(Chats.REFRESH_NEW_MESSAGE)){
                dopeAll();
            }
        }
    };

    private void startInit() {
        messages_lista=messages_list;
        if(nt_reversed) {
            Collections.reverse(messages_lista);
            nt_reversed=false;
        }

        ConvoActAdapter convoActAdapter=new ConvoActAdapter(messages_lista, context, new SendDatum() {
            @Override
            public void send(Object object) {
                int[] recieve=(int[])object;
                if(recieve!=null) {
                    //todo alert dialog delete terxt
                    int position=recieve[1];
                    if (recieve[0] == LONG_CLICK) {
                        Messages msg=messages_lista.get(position);
                        if(msg!=null) {
                            MsgLongClickDialog msgLongClickDialog=new MsgLongClickDialog(context, msg.getMess_age(), msg.getId(), msg.getmsg_id(), sendData);
                            msgLongClickDialog.show();
                        }
                    }
                }
            }
        }, recipient);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        if(Stores.flingEdit)
            recyclerView.fling(Stores.flingVelX, Stores.flingVelY);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(createInfiniteScrollListener());
        recyclerView.setAdapter(convoActAdapter);
        convoActAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition((messages_list.size()-1));
    }

    private onVerticalScrollListener createInfiniteScrollListener() {
        return new onVerticalScrollListener(){
            @Override
            public void onScrolledToTop() {
                super.onScrolledToTop();
            }

        };
    }

    public void sendToServer(String text){
        text= input.getInputEditText().getText().toString();
        String urltoImage=(tempUri!=Uri.EMPTY)?tempUri.toString():"";
//        privacy
        Messages messages_=new Messages();
        messages_.setImage(urltoImage);
        messages_.setRecip(recipient);
        messages_.setMess_age(text);
        messages_.setTim_e(TimeModel.getPhpTime2());
        messages_.setTime_stamp(TimeModel.getPhpTime());
        messages_.setAuth(stores.getUsername());
        messages_.setmsg_id("hsdhd"+stores.getSTime());
        messages_.setSent(0);
        messages_.setContext(context);
        boolean fg=messages_.saveNew(context);

        input.getInputEditText().setText("");
        tempUri=Uri.EMPTY;

        messages_list.add(messages_);
        MessagesCall messagesCall=new MessagesCall(context);
        messagesCall.sendAllMessages();
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

            if(tempUri!=Uri.EMPTY && !stores.isExtUrl(urltoImage)){
                sendToGoogle();
            }else{
                sendToServer("");
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
                sendToServer("");
            }
        });
        googleUpload.sendToGoogle();
    }


    private void setUpProfile() {
        if(Prefs.getString(LAST_READ_USER, "1a").equalsIgnoreCase(username)){
            last_dp_time = Prefs.getInt(LAST_READ, 1);
        }
        Retrofit retrofit = ApiClient.getClient();
        stores = new Stores(context);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<Model__> call=apiInterface.getUserDM(stores.getUsername(), stores.getPass(), stores.getApiKey(), recipient, ""+last_dp_time);

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
                    user_data = model_list.get(0);
                    final TextView tx = (TextView)findViewById(R.id.warning);
                    tx.setVisibility(Stores.initView);
                    if (user_data.getError() != null) {
                        stores.handleError(user_data.getError(), context, new ServerError() {
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
                        String user_name = user_data.getAuth_username();
                        String image=user_data.getAuth_data().getAuth_img();
                        String fullnames=user_data.getAuth_data().getFullname();
                        String online=user_data.getOnline();
                        String last_read=user_data.getLast_read();
                        String last_rcvd=user_data.getLast_rcvd();

                        (new Messages(context)).setLastRead(user_name, last_rcvd, Stores.RCVD_MSG);
                        (new Messages(context)).setLastRead(user_name, last_read, Stores.READ_MSG);

                        if(online.trim().equalsIgnoreCase("1")){
                            online = getString(R.string.online);
                        }else{
                            online=(new TimeModel(context)).getDWM(online);
                            online = getString(R.string.last_seen, online);
                        }
                        subtitle.setText(online);

                        username_.setText(fullnames);
                        Piccassa.loadDp(context, image, user_dp);
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
    public void onAddAttachments() {
        showSelector();
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        sendToServer(input.toString());
        this.input.getInputEditText().setText("");
        return false;
    }

}
