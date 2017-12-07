package ng.com.coursecode.piqmessenger;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity; import ng.com.coursecode.piqmessenger.ExtLib.PiccMaqCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.File.CFile;
import ng.com.coursecode.piqmessenger.GifReplace.GifAct;
import ng.com.coursecode.piqmessenger.R;

public class ImageActivity extends PiccMaqCompatActivity implements View.OnClickListener{


    private static final int IMGREQUESTCODE = 234;
    private static final int GALLERYREQUESTCODE = 287;
    private static final int GIFREQUESTCODE = 1233;

    Uri tempUri=Uri.EMPTY;
    boolean isReady=false;
    ImageView img;
    Context context;

    FancyButton select, done, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        context=ImageActivity.this;
        select=(FancyButton)findViewById(R.id.select_image);
        done=(FancyButton)findViewById(R.id.action_done);
        cancel=(FancyButton)findViewById(R.id.cancel_action);
        img=(ImageView)findViewById(R.id.imageAct);

        select.setOnClickListener(this);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);
        showSelector();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.select_image:
                showSelector();
                break;
            case R.id.action_done:
                release(Activity.RESULT_OK);
                break;
            case R.id.cancel_action:
                release(Activity.RESULT_CANCELED);
                break;
        }
    }

    public void release(int td){
        Intent result = new Intent();
        result.setData(tempUri);
        setResult(td, result);
        finish();
    }

    private void showSelector() {
        tempUri= CFile.getTempUri(CFile.IMG_DIR);
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setItems(R.array.NewPost_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentq;
                switch(which){
                    case 0:
                        CFile.setUp();
                        intentq=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isReady=true;
            switch (requestCode){
                case IMGREQUESTCODE:
                    break;
                case  GALLERYREQUESTCODE:
                    tempUri = data.getData();
                    break;
                case GIFREQUESTCODE:
                    tempUri = data.getData();
                    break;
            }
            Piccassa.loadGlide(context, tempUri, R.drawable.going_out_add_status_plus, img);
        } else {
            Toasta.makeText(context, R.string.noImg, Toast.LENGTH_SHORT);
        }
    }
}
