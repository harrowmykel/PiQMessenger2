package ng.com.coursecode.piqmessenger.fragments_;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.contacts_.StatusAct;
import ng.com.coursecode.piqmessenger.conversate.Converse;
import ng.com.coursecode.piqmessenger.database__.Status_tab;
import ng.com.coursecode.piqmessenger.dialog_.DeleteDialog;
import ng.com.coursecode.piqmessenger.dialog_.StatusPopUpmenu;
import ng.com.coursecode.piqmessenger.extLib.Pic_TextView;
import ng.com.coursecode.piqmessenger.extLib.PiccMaqFragment;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.extLib.staggeredgridviewdemo.views.ScaleImageView2;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.interfaces.ServerError;
import ng.com.coursecode.piqmessenger.model__.Model__;
import ng.com.coursecode.piqmessenger.model__.Model__3;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.posts_act.LikesAct;
import ng.com.coursecode.piqmessenger.posts_act.PostsAct;
import ng.com.coursecode.piqmessenger.retrofit__.ApiClient;
import ng.com.coursecode.piqmessenger.retrofit__.ApiInterface;
import ng.com.coursecode.piqmessenger.statuses.Show_Status;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by harro on 15/11/2017.
 */

public class StatusFragment2 extends PiccMaqFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_PARCEL = "hmnbfs.d,mnf.m";
    public static final String HAS_SEEN_DEF_STAT = "Jbedjfbjgj";

    //Declare timer
    CountDownTimer cTimer = null;

    Context context;

    String username_, status_code, fullname_, user_img_, thisUser = "", stry, type_of_action;
    int position = 0;
    int max = -1;
    int countdown;

    boolean showProg = true;
    boolean lngClicked = false;
    boolean imgLoaded = false;
    Stores store;
    ArrayList<Model__3> status_tabs;
    Model__3 messages;
    boolean thisUser_ = false;

    @BindView(R.id.stat_prog)
    ProgressBar statProg;
    @BindView(R.id.stat_img_p)
    ImageView statImgP;
    @BindView(R.id.stat_vid_p)
    VideoView statVidP;
    @BindView(R.id.stat_img)
    CircleImageView statImg;
    @BindView(R.id.stat_name)
    TextView statName;
    @BindView(R.id.stat_sub)
    TextView statSub;
    @BindView(R.id.stat_amt)
    TextView statAmt;
    @BindView(R.id.stat_more)
    ImageButton statMore;
    @BindView(R.id.stat_text)
    TextView statText;
    @BindView(R.id.stat_reply)
    MaterialFancyButton statReply;
    @BindView(R.id.stat_pic_prog)
    ProgressBar statPicProg;
    @BindView(R.id.stat_prev)
    View statPrev;
    @BindView(R.id.stat_next)
    View statNext;

    View rootView;

    Unbinder unbinder;
    private long totalTimeVid = 1000;
    private long totalTimeImg = 7000;
    long TotalTime = totalTimeImg;

    public StatusFragment2() {
    }

    /**
     * Returns a new instance of this PiccMaqFragment for the given section
     * number.
     */
    public static StatusFragment2 newInstance(Model__3 users_posts) {
        return newInstance(users_posts, Status_tab.FAV);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show__status, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getContext();
        store = new Stores(context);

        Bundle args = getArguments();
        Model__3 users_posts = args.getParcelable(ARG_PARCEL);
        type_of_action = args.getString(Show_Status.TYPE_);
        type_of_action = (type_of_action == null) ? "" : type_of_action;

        username_ = users_posts.getUsername();
        fullname_ = users_posts.getFullname();
        user_img_ = users_posts.getUser_img();

        status_tabs = users_posts.getStatData();
        String az = "@" + username_;

        thisUser = store.getUsername();
        thisUser_ = username_.equalsIgnoreCase(thisUser);

        setViewsUp();

        Piccassa.loadDp(context, user_img_, statImg);
        statSub.setText(az);
        statName.setText(fullname_);
        position = users_posts.getStartFrom();
        setImage(position);
        statNext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longIn();
                return false;
            }
        });
        statPrev.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longIn();
                return false;
            }
        });
        statReply.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                statImgP.performLongClick();
                return false;
            }
        });
        return rootView;
    }

    private void setViewsUp() {
        statProg.setMax(100);
        if (Status_tab.INTRO.equalsIgnoreCase(type_of_action) || (context.getString(R.string.app_name).equalsIgnoreCase(username_))) {
            statReply.setVisibility(View.GONE);
            statReply.setVisibility(View.INVISIBLE);
            Piccassa.loadDp(context, R.drawable.profile_btn_superlike, statImg);
        } else if (thisUser_) {
            statReply.setVisibility(View.VISIBLE);
            statReply.setText(R.string.view_users);
            statReply.setIconResource("\uf06e");
        }

    }

    private void longIn() {

        int type_Visi = View.GONE;
        if (lngClicked) {
            type_Visi = View.GONE;
        } else {
            type_Visi = View.VISIBLE;
        }

        statSub.setVisibility(type_Visi);
        statAmt.setVisibility(type_Visi);
        statName.setVisibility(type_Visi);
        statReply.setVisibility(type_Visi);
        statImg.setVisibility(type_Visi);
        if (!stry.isEmpty())
            statText.setVisibility(type_Visi);
        statProg.setVisibility(type_Visi);

        if (type_Visi == View.VISIBLE && imgLoaded) {
            startTimer();
        } else {
            cancelTimer();
        }

        if (Status_tab.INTRO.equalsIgnoreCase(type_of_action) || (context.getString(R.string.app_name).equalsIgnoreCase(username_))) {
            statReply.setVisibility(View.GONE);
            statReply.setVisibility(View.INVISIBLE);
        }

        lngClicked = !lngClicked;
    }

    private void goToViewUsers() {
        Intent intent = new Intent(context, LikesAct.class);
        intent.putExtra(StatusAct.STATUS_CODE, status_code);
        intent.putExtra(PostsAct.POSTID, status_code);
        intent.putExtra(LikesAct.TYPE_OF_ACTION, LikesAct.VIEWSTATUS);
        startActivity(intent);
    }

    ServerError serverError = new ServerError() {
        @Override
        public void onEmptyArray() {
        }

        @Override
        public void onShowOtherResult(String res__) {
        }
    };

    public void deleteStatus() {
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Model__> call = apiInterface.deleteStatus(store.getUsername(), store.getPass(), store.getApiKey(), status_code);

        call.enqueue(new retrofit2.Callback<Model__>() {
            @Override
            public void onResponse(Call<Model__> call, Response<Model__> response) {
                Model__ model_lisj = response.body();
                List<Model__> model_lis = model_lisj.getData();
                Model__ modelll = model_lis.get(0);

                if (modelll.getError() != null) {
                    store.handleError(modelll.getError(), context, serverError);
                } else if (modelll.getSuccess() != null) {
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
        SendDatum sendDatum_ = (SendDatum) context;
        if (sendDatum_ != null) {
            sendDatum_.send(fullscreen);
        }
    }

    private void setImage(int position) {
        this.position = position;
        max = status_tabs.size();
        countdown = max - position;
        imgLoaded = false;
        if (position < max && position > -1) {
            messages = status_tabs.get(position);
//         todo   messages.getTime()messages.getSeen()
            String img = messages.getImage();
            if (img != null) {
                statPicProg.setVisibility(View.VISIBLE);
                cancelTimer();
                stry = messages.getText();
                status_code = messages.getStatus_id();
                if (stry.trim().length() > 0) {
                    statText.setVisibility(View.VISIBLE);
                    statText.setText(stry);
                } else {
                    statText.setVisibility(View.GONE);
                }
                if (messages.getType().equalsIgnoreCase(Stores.VID)) {
                    statVidP.setVisibility(View.VISIBLE);
                    statImgP.setVisibility(View.GONE);
                    statVidP.setVideoURI(Uri.parse(img));
                    statVidP.start();
                    statVidP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            statPicProg.setVisibility(View.GONE);
                            TotalTime = totalTimeVid;
                            startTimer();
                            imgLoaded = true;
                            sendToActivity(status_code);
                        }
                    });
                    statVidP.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            statPicProg.setVisibility(View.GONE);
                            Toasta.makeText(context, R.string.unable_to_load_vid, Toast.LENGTH_SHORT);
                            return false;
                        }
                    });
                    statVidP.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            statPicProg.setVisibility(View.GONE);
                            calUP();
                        }
                    });
                } else {
                    statVidP.setVisibility(View.GONE);
                    statImgP.setVisibility(View.VISIBLE);
                    Piccassa.loadStatusFrag(context, Uri.parse(img), R.drawable.nosong, statImgP, new Callback() {
                        @Override
                        public void onSuccess() {
                            statPicProg.setVisibility(View.GONE);
                            startTimer();
                            imgLoaded = true;
                            sendToActivity(status_code);
                            calUP();
                        }

                        @Override
                        public void onError() {
                            statPicProg.setVisibility(View.GONE);
                            Toasta.makeText(context, R.string.unable_to_load_image, Toast.LENGTH_SHORT);
                        }
                    });
                }
                statAmt.setText(""+position+"/"+max);
            }
        } else {
            cancelTimer();
            this.position = max + 1;//so that it closes app
            sendToActivity(status_code);
            calUP();
        }
    }

    //start timer function
    void startTimer() {
        showProg = true;
        cancelTimer();
        cTimer = new CountDownTimer(TotalTime, 300) {
            public void onTick(long millisUntilFinished) {
                if (showProg) {
                    long prog = TotalTime - millisUntilFinished;
                    int prg = (int) ((prog * 100) / TotalTime);
                    statProg.setProgress(prg);
                }
                showProg = !showProg;
            }

            public void onFinish() {
                statProg.setProgress(100);
                setImage(position + 1);
                calUP();
                TotalTime = totalTimeImg;
            }
        };
        cTimer.start();
    }

    public void calUP() {
        if (position >= (max)) {
            sendToActivity(Show_Status.NEXT_STATUS);
        }
    }

    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    public void showMessage() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("");
        if (!(position < max && position > -1)) {
            position = max - 1;
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

    public static StatusFragment2 newInstance(Model__3 users_posts, String type) {
        StatusFragment2 fragment = new StatusFragment2();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARCEL, users_posts);
        args.putString(Show_Status.TYPE_, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //todo longIn();

    @OnClick({R.id.stat_prog, R.id.stat_img_p, R.id.stat_vid_p, R.id.stat_img, R.id.stat_name, R.id.stat_sub, R.id.stat_amt, R.id.stat_more, R.id.stat_text, R.id.stat_reply, R.id.stat_pic_prog, R.id.stat_prev, R.id.stat_next})
    public void onViewClicked(View view) {
        String postid;
        Intent intent;
        switch (view.getId()) {
            case R.id.stat_prog:
                break;
            case R.id.stat_img_p:
                break;
            case R.id.stat_vid_p:
                break;
            case R.id.stat_img:
                postid = username_;
                intent = new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, postid);
                startActivity(intent);
                break;
            case R.id.stat_name:
                break;
            case R.id.stat_sub:
                postid = username_;
                intent = new Intent(context, Profile.class);
                intent.putExtra(Profile.USERNAME, postid);
                startActivity(intent);
                break;
            case R.id.stat_amt:
                break;
            case R.id.stat_more:
                cancelTimer();
                (new StatusPopUpmenu(context, statMore, thisUser_, new SendDatum() {
                    @Override
                    public void send(Object object) {
                        //todo
                        String send=(String)object;
                        switch (send) {
                            case Show_Status.FULLSCREEN:
                                sendToActivity(Show_Status.FULLSCREEN);
                                startTimer();
                                break;
                            case Show_Status.VIEW_USER:
                                goToViewUsers();
                                break;
                            case Show_Status.DELETE:
                                deleteStatus();
                                break;
                        }
                    }
                })).show();
                break;
            case R.id.stat_text:
                showMessage();
                break;
            case R.id.stat_reply:
                if (thisUser_) {
                    goToViewUsers();
                } else {
                    postid = username_;
                    intent = new Intent(context, Converse.class);
                    intent.putExtra(Converse.USERNAME, postid);
                    startActivity(intent);
                }
                break;
            case R.id.stat_pic_prog:
                break;
            case R.id.stat_prev:
                position--;
                setImage(position);
                break;
            case R.id.stat_next:
                position++;
                setImage(position);
                break;
        }
    }
}
