package ng.com.coursecode.piqmessenger.Model__;

import com.google.gson.Gson;

import java.util.List;

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

    public static String toJson(Object model_lis) {
        return (new Gson()).toJson(model_lis);
    }
}
