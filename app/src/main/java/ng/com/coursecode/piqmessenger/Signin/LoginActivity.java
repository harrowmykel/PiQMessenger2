package ng.com.coursecode.piqmessenger.Signin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.ExtLib.FullScreenActivity;
import ng.com.coursecode.piqmessenger.MainActivity;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends FullScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_login);
        (findViewById(R.id.log_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignActivity.class);
                intent.putExtra(SignActivity.IS_LOGIN, true);
                startActivity(intent);
                finish();
            }
        });
        (findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignActivity.class);
                intent.putExtra(SignActivity.IS_LOGIN, false);
                startActivity(intent);
                finish();
            }
        });
    }
}
