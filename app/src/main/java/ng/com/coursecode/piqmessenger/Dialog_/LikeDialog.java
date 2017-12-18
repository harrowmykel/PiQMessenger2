package ng.com.coursecode.piqmessenger.Dialog_;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import com.pixplicity.easyprefs.library.Prefs;

import ng.com.coursecode.piqmessenger.Interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 01/12/2017.
 */

public class LikeDialog implements View.OnClickListener{

    SendDatum SendDatum;
    Context context;
    Dialog dialog;

    public LikeDialog(Context context, SendDatum sendDatum_) {
        this.context=context;
        SendDatum = sendDatum_;
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
        SendDatum.send(postAngry);
        dialog.dismiss();
    }

    public void show(){
        dialog.show();
    }

    public static void playLikeSound(Context context) {
        if(Prefs.getBoolean(Stores.PLAY_LIKE, true)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.balloon_pop);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mpo) {
                    mpo.release();
                }
            });
        }
    }
}
