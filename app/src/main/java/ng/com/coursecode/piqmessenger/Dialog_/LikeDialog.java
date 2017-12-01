package ng.com.coursecode.piqmessenger.Dialog_;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.sendData;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 01/12/2017.
 */

public class LikeDialog implements View.OnClickListener{

    sendData sendData;
    Context context;
    Dialog dialog;

    public LikeDialog(Context context, sendData sendData_) {
        this.context=context;
        sendData = sendData_;
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.likes);
        View view=dialog.findViewById(R.id.parent_view);

        View lLike=view.findViewById(R.id.l_like);
        View lLove=view.findViewById(R.id.l_love);
        View lHaha=view.findViewById(R.id.l_haha);
        View lWow=view.findViewById(R.id.l_wow);
        View lSad=view.findViewById(R.id.l_sad);
        View lAngry=view.findViewById(R.id.l_angry);
        
        lAngry.setOnClickListener(this);
        lHaha.setOnClickListener(this);
        lLike.setOnClickListener(this);
        lLove.setOnClickListener(this);
        lSad.setOnClickListener(this);
        lWow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.l_like:
                joyOut(Stores.POST_LIKE);
                break;
            case R.id.l_love:
                joyOut(Stores.POST_LOVE);
                break;
            case R.id.l_haha:
                joyOut(Stores.POST_HAHA);
                break;
            case R.id.l_wow:
                joyOut(Stores.POST_WOW);
                break;
            case R.id.l_sad:
                joyOut(Stores.POST_SAD);
                break;
            case R.id.l_angry:
                joyOut(Stores.POST_ANGRY);
                break;
        }
    }

    public void joyOut(String postAngry) {
        LikeDialog.playLikeSound(context);
        sendData.send(postAngry);
        dialog.dismiss();
    }

    public void show(){
        dialog.show();
    }

    public static void playLikeSound(Context context) {

    }
}