package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Contacts_.StatusAct;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView;
import ng.com.coursecode.piqmessenger.Model__. Model__3;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.R;



/**
 * Created by harro on 15/11/2017.
 */

public class StatusFragment  extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_PARCEL = "hmnbfs.d,mnf.m";

    TextView stat_username, stat_name, stat_amt, stat_text;
    CircleImageView stat_dp;
    ImageView stat_img;
    View nxt, prev, stat_reply, stat_menu;
    View rootView;
    //Declare timer
    CountDownTimer cTimer = null;

    Context context;

    String username_, status_code;
    String fullname_;
    String user_img_;
    int position=0;
    int max=-1;
    int countdown;
    ProgressBar ringProgress, img_ring;
    long TotalTime=7000;
    boolean showProg=true;
    boolean lngClicked=false;
    boolean imgLoaded=false;
    Stores store;
    ArrayList< Model__3> status_tabs;
     Model__3 messages;

    public StatusFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StatusFragment newInstance( Model__3 users_posts) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARCEL, users_posts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show__status, container, false);
        context=getContext();
        store=new Stores(context);

        Bundle args = getArguments();
         Model__3 users_posts=args.getParcelable(ARG_PARCEL);

        username_=users_posts.getUsername();
        fullname_=users_posts.getFullname();
        user_img_=users_posts.getUser_img();

        status_tabs=users_posts.getStatData();

        setViewsUp();
        String az="@"+ username_;

        Piccassa.load(context, user_img_, R.drawable.user_sample, stat_dp);
        stat_username.setText(az);
        stat_name.setText(fullname_);
        setImage(position);
        return rootView;
    }

    private void setViewsUp() {
        stat_username=(TextView)rootView.findViewById(R.id.stat_sub);
        stat_amt=(TextView)rootView.findViewById(R.id.stat_amt);
        stat_name=(TextView)rootView.findViewById(R.id.stat_name);
        stat_text=(TextView)rootView.findViewById(R.id.stat_text);
        stat_reply=rootView.findViewById(R.id.stat_reply);
        stat_dp=(CircleImageView)rootView.findViewById(R.id.stat_img);
        stat_img=(ScaleImageView)rootView.findViewById(R.id.stat_img_p);
        nxt=rootView.findViewById(R.id.stat_next);
        prev=rootView.findViewById(R.id.stat_prev);
        stat_menu=rootView.findViewById(R.id.stat_more);
        ringProgress=(ProgressBar)rootView.findViewById(R.id.stat_prog);
        img_ring=(ProgressBar)rootView.findViewById(R.id.stat_pic_prog);
        ringProgress.setMax(100);

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                setImage(position);
            }
        });

        nxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int type_Visi=View.GONE;
                if(lngClicked){
                    type_Visi=View.GONE;
                }else{
                    type_Visi=View.VISIBLE;
                }

                stat_username.setVisibility(type_Visi);
                stat_amt.setVisibility(type_Visi);
                stat_name.setVisibility(type_Visi);
                stat_reply.setVisibility(type_Visi);
                stat_dp.setVisibility(type_Visi);
                stat_text.setVisibility(type_Visi);

                if(type_Visi==View.VISIBLE && imgLoaded){
                    startTimer();
                }else{
                    cancelTimer();
                }

                lngClicked=!lngClicked;
                return false;
            }
        });

        stat_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                setImage(position);
            }
        });
        stat_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid=username_;
                Intent intent=new Intent(context, Converse.class);
                intent.putExtra(Converse.USERNAME, postid);
                startActivity(intent);
            }
        });

        stat_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                PopupMenu popupMenu=new PopupMenu(context, v);
                if(username_.equalsIgnoreCase(store.getUsername())){
                    popupMenu.getMenuInflater().inflate(R.menu.menu_status_act, popupMenu.getMenu());
                }else{
                    popupMenu.getMenuInflater().inflate(R.menu.menu_status_act_, popupMenu.getMenu());
                }
                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        startTimer();
                    }
                });
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_delete:
                                break;
                            case R.id.action_view:
                                Intent intent=new Intent(context, StatusAct.class);
                                intent.putExtra(StatusAct.STATUS_CODE, status_code);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void setImage(int position) {
        this.position=position;
        max=status_tabs.size();
        countdown=max-position;
        imgLoaded=false;
        if(position<max && position>-1){
            messages=status_tabs.get(position);

//            messages.getTime()messages.getSeen()

            String img= messages.getImage();
            if(img!=null){
                img_ring.setVisibility(View.VISIBLE);
                cancelTimer();
                String stry=messages.getText();
                status_code=messages.getStatus_id();
                if(stry.trim().length()>0){
                    stat_text.setVisibility(View.VISIBLE);
                    stat_text.setText(stry);
                }else {
                    stat_text.setVisibility(View.GONE);
                }
                Piccassa.loadStatusFrag(context, img, R.drawable.nosong, stat_img, new Callback() {
                    @Override
                    public void onSuccess() {
                        img_ring.setVisibility(View.GONE);
                        startTimer();
                        imgLoaded=true;
                    }

                    @Override
                    public void onError() {
                        img_ring.setVisibility(View.GONE);
                        Toasta.makeText(context, R.string.unable_to_load_image, Toast.LENGTH_SHORT);
                    }
                });
                stat_amt.setText(""+countdown);
            }
        }else{
            cancelTimer();
        }
    }

    //start timer function
    void startTimer() {
        showProg=true;
        cancelTimer();
        cTimer = new CountDownTimer(TotalTime, 300) {
            public void onTick(long millisUntilFinished) {
                if (showProg) {
                    long prog= TotalTime-millisUntilFinished;
                    int prg=(int)((prog*100)/TotalTime);
                    ringProgress.setProgress(prg);
                }
                showProg=!showProg;
            }
            public void onFinish() {
                ringProgress.setProgress(100);
                setImage(position+1);
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    public  void showMessage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("");
        if(!(position<max && position>-1)){
            position=max-1;
        }
        alert.setMessage(messages.getText());
        alert.show();
    }
}
