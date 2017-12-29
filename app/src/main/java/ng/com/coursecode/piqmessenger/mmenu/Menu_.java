package ng.com.coursecode.piqmessenger.mmenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ng.com.coursecode.piqmessenger.adapters__.ListAdapter_;
import ng.com.coursecode.piqmessenger.contacts_.ContactAct;
import ng.com.coursecode.piqmessenger.database__.Users_prof;
import ng.com.coursecode.piqmessenger.dialog_.LearnDialog;
import ng.com.coursecode.piqmessenger.EditProfile;
import ng.com.coursecode.piqmessenger.dialog_.PassChangeDialog;
import ng.com.coursecode.piqmessenger.extLib.PiccMaqCompatActivity;
import ng.com.coursecode.piqmessenger.extLib.Piccassa;
import ng.com.coursecode.piqmessenger.extLib.StartUp;
import ng.com.coursecode.piqmessenger.interfaces.SendDatum;
import ng.com.coursecode.piqmessenger.MainActivity;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.notifications_a.NotificationsAct;
import ng.com.coursecode.piqmessenger.posts_act.LikesAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;
import ng.com.coursecode.piqmessenger.searches.ConvoSearchAct;
import ng.com.coursecode.piqmessenger.searches.SearchAct;
import ng.com.coursecode.piqmessenger.settingss.SettingsActivity;
import ng.com.coursecode.piqmessenger.statuses.StatusAll;

public class Menu_ extends PiccMaqCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String PostId = "jdldn";
    public static final String ISUSERR = "jdkdlkd";
    public static String POSTIDID = "jdldn";
    boolean isUser = false;
    @BindView(R.id.prof_pic)
    CircleImageView profPic;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.fullname)
    TextView fullname;
    @BindView(R.id.this_user)
    LinearLayout thisUser;
    RecyclerView list1;
    RecyclerView list2;
    RecyclerView list3;
    LinearLayoutManager mLayoutManagerseen, mLayoutManagerseen2, mLayoutManagerseen3;
    Context context;
    String username_;

    int[] list1List1={R.string.edit_profile, R.string.friends, R.string.find_people, R.string.find_group, R.string.online_friends, R.string.online_members};
    int[] list1List2={R.string.games_and_fun, R.string.notifications, R.string.learn_how_to_use, R.string.settings, R.string.change_pass};//,R.string.birthday_celebrants, , R.string.spend_time_and_earn}R.string.create_avatar, R.string.help_translate_and_earn};
    int[] list1List3={R.string.home, R.string.messages, R.string.status, R.string.groups, R.string.help};

    int[] list1List1img={R.string.edit_profile, R.string.friends, R.string.online_friends};
    int[] list1List2img={R.string.games_and_fun, R.string.birthday_celebrants,  R.string.notifications};//, , R.string.spend_time_and_earn}R.string.create_avatar, R.string.help_translate_and_earn};
    int[] list1List3img={R.string.home, R.string.messages, R.string.groups, R.string.help};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        ButterKnife.bind(this);

        list1=(RecyclerView)findViewById(R.id.list1);
        list2=(RecyclerView)findViewById(R.id.list2);
        list3=(RecyclerView)findViewById(R.id.list3);

        setSupportActionBar(toolbar);
        context=Menu_.this;
        //context.getString()
        username_=(new Stores(context)).getUsername();

        Users_prof users_prof = Users_prof.getInfo(context, username_);
        Piccassa.loadDp(context, users_prof.getImage(), profPic);
        fullname.setText(users_prof.getFullname());
        username.setText(username_);

        ListAdapter_ adapter_1=new ListAdapter_(context, getIntArrAsString(list1List1), list1List1img, sendDatum_);
        if(Stores.flingEdit){
            list1.fling(Stores.flingVelX, Stores.flingVelY);
        }
        list1.setItemAnimator(new DefaultItemAnimator());
        mLayoutManagerseen = new LinearLayoutManager(context);
        list1.setLayoutManager(mLayoutManagerseen);
        list1.setAdapter(adapter_1);

        ListAdapter_ adapter_2=new ListAdapter_(context, getIntArrAsString(list1List2), list1List2img, sendDatum_);
        if(Stores.flingEdit){
            list2.fling(Stores.flingVelX, Stores.flingVelY);
        }
        list2.setItemAnimator(new DefaultItemAnimator());
        mLayoutManagerseen2 = new LinearLayoutManager(context);
        list2.setLayoutManager(mLayoutManagerseen2);
        list2.setAdapter(adapter_2);

        ListAdapter_ adapter_=new ListAdapter_(context, getIntArrAsString(list1List3), list1List3img, sendDatum_);
        if(Stores.flingEdit){
            list3.fling(Stores.flingVelX, Stores.flingVelY);
        }
        list3.setItemAnimator(new DefaultItemAnimator());
        mLayoutManagerseen3 = new LinearLayoutManager(context);
        list3.setLayoutManager(mLayoutManagerseen3);
        list3.setAdapter(adapter_);

        (findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(R.string.log_out).setMessage(R.string.log_out_sure).setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton(R.string.log_out, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        (new StartUp(context)).end();
                        checkLogined();
                    }
                }).show();
            }
        });
        (findViewById(R.id.this_user)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Profile.class));
            }
        });
    }

    public ArrayList<Integer> getIntArrAsString(int[] arr){
        ArrayList<Integer> arrs=new ArrayList<>();
        for(int arr_int:arr) {
            arrs.add(arr_int);
        }
        return arrs;
    }

    SendDatum sendDatum_ =new SendDatum() {
        @Override
        public void send(Object object) {
            int clicked=(Integer) object;
            Intent intent=null;
            switch (clicked){
                case R.string.edit_profile:
                    intent=new Intent(context, EditProfile.class);
                    break;
                case R.string.friends:
                case R.string.find_people:
                    intent=new Intent(context, ContactAct.class);
                    break;
                case R.string.online_friends:
                    intent=new Intent(context, LikesAct.class);
                    intent.putExtra(LikesAct.TYPE_OF_ACTION, LikesAct.ONLINE_FRIENDS);
                    break;
                case R.string.online_members:
                    intent=new Intent(context, LikesAct.class);
                    intent.putExtra(LikesAct.TYPE_OF_ACTION, LikesAct.ONLINE_MEMBERS);
                    break;
                case R.string.learn_how_to_use:
                case R.string.help:
                    (new LearnDialog(context)).show();
                    break;
                case R.string.change_pass:
                    (new PassChangeDialog(context)).show();
                    break;
                case R.string.settings:
                    intent=new Intent(context, SettingsActivity.class);
                    break;
                case R.string.birthday_celebrants:
                    intent=new Intent(context, LikesAct.class);
                    intent.putExtra(LikesAct.TYPE_OF_ACTION, LikesAct.BIRTHDAY);
                    break;
                case R.string.notifications:
                    intent=new Intent(context, NotificationsAct.class);
                    break;
                case R.string.games_and_fun:
                    break;
                case R.string.home:
                case R.string.groups:
                    intent=new Intent(context, MainActivity.class);
                    intent.putExtra(Stores.TYPE_OF_ACTION, clicked);
                    break;
                case R.string.status:
                    intent=new Intent(context, StatusAll.class);
                    intent.putExtra(Stores.TYPE_OF_ACTION, clicked);
                    break;
                case R.string.spend_time_and_earn:
                    break;
                case R.string.create_avatar:
                    break;
                case R.string.help_translate_and_earn:
                    break;
                case R.string.messages:
                    intent=new Intent(context, ConvoSearchAct.class);
                    break;
                case R.string.find_group:
                    intent=new Intent(context, SearchAct.class);
                    intent.putExtra(Stores.TYPE_OF_ACTION, R.string.groups);
                    break;
            }
            if(intent!=null)
            startActivity(intent);
        }
    };
}
