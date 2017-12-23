package ng.com.coursecode.piqmessenger.Model__;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import hirondelle.date4j.DateTime;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
import ng.com.coursecode.piqmessenger.NotificationsA.NotificationsAct;
import ng.com.coursecode.piqmessenger.PostsAct.PostsAct;
import ng.com.coursecode.piqmessenger.Profile;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 10/10/2017.
 */

public class Stores {

    public static final String[] TENOR = {"tenor", "giphy"};
    public static final String USERNAME = "jkdas;nfdzngsdjfgnjd";
    public static final String PROFILE_STORE = "profile/";
    public static final String STATUS_STORE = "status/";
    public static final String CLOUD_URL = "gs://piccmaq-messenger.appspot.com";
    public static final String POST_STORE = "posts/";
    public static final String MSG_STORE = "fkdlnfeskl;nr";
    public static final long WAIT_PERIOD = 200;//for handlers
    public static final String TYPE_OF_ACTION = "korwedgfgc";
    public static final String DELETE_FRND = "delete_frnd";
    public static final String ACCEPT_FRND = "accept_frnd";
    public static final String SEND_FRND = "send_frnd";
    public static final long SUB_REFRESH_TIME = 1000*60*60*24*3;//each 3 days
    public static final String DISCOVER = "Knrknfkr";
    public static final String IMG = "jfvjlkld";
    public static final String VID = "jfvjfklflkld";
    public static final String PLAY_LIKE = "Jendjfnjfknj";
    public static final String REFRESH_ACTIVITY = "Knfknfknk";
    public static final String EDIT = "jkfj";
    public static final String DEFAULT_STAT = "iorklenl;tkwrnlr4830439ewrhdiofwuhfodb";
    public static final String REFRESH_ACTIVITY_STATUS = "hjdbfjkf";
    public static final String REFRESH_ACTIVITY_GROUP = "fjfjksdlj";
    public static final String REFRESH_ACTIVITY_POST = "fhdskbfsjdbkfsj";
    public static final String NOTIFICATION_STRING = "Idniknfinfoin";
    public static final String NOTIFICATION_DISP_ID = "Ikjdjdijidp";
    public static final String APP_FIREBASE_TOPIC = "nefskdnkgldfndkfsnkvdndknfkdndfndld";
    public static final String REFRESH_NEW_ACT = "erjdfsyunfk";
    public static final String UNREAD_MSG = "w";
    public static final String READ_MSG = "r";
    public static int flingVelX=1;
    public static int flingVelY=2;
    public static boolean flingEdit=true;
    public static String imgDefault="jhfsbhjb";
    public static int scrollChange=5;
    public static String Results="klfdnkflhgjknk";
    public static String toSkip="Jbdfjkfbjkfbfj";
    public static String TopicEND="ending";

    public static final String POST_LOVE = "2636hb363";
    public static final String POST_LIKE = "393k99fd5";
    public static final String POST_SAD = "393k995fdsf";
    public static final String POST_WOW = "393k995fafk";
    public static final String POST_ANGRY = "393krkjhj995";
    public static final String POST_HAHA = "393k9rgrr95";
    public static final String POST_NONE = "0";
    public static int initView= View.GONE;
    public static List<String> serviceError=new ArrayList<>();
    public static String GroupTopicEND="endgroup";

    public List<Messages> db_result;


    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";


    public static int dbReqCounts=14;
    Context context;
    private String apiKey="dcbafhjh";
    public boolean useInternet=false;
    public boolean useList=false;
    public boolean useList2=true;
    public static boolean suseInternet=false;
    public static boolean suseList=false;
    public static boolean suseList2=true;

    public Stores(Context context_) {
        context=context_;
    }

    public String getUsername() {
        return Prefs.getString(Profile.USERS_NAME, "");
    }

    public String getPass() {
        return Prefs.getString(Profile.USERS_PASS, "");
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getTime() {
        return "1";// "" + System.currentTimeMillis();
    }
    public String getSTime() {
        return "" + System.currentTimeMillis();
    }

    public static String SearchQuery ="searchQuerysjjsjsl";

    public static String CurrentPage ="CurrentPage";

    public void handleError(String error, Context context, ServerError serverError) {
        switch(error){
            case "6479":
                serverError.onEmptyArray();
                break;
            default:
                serverError.onShowOtherResult(context.getString(R.string.acc_req));
                break;
        }
    }

    public void reportException(Exception r) {
        Toasta.makeText(context, r.getLocalizedMessage(), Toast.LENGTH_SHORT);
    }

    public void reportException(Exception r, String location) {
        Toasta.makeText(context, r.getLocalizedMessage(), Toast.LENGTH_SHORT);
    }

    public void reportThrowable(Throwable t, String location) {
        Toasta.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT);
    }

    public static void _handleError(String error, Context context) {
        Toasta.makeText(context, "error", Toast.LENGTH_SHORT);
    }

    public  static void _reportException(Exception r, Context context) {
        Toasta.makeText(context, r.getLocalizedMessage(), Toast.LENGTH_SHORT);
    }

    public  static void _reportException(Exception r, String location, Context context) {
        Toasta.makeText(context, r.getLocalizedMessage(), Toast.LENGTH_SHORT);
    }

    public  static void _reportThrowable(Throwable t, String location, Context context) {
        Toasta.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT);

    }

    public static String ucWords(String str){
        //capitalize each word
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = " ";
            if(s.length()>0)
                cap=" "+s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    public static String limitDb(int total, int req, int cur_page){
        cur_page=(cur_page<2)?1:cur_page;
        int skip = ((cur_page - 1) * req);
        int num_pages = (total / req);
        return ""+skip+", "+req;
    }

    public static int parseInt(String any) {
        any=(any.equals(""))?"0":any;
        int pgLeft=0;
        try{
            pgLeft=Integer.parseInt(any);
        }catch (Exception e){

        }
        return pgLeft;
    }


    public List<Messages> getDb_result() {
        return db_result;
    }

    public void setDb_result(List<Messages> db_result) {
        this.db_result = db_result;
    }

    public static String[] getSuggestions(Context context, String contacts) {
        return context.getResources().getStringArray(R.array.query_suggestions);
    }

    public static void saveSuggestions(Context context, String contacts, String query) {

    }
    public static String[] getSuggestions(Context context, int contacts) {
        return context.getResources().getStringArray(R.array.query_suggestions);
    }

    public static void saveSuggestions(Context context, int contacts, String query) {

    }

    public String getGifApiKey() {
        return "UKZN7XUS106M";
    }

    public String getUserDp() {
        return "jdknfjfnl";
    }

    public static int random(int i, int i1) {
        Random r = new Random();
        int i10 = r.nextInt(i1 - i) + i;
        return i10;
    }

    public static int getLikeSymbol(){
        int[] LikeSymbols={R.drawable.message_liked, R.drawable.chat_heart_full};
        int gh=Stores.random(0, 2);
        return LikeSymbols[gh];
    }

    public static int getLikeImageRes(String type) {
        int dp;

        switch (type){
            case POST_LOVE:
                dp=R.drawable.love;
                break;
            case POST_LIKE:
                dp=R.drawable.like;
                break;
            case POST_SAD:
                dp=R.drawable.sad;
                break;
            case POST_WOW:
                dp=R.drawable.wow;
                break;
            case POST_ANGRY:
                dp=R.drawable.angry;
                break;
            case POST_HAHA:
                dp=R.drawable.haha;
                break;
            case POST_NONE:
                dp=R.drawable.ic_favorite_border_black_24dp;
                break;
            default:
                dp=Stores.getLikeSymbol();
                break;
        }
        return dp;
    }

    public String getModelString(ArrayList<Model__2> messagesseen) {
        return (new Gson()).toJson(messagesseen);
    }

    public String getFolderMSG() {
        return "dhsjfvkfhfjj/";
    }

    public String getFolderPosts() {
        return "solder/";
    }

    public String getStatusFolder() {
        return "statuse/";
    }

    public String getTime(String msgcall) {
        return ""+(Prefs.getLong(msgcall, 6)-6);
    }

    public String getFirebaseStore(String profileStore) {
        DateTime dateTime=DateTime.today(TimeZone.getDefault());
        DateTime dateTime1=new DateTime(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), 0, 0, 0, 0);
        return profileStore+dateTime1.getMilliseconds(TimeZone.getDefault())+"/";
    }

    public boolean isExtUrl(String urltoImage) {
        return (urltoImage.toLowerCase().contains("tenor") ||  urltoImage.toLowerCase().contains("giphy"));
    }

    public String getGiphyApiKey() {
        return "e7b7fe5398b44c60a82bd68bfc82f3e5";
    }

    public static String notZero(String s) {
        return (s.equalsIgnoreCase("0"))?"":s;
    }

    public static String notZero(int s) {
        return (s<1 )?"":""+s;
    }

    public static boolean isTrue(String frndsData) {
        frndsData=(frndsData==null)?"0":frndsData;
        return frndsData.trim().equalsIgnoreCase("1");
    }

    public Uri getResUri(int vid_id) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(vid_id)
                + '/' + context.getResources().getResourceTypeName(vid_id) +
                '/' + context.getResources().getResourceEntryName(vid_id));
    }

    public Uri getRawResUri(int vid_id) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/"+vid_id);
    }

    public static Intent getIntentNotif(Context context, String not_id) {
        Intent intent;
        int not_int=232;
        int ResString=R.string.sent_req;
        switch (not_id){
            case "sent_req":
                intent=new Intent(context, Profile.class);
                ResString=R.string.sent_req;
                not_int=2321;
                break;
            case "mention_req":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.mention_req;
                not_int=222321;
                break;
            case "comment_like":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.comment_like;
                not_int=22232;
                break;
            case "comment_req":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.comment_req;
                not_int=22232;
                break;
            case "add_reply":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.add_reply;
                not_int=2223212;
                break;
            case "acc_req":
                intent=new Intent(context, Profile.class);
                ResString=R.string.acc_req;
                not_int=221221;
                break;
            case "add_like":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.add_like;
                not_int=220221;
                break;
            case "new_post_by_admin":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.new_post_by_admin;
                not_int=229321;
                break;
            case "new_post_r":
                intent=new Intent(context, PostsAct.class);
                ResString=R.string.new_post_r;
                not_int=2223212;
                break;
            case "send_piccoin":
                intent=new Intent(context, Profile.class);
                ResString=R.string.send_piccoin;
                not_int=22321;
                break;
            case "check_prof":
                intent=new Intent(context, Profile.class);
                ResString=R.string.check_prof;
                not_int=22221;
                break;
            default:
                intent=new Intent(context, NotificationsAct.class);
                ResString=R.string.new_notification;
                not_int=22290;
                break;
        }

        intent.putExtra(NOTIFICATION_STRING, ResString);
        intent.putExtra(NOTIFICATION_DISP_ID, not_int);
        return intent;
    }


    public static RecivData getRecivNotif(String not_id) {
        int not_int=232;
        int ResString=R.string.sent_req;
        switch (not_id){
            case "sent_req":
                ResString=R.string.sent_req;
                not_int=2321;
                break;
            case "mention_req":
                ResString=R.string.mention_req;
                not_int=222321;
                break;
            case "comment_like":
                ResString=R.string.comment_like;
                not_int=22232;
                break;
            case "comment_req":
                ResString=R.string.comment_req;
                not_int=22232;
                break;
            case "add_reply":
                ResString=R.string.add_reply;
                not_int=2223212;
                break;
            case "acc_req":
                ResString=R.string.acc_req;
                not_int=221221;
                break;
            case "add_like":
                ResString=R.string.add_like;
                not_int=220221;
                break;
            case "new_post_by_admin":
                ResString=R.string.new_post_by_admin;
                not_int=229321;
                break;
            case "new_post_r":
                ResString=R.string.new_post_r;
                not_int=2223212;
                break;
            case "send_piccoin":
                ResString=R.string.send_piccoin;
                not_int=22321;
                break;
            case "check_prof":
                ResString=R.string.check_prof;
                not_int=22221;
                break;
            default:
                ResString=R.string.new_notification;
                not_int=22290;
                break;
        }

        return new RecivData(ResString, not_int);
    }

    public void reportServiceException(Exception r, String mFirename) {

    }

    public static void addCircleBorder(CircleImageView status_dp, Context context, boolean b) {
        int valueInPixels = (int) context.getResources().getDimension(R.dimen.zero);
        if (b) {
            status_dp.setBorderColor(ContextCompat.getColor(context, R.color.border_color_));
            valueInPixels = (int) context.getResources().getDimension(R.dimen.border_width);
            status_dp.setBorderWidth(valueInPixels);
        }
        status_dp.setBorderWidth(valueInPixels);
    }

    public static void addCircleBorderMsg(CircleImageView status_dp, Context context, boolean thrown) {
        int valueInPixels = (int) context.getResources().getDimension(R.dimen.zero);
        if (thrown) {
            status_dp.setBorderColor(ContextCompat.getColor(context, R.color.border_color_2));
            valueInPixels = (int) context.getResources().getDimension(R.dimen.border_width);
            status_dp.setBorderWidth(valueInPixels);
        }
        status_dp.setBorderWidth(valueInPixels);
    }
}
