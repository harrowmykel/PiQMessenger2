package ng.com.coursecode.piqmessenger.ExtLib;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by harro on 02/12/2017.
 */

public class FullScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setFullscreen();
        super.onCreate(savedInstanceState);
    }

    public void setFullscreen() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onResume() {
        setFullscreen();
        super.onResume();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        setFullscreen();
        super.onActivityReenter(resultCode, data);
    }
}
