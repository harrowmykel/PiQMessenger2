package ng.com.coursecode.piqmessenger.Fragments_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.VideoView;

import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Contacts_.StatusAct;
import ng.com.coursecode.piqmessenger.Conversate.Converse;
import ng.com.coursecode.piqmessenger.Database__.Status_tab;
import ng.com.coursecode.piqmessenger.ExtLib.Piccassa;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.ExtLib.staggeredgridviewdemo.views.ScaleImageView2;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.Interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.Model__.Model__;
import ng.com.coursecode.piqmessenger.Model__.Model__3;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.PostsAct.LikesAct;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.Retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.Statuses.Show_Status;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 15/11/2017.
 */

public class StatusFragment  extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_PARCEL = "hmnbfs.d,mnf.m";
    public static final String HAS_SEEN_DEF_STAT = "Jbedjfbjgj";

    TextView stat_username, stat_name, stat_amt, stat_text;
    CircleImageView stat_dp;
    ImageView stat_img;
    VideoView stat_vid;
    View nxt, prev, stat_menu;
    MaterialFancyButton stat_reply;
    View rootView;
    //Declare timer
    CountDownTimer cTimer = null;

    Context context;

    String username_, status_code, fullname_, user_img_, thisUser="", stry, type_of_action;
    int position=0;
    int max=-1;
    int countdown;
    ProgressBar ringProgress, img_ring;
    boolean showProg=true;
    boolean lngClicked=false;
    boolean imgLoaded=false;
    Stores store;
    ArrayList< Model__3> status_tabs;
     Model__3 messages;
    boolean thisUser_=false;
    private long totalTimeVid=1000;
    private long totalTimeImg=7000;
    long TotalTime=totalTimeImg;

    public StatusFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StatusFragment newInstance(Model__3 users_posts) {
        return newInstance(users_posts, Status_tab.FAV);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show__status, container, false);
        context=getContext();
        store=new Stores(context);

        Bundle args = getArguments();
        Model__3 users_posts=args.getParcelable(ARG_PARCEL);
        type_of_action=args.getString(Show_Status.TYPE_);
        type_of_action=(type_of_action==null)?"":type_of_action;

        username_=users_posts.getUsername();
        fullname_=users_posts.getFullname();
        user_img_=users_posts.getUser_img();

        status_tabs=users_posts.getStatData();
        String az="@"+ username_;

        thisUser=store.getUsername();
        thisUser_=username_.equalsIgnoreCase(thisUser);

        setViewsUp();

        Piccassa.load(context, Uri.parse(user_img_), R.drawable.user_sample, stat_dp);
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
        stat_reply=(MaterialFancyButton) rootView.findViewById(R.id.stat_reply);
        stat_dp=(CircleImageView)rootView.findViewById(R.id.stat_img);
        stat_img=(ScaleImageView2)rootView.findViewById(R.id.stat_img_p);
        stat_vid=(VideoView)rootView.findViewById(R.id.stat_vid_p);
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
                longIn();
                return false;
            }
        });

        prev.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longIn();
                return false;
            }
        });

        stat_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid = username_;
                Intent intent = new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, postid);
                startActivity(intent);
            }
        });

        stat_username.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String postid = username_;
                                                 Intent intent = new Intent(context, Profile.class);
                                                 intent.putExtra(Profile.USERNAME, postid);
                                                 startActivity(intent);

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
                if(thisUser_){
                    goToViewUsers();
                }else{
                    String postid = username_;
                    Intent intent = new Intent(context, Converse.class);
                    intent.putExtra(Converse.USERNAME, postid);
                    startActivity(intent);
                }
            }
        });

        if(Status_tab.INTRO.equalsIgnoreCase(type_of_action) || (context.getString(R.string.app_name).equalsIgnoreCase(username_))){
            stat_reply.setVisibility(View.GONE);
            stat_reply.setVisibility(View.INVISIBLE);
            Piccassa.load(context, R.drawable.profile_btn_superlike, stat_dp);
        }else if(thisUser_){
            stat_reply.setVisibility(View.VISIBLE);
            stat_reply.setText(R.string.view_users);
            stat_reply.setIconResource("\uf06e");
        }

        stat_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                PopupMenu popupMenu=new PopupMenu(context, v);
                if(thisUser_){
                    popupMenu.getMenuInflater().inflate(R.menu.menu_status_act, popupMenu.getMenu());
                }else{
                    popupMenu.getMenuInflater().inflate(R.menu.menu_report, popupMenu.getMenu());
                }
                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        sendToActivity(Show_Status.FULLSCREEN);
                        startTimer();
                    }
                });
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_delete:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setTitle(R.string.action_delete).setMessage(R.string.delete_confirm)
                                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteStatus();
                                    }
                                }).show();
                                break;
                            case R.id.action_view:
                                goToViewUsers();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void longIn() {

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
        if(!stry.isEmpty())
            stat_text.setVisibility(type_Visi);
        ringProgress.setVisibility(type_Visi);

        if(type_Visi==View.VISIBLE && imgLoaded){
            startTimer();
        }else{
            cancelTimer();
        }

        if(Status_tab.INTRO.equalsIgnoreCase(type_of_action) || (context.getString(R.string.app_name).equalsIgnoreCase(username_))){
            stat_reply.setVisibility(View.GONE);
            stat_reply.setVisibility(View.INVISIBLE);
        }

        lngClicked=!lngClicked;
    }

    private void goToViewUsers() {
        Intent intent=new Intent(context, LikesAct.class);
        intent.putExtra(StatusAct.STATUS_CODE, status_code);
        intent.putExtra(PostsAct.POSTID, status_code);
        intent.putExtra(LikesAct.TYPE_OF_ACTION, LikesAct.VIEWSTATUS);
        startActivity(intent);
    }

    ServerError serverError=new ServerError() {
        @Override
        public void onEmptyArray() {
        }

        @Override
        public void onShowOtherResult(String res__) {
        }
    };

    public void deleteStatus(){
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call = apiInterface.deleteStatus(store.getUsername(), store.getPass(), store.getApiKey(), status_code);

        call.enqueue(new retrofit2.Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj=response.body();
                List<Model__> model_lis=model_lisj.getData();
                Model__ modelll=model_lis.get(0);

                if(modelll.getError()!=null) {
                    store.handleError(modelll.getError(), context, serverError);
                }else if(modelll.getSuccess() !=null){
                    sendToActivity(Show_Status.FINISH_);
                }
            }

            @Override
            public void onFailure(Call<Model__> call, Throwable t) {
                store.reportThrowable(t, "contactlist");
            }
        });
    }

    private void sendToActivity(String fullscreen) {
        SendDatum sendDatum_ =(SendDatum)context;
        if(sendDatum_ !=null){
            sendDatum_.send(fullscreen);
        }
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
                stry=messages.getText();
                status_code=messages.getStatus_id();
                if(stry.trim().length()>0){
                    stat_text.setVisibility(View.VISIBLE);
                    stat_text.setText(stry);
                }else {
                    stat_text.setVisibility(View.GONE);
                }
                if(messages.getType().equalsIgnoreCase(Stores.VID)){
                    stat_vid.setVisibility(View.VISIBLE);
                    stat_img.setVisibility(View.GONE);
                    stat_vid.setVideoURI(Uri.parse(img));
                    stat_vid.start();
                    stat_vid.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            img_ring.setVisibility(View.GONE);
                            TotalTime=totalTimeVid;
                            startTimer();
                            imgLoaded=true;
                            sendToActivity(status_code);
                        }
                    });
                    stat_vid.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            img_ring.setVisibility(View.GONE);
                            Toasta.makeText(context, R.string.unable_to_load_vid, Toast.LENGTH_SHORT);
                            return false;
                        }
                    });
                    stat_vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            img_ring.setVisibility(View.GONE);
                        }
                    });
                }else{
                    stat_vid.setVisibility(View.GONE);
                    stat_img.setVisibility(View.VISIBLE);
                    Piccassa.loadStatusFrag(context, Uri.parse(img), R.drawable.nosong, stat_img, new Callback() {
                        @Override
                        public void onSuccess() {
                            img_ring.setVisibility(View.GONE);
                            startTimer();
                            imgLoaded=true;
                            sendToActivity(status_code);
                        }

                        @Override
                        public void onError() {
                            img_ring.setVisibility(View.GONE);
                            Toasta.makeText(context, R.string.unable_to_load_image, Toast.LENGTH_SHORT);
                        }
                    });
                }
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
                if(position>=(max)){
                    sendToActivity(Show_Status.NEXT_STATUS);
                }
                TotalTime=totalTimeImg;
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
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sendToActivity(Show_Status.FULLSCREEN);
            }
        });
        alert.show();
    }

    public static StatusFragment newInstance(Model__3 users_posts, String type) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARCEL, users_posts);
        args.putString(Show_Status.TYPE_, type);
        fragment.setArguments(args);
        return fragment;
    }
}
