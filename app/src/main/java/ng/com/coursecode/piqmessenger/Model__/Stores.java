package ng.com.coursecode.piqmessenger.Model__;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;
import ng.com.coursecode.piqmessenger.Database__.Messages;
import ng.com.coursecode.piqmessenger.ExtLib.Toasta;
import ng.com.coursecode.piqmessenger.Interfaces.ServerError;
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
    public static int flingVelX=1;
    public static int flingVelY=2;
    public static boolean flingEdit=true;
    public static String imgDefault="jhfsbhjb";
    public static int scrollChange=5;
    public static String Results="klfdnkflhgjknk";
    public static String toSkip="Jbdfjkfbjkfbfj";
    public static String TopicEND="ending";

    public static String POST_LOVE = "2636hb363";
    public static String POST_LIKE = "393k99fd5";
    public static String POST_SAD = "393k995fdsf";
    public static String POST_WOW = "393k995fafk";
    public static String POST_ANGRY = "393krkjhj995";
    public static String POST_HAHA = "393k9rgrr95";
    public static String POST_NONE = "0";
    public static int initView= View.GONE;
    public static List<Integer> serviceError=new ArrayList<>();

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
    private String username="Piccmaq";
    private String pass="08036660086";
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
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getTime() {
        return "1";// "" + System.currentTimeMillis();
    }

    public static String SearchQuery ="searchQuerysjjsjsl";

    public static String CurrentPage ="CurrentPage";

    public void handleError(String error, Context context, ServerError serverError) {
        switch(error){
            case "6479":
                serverError.onEmptyArray();
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
        if(!type.equals("0")){
            dp=Stores.getLikeSymbol();
        }else{
            dp=R.drawable.ic_favorite_border_black_24dp;
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
        return ""+Prefs.getLong(msgcall, 0);
    }

    public String getFirebaseStore(String profileStore) {
        DateTime dateTime=DateTime.today(TimeZone.getDefault());
        DateTime dateTime1=new DateTime(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), 0, 0, 0, 0);
        return profileStore+dateTime1.getMilliseconds(TimeZone.getDefault())+"/";
    }

    public boolean isExtUrl(String urltoImage) {
        return urltoImage.toLowerCase().contains("tenor");
    }
}
