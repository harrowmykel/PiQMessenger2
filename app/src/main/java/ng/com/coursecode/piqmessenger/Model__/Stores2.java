package ng.com.coursecode.piqmessenger.Model__;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.google.gson.Gson;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.List;

import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 18/10/2017.
 */

public class Stores2 {
    static String ending__="endd";

    public static String fullname="fullname"+ending__;
    public static String status_id="status_id"+ending__;
    public static String time="time"+ending__;
    public static String text="text"+ending__;

    public static String user_name="user_name"+ending__;
    public static String user_id="user_id"+ending__;
    public static String msg_id="msg_id"+ending__;
    public static String mess_age="mess_age"+ending__;
    public static String tim_e="tim_e"+ending__;
    public static String time_stamp="time_stamp"+ending__;
    public static String auth="auth"+ending__;
    public static String confirm="confirm"+ending__;
    public static String image="image"+ending__;
    public static String recip="recip"+ending__;
    public static String seen="seen"+ending__;
    public static String messagesTable="messagesTable"+ending__;
    public static String statusTable="statusTable"+ending__;

    public static String id_="id_"+ending__;
    public static String group_id="group_id"+ending__;
    public static String groupTable="groupTable"+ending__;
    public static String friends="friends"+ending__;
    public static String profTable="profTable"+ending__;
    public static String postsTable="postsTable"+ending__;
    public static String posts_id="posts_id"+ending__;
    public static String discover_id="discover_id"+ending__;
    public static String discoverTable="discoverTable"+ending__;
    public static String fav="fav"+ending__;
    public static String liked="liked"+ending__;

    public static String reciv="reciv"+ending__;
    public static String likes="likes"+ending__;
    public static String Comment="comment"+ending__;
    public static String time_db="time_db"+ending__;
    public static String sent="sent"+ending__;

    public static String toJson(Object model_lis) {
        return (new Gson()).toJson(model_lis);
    }



    public static void setFrndText(MaterialFancyButton users_frnd, FrndsData frndsData, Context context) {
        int icon= R.string.add_friends;
        int iconRes=R.string.fawi_user_plus;
        int color=R.color.privacy_selected;
        int textColor=R.color.white;
        if(users_frnd==null){
            return;
        }else if(frndsData==null){
            users_frnd.setVisibility(View.GONE);
        }else{
            if(frndsData.getRFrnds()){
                icon=R.string.friends;
                iconRes=R.string.fawi_users;
                color=R.color.friends_reqq;
            }else if(frndsData.getRRcvd()){
                icon=R.string.accept_request;
                iconRes=R.string.fawi_user_plus;
//                color=R.color.black;
            }else if (frndsData.getRSent()){
                icon=R.string.request_sent;
                iconRes=R.string.fawi_check_square_o;
//                color=R.color.black;
            }else{
                icon=R.string.send_request;
                iconRes=R.string.fawi_user_plus;
                color=R.color.send_reqq;
                textColor=R.color.sendtext;
            }
            users_frnd.setIcon(context.getString(iconRes));
            users_frnd.setText(context.getString(icon));
//            users_frnd.setTextColor(textColor);
//            users_frnd.setBorderColor(textColor);
            users_frnd.setIconColor(textColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                users_frnd.setBackgroundColor(context.getResources().getColor(color, context.getTheme()));
            }else{
                users_frnd.setBackgroundColor(context.getResources().getColor(color));
            }
        }
    }
    public static FrndsData getFrndsData(FrndsData frndsData) {
        if(frndsData==null){
            frndsData=new FrndsData("0", "0", "0");
        }
        FrndsData frndsData1;
        if(frndsData.getRFrnds()){
            //delete frndship
            frndsData1=new FrndsData("0", "0", "0");
        }else if(frndsData.getRRcvd()){
            //accept
            frndsData1=new FrndsData("0", "0", "1");
        }else if (frndsData.getRSent()){
            //delete
            frndsData1=new FrndsData("0", "0", "0");
        }else {
            //send
            frndsData1=new FrndsData("1", "0", "0");
        }
        return frndsData1;
    }
}
