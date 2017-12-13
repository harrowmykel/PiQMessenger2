package ng.com.coursecode.piqmessenger.Conversate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ng.com.coursecode.piqmessenger.R;

public class Message extends AppCompatActivity implements IMessage{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.messagesList)
    MessagesList messagesList;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);
        messagesList.setAdapter(adapter);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public IUser getUser() {
        return null;
    }

    @Override
    public Date getCreatedAt() {
        return null;
    }
}
