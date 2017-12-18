package ng.com.coursecode.piqmessenger.Signin;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ng.com.coursecode.piqmessenger.ExtLib.FullScreenActivity;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends FullScreenActivity {

    @BindView(R.id.stat_vid_p)
    VideoView statVidP;
    @BindView(R.id.idf)
    ImageView idf;
    @BindView(R.id.idfo)
    TextView idfo;
    @BindView(R.id.log_in)
    TextView logIn;
    @BindView(R.id.sign_up)
    TextView signUp;
    @BindView(R.id.bttm)
    LinearLayout bttm;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context=LoginActivity.this;
        statVidP.setVideoURI((new Stores(context)).getRawResUri(R.raw.login_video));
        statVidP.start();
        statVidP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                statVidP.setVideoURI((new Stores(context)).getRawResUri(R.raw.login_video));
                statVidP.start();
            }
        });

        (findViewById(R.id.log_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                intent.putExtra(SignActivity.IS_LOGIN, true);
                startActivity(intent);
                finish();
            }
        });
        (findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                intent.putExtra(SignActivity.IS_LOGIN, false);
                startActivity(intent);
                finish();
            }
        });
    }
}
