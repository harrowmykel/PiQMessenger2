package ng.com.coursecode.piqmessenger.GifReplace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;


import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.R;

public class MediaViewer extends AppCompatActivity {

    public static final String DATA__ = "sjbdj";
    public static String DATA_= "jfkdbdjzkfbs";

    ScaleImageView imageView;
    VideoView videoView;
    View v;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_viewer);
        imageView=(ScaleImageView)findViewById(R.id.img_);
        String datat=getFile();
context=MediaViewer.this;
        Glide.with(context) // replace with 'this' if it's in activity
                .load(datat)
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mediaviewr, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_done:
//                copy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getFile() {
        return getIntent().getStringExtra(DATA__);
    }

    public Uri getUri() {
        return Uri.parse(getFile());
    }
}
