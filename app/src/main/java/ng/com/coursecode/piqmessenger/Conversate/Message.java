package ng.com.coursecode.piqmessenger.Conversate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.ExtLib.GoogleUpload;
import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ImageActivity;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;
import ng.com.coursecode.piqmessenger.NetworkCalls.MessagesCall;
import ng.com.coursecode.piqmessenger.R;

public class Message extends PiccMaqCompatActivity implements MessageInput.InputListener,
        MessageInput.AttachmentsListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.messagesList)
    MessagesList messagesList;
    public static String USERNAME = "hdjaebjkjrslfaljdskd";
    public String username, recipient, privacy = "kf";

    Context context;
    List<Messages> messages_list = new ArrayList<>();
    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;
    Uri tempUri = Uri.EMPTY;
    boolean isReady = false;
    @BindView(R.id.input)
    MessageInput input;

    private int NOT_INT = 23178425;
    private int small_icon = R.drawable.profile_add_photo;
    private String POST_FOLDER;

    RecyclerView recyclerView;
    Stores stores;
    MaterialEditText materialEditText;
    TextView username_, subtitle;
    ImageView user_dp;

    Model__ user_data;
    MessagesListAdapter<Messages> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recipient = username = getIntent().getStringExtra(USERNAME);
        username = (username == null) ? "" : username;
        context = this;
        setTitle(username);
        stores=new Stores(context);
        input.setInputListener(this);
        input.setAttachmentsListener(this);
        startInit();
    }

    public void startInit() {
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                if(url!=null && !url.isEmpty()) {
                    Piccassa.load(context, url, R.drawable.user_sample, imageView);
                }
            }
        };

        MessageHolders holdersConfig = new MessageHolders();
        holdersConfig.setIncomingTextLayout(R.layout.item_incoming_text_message);
        holdersConfig.setOutcomingTextLayout(R.layout.item_outcoming_text_message);
        holdersConfig.setIncomingImageLayout(R.layout.item_incoming_image_message);
        holdersConfig.setOutcomingImageLayout(R.layout.item_outcoming_image_message);
        adapter = new MessagesListAdapter<>(username, holdersConfig, imageLoader);

        Messages messages = new Messages();
        messages_list = messages.listAllFromUser(context, username);
        adapter.addToEnd(messages_list, true);
        messagesList.setAdapter(adapter);
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


    public void sendToServer(String text) {
        String urltoImage = (tempUri != Uri.EMPTY) ? tempUri.toString() : "";
//        privacy
        Messages messages_ = new Messages();
        messages_.setImage(urltoImage);
        messages_.setRecip(recipient);
        messages_.setMess_age(text);
        messages_.setTim_e(TimeModel.getPhpTime());
        messages_.setTime_stamp(TimeModel.getPhpTime());
        messages_.setAuth(stores.getUsername());
        messages_.setmsg_id("hsdhd" + stores.getSTime());
        messages_.setSent(0);
        messages_.setContext(context);
        messages_.setAuthorUp(stores.getUsername(), stores);
        boolean fg = messages_.saveNew(context);
        tempUri = Uri.EMPTY;

        MessagesCall messagesCall = new MessagesCall(context);
        messagesCall.sendAllMessages();
        adapter.addToStart(messages_, true);
        messagesCall.getAllMessages();
    }

    private void showSelector() {
        Intent intentq;
        intentq = new Intent(context, ImageActivity.class);
        intentq.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(intentq, IMGREQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isReady = true;
            tempUri = data.getData();
            String urltoImage = tempUri.toString();

            if (tempUri != Uri.EMPTY && !stores.isExtUrl(urltoImage)) {
                sendToGoogle();
            } else {
                sendToServer("");
            }
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }

    public void sendToGoogle() {
        GoogleUpload googleUpload = new GoogleUpload(context, Stores.MSG_STORE, NOT_INT, small_icon, tempUri, new GoogleUpload.GoogleUploadListener() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(Uri url) {
                tempUri = url;
                sendToServer("");
            }
        });
        googleUpload.sendToGoogle();
    }


}
