package ng.com.coursecode.piqmessenger.database__;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.model__.Model__3;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.Stores2;
import ng.com.coursecode.piqmessenger.model__.TimeModel;
import ng.com.coursecode.piqmessenger.R;

/**
 * Created by harro on 10/10/2017.
 */

public class Status_tab {

    public static final String UNSEEN = "UNSEEN";
    public static final String FAV = "FAV";
    public static final String SEEN = "SEEN";
    public static final String CREATE = "create";
    public static final String INTRO = "heidkhifr";
    public String user_name;
    
    public String status_id="fskjbjtjktslj;tbobos";
    public String time;
    public String text;
    public String image;
    public String seen;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    //DB_Aro dbHelper;
    Context context;
    Cursor cursor;
    public String fav;
    private String fullname;
    String user_image;
    String auth;
    private String type;
    int startFrom;
    
    public Status_tab(){

    }

    public Status_tab(String user_name, String status_id,
                      String time, String text, String image, String seen){
        setUser_name(user_name);
        setStatus_id(status_id);
        setTime(time);
        setText(text);
        setImage(image);
        setSeen(seen);
    }

    public boolean save(Context context1) {
        context = context1;
        if (context != null) {
            // dbHelper = DB_Aro.getHelper(context);
            wrtable = DB_Aro.getWDb(context);
            contentValues = new ContentValues();
            contentValues.put(Stores2.user_name, getUser_name().toLowerCase());
            contentValues.put(Stores2.status_id, getStatus_id());
            contentValues.put(Stores2.time, getTime());
            contentValues.put(Stores2.time_db, TimeModel.getLongPhpTime(getTime()));
            contentValues.put(Stores2.text, getText());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.type, getType());
            contentValues.put(Stores2.seen, getSeen());
            contentValues.put(Stores2.sent, "0");
            contentValues.put(Stores2.fav, getFav());

            String toFind=Stores2.status_id + " = ?";
            String sTable=Stores2.statusTable;
            String[] sArray=new String[]{getStatus_id()};
            Cursor result = wrtable.query(
                    sTable,
                    null,
                    toFind,
                    sArray,
                    null,
                    null,
                    null
            );
            if (result != null && result.getCount() > 0) {
                String[] sf=sArray;
                //no need to update
//                wrtable.update(sTable, contentValues, toFind, sf);
                result.close();
            }else{
                wrtable.insert(sTable, null, contentValues);
            }

            return true;
        }
        return false;
    }

    public ArrayList<Model__3> listAll(Context context_) {
        context = context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
        wrtable=DB_Aro.getWDb(context);

        wrtable.delete(Stores2.statusTable, Stores2.time_db+" < ?", new String[]{""+TimeModel.aDayAgo_()});
        ArrayList<Model__3> statuses = new ArrayList<>();

        String[] projection = {Stores2.user_name};

        String selection = null;
        String[] selectionArgs = {};
        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.time + " DESC";

        cursor = rdbleDb.query(true, Stores2.statusTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,                                 // The sort order
                ""
        );
//        wrtable.close();

        return  listFromCursor(cursor);
    }

    public ArrayList<Model__3> listAllSearch(Context context_, String query) {
        context = context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb =wrtable= DB_Aro.getWDb(context);
        wrtable.delete(Stores2.statusTable, Stores2.time_db+" < ?", new String[]{""+TimeModel.aDayAgo_()});

        String[] projection = {Stores2.user_name};
        query="%"+query+"%";
        String selection = Stores2.user_name + " LIKE ?";
        String[] selectionArgs = {query};
        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.time + " DESC";

        cursor = rdbleDb.query(true, Stores2.statusTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,                                 // The sort order
                ""
        );
        return  listFromCursor(cursor);
    }
    
    public ArrayList<Model__3> listFromCursor(Cursor cursor){

        ArrayList<Model__3> statuses = new ArrayList<>();
        ArrayList<String> names=new ArrayList<>();
        int num = cursor.getCount();
//all usernames are listed distinctly
        for (int i = 0; i < num; i++) {
            cursor.moveToPosition(i);

            auth = cursor.getString(cursor.getColumnIndex(Stores2.user_name));
            if(names.contains(auth)){
                continue;
            }else{
                names.add(auth);
            }

            String[] projection1 = {Stores2.user_name,
                    Stores2.status_id,
                    Stores2.time,
                    Stores2.text,
                    Stores2.fav,
                    Stores2.type,
                    Stores2.image,
                    Stores2.seen};

            String selection1 = Stores2.user_name + " = ?";
            String[] selectionArgs1 = {auth};
            // How you want the results sorted in the resulting Cursor
            String sortOrder1 = Stores2.time + " ASC";
            rdbleDb=DB_Aro.getWDb(context);
            Cursor cursor1 = rdbleDb.query(Stores2.statusTable,  // The table to query
                    projection1,                               // The columns to return
                    selection1,                                // The columns for the WHERE clause
                    selectionArgs1,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder1                                 // The sort order
            );

            int num1 = cursor1.getCount();
            Model__3 model__3 = new Model__3();

            Users_prof users_prof = Users_prof.getInfo(context, auth);
            fullname = users_prof.getFullname();
            user_image = users_prof.getImage();
            model__3.setFullname(fullname);
            model__3.setUser_img(user_image);
            ArrayList<Model__3> status_tab_list = new ArrayList<>();

            boolean seen_ = true;
            boolean unseen_set=false;
            boolean fav_ = false;
            startFrom=0;

            //all post from a user are listed
            for (int i1 = 0; i1 < num1; i1++) {
                cursor1.moveToPosition(i1);
                Model__3 status_tab = new Model__3();
                status_id = cursor1.getString(cursor1.getColumnIndex(Stores2.status_id));
                time = cursor1.getString(cursor1.getColumnIndex(Stores2.time));
                text = cursor1.getString(cursor1.getColumnIndex(Stores2.text));
                image = cursor1.getString(cursor1.getColumnIndex(Stores2.image));
                fav = cursor1.getString(cursor1.getColumnIndex(Stores2.fav));
                type = cursor1.getString(cursor1.getColumnIndex(Stores2.type));

                status_tab.setStatus_id(status_id);
                status_tab.setTime(time);
                status_tab.setText(text);
                status_tab.setImage(image);
                status_tab.setType(type);

                status_tab.setFullname(fullname);
                status_tab.setUser_image(user_image);

                seen = cursor1.getString(cursor1.getColumnIndex(Stores2.seen));
                seen_ = seen_ && seen.equals("1");
                fav_ = fav_ || (!fav.equals("0"));
                status_tab_list.add(status_tab);
                if(!seen_ && !unseen_set){
                    unseen_set=true; 
                    startFrom=i1;
                    
                }
            }

            model__3.setSeen(seen_);
            model__3.setStatFav(fav_);
            model__3.setUsername(auth);
            model__3.setStartFrom(startFrom);
            model__3.setStatData(status_tab_list);
            statuses.add(model__3);

            cursor1.close();
        }
        cursor.close();
//        wrtable.close();
//        //dbHelper.close();
        return statuses;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public void delete(Context context_, String string__) {
        context = context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String selection = Stores2.status_id + " = ?";
        String[] selectionArgs = {string__};

        rdbleDb.delete(Stores2.statusTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public void viewed(SQLiteDatabase rdbleDb, String string__) {

        String selection = Stores2.status_id + " = ?";
        String[] selectionArgs = {string__};

        contentValues = new ContentValues();
        contentValues.put(Stores2.seen, "1");

        rdbleDb.update(Stores2.statusTable, contentValues, selection, selectionArgs);
    }

    public void delete(Context context_) {
        context = context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String selection = null;
        String[] selectionArgs = null;

        rdbleDb.delete(Stores2.statusTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public SQLiteDatabase getDb(Context context_) {
        context = context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
        return rdbleDb;
    }

    public ArrayList<String> getOld(SQLiteDatabase rdbleDb) {
        ArrayList<String> strings=new ArrayList<>();
        Stores store=new Stores(context);

        String[] projection = {Stores2.status_id};

        String selection = Stores2.seen + " = ? AND "+Stores2.sent + " = ? ";
        String[] selectionArgs = {"1", "0"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.time_db + " ASC ";

        cursor = rdbleDb.query(Stores2.statusTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder);

        int num = cursor.getCount();

        for (int i = 0; i < num; i++) {
            cursor.moveToPosition(i);

            String auth = cursor.getString(cursor.getColumnIndex(Stores2.status_id));
            strings.add(auth);
        }
        cursor.close();
        return strings;
    }

    public String mkString(ArrayList<String> kl) {
        String msgs = "[";
        int num = kl.size();//.getCount();

        for (int i = 0; i < num; i++) {
            if(i!=0){
                msgs+=", ";
            }
            String auth = kl.get(i);
            msgs+="{'status_code':'"+auth+"'}";
        }
        return msgs+"]";
    }

    public void sent(SQLiteDatabase rdbleDb, String string__) {
        rdbleDb=DB_Aro.getWDb(context);
        String selection = Stores2.status_id + " = ?";
        String[] selectionArgs = {string__};

        contentValues = new ContentValues();
        contentValues.put(Stores2.sent, "1");

        rdbleDb.update(Stores2.statusTable, contentValues, selection, selectionArgs);
    }

    public ArrayList<Model__3> fetchIntro(Context context){
        return addIntro(context, false);
    }

    public String getType() {
        return (type==null)?Stores.IMG: type;
    }

    public String setType(String type) {
        return this.type=type;
    }

    public ArrayList<Model__3> addIntro(Context context, boolean savee) {
        ArrayList<Model__3> statuses = new ArrayList<>();
        ArrayList<Model__3> status_tab_list = new ArrayList<>();

        Model__3 Model__3 = new Model__3();
        auth=fullname = context.getString(R.string.app_name);

//        Uri uri = Uri.parse("android.resource://your.package.here/drawable/image_name");
        int vid_id=R.mipmap.ic_launcher;

        Uri file= (new Stores(context)).getResUri(vid_id);
        user_image = file.toString();
        Model__3.setFullname(fullname);
        Model__3.setUsername(fullname);
        Model__3.setUser_img(user_image);
        int pos=0;
        int[] inatARr={R.drawable.selfie, R.drawable.mlogin, R.raw.login_video, R.drawable.login_girl,  R.raw.highlights_intro_video};
        int[] strARr={R.string.share, R.string.meet, R.string.connect, R.string.beYou, R.string.coonect_intro};

        Status_tab messages_=new Status_tab();
        for (int vid_id_ : inatARr) {
            vid_id=vid_id_;
            file=(new Stores(context)).getRawResUri(vid_id);

            Model__3 status_tab = new Model__3();
            status_id =(pos==4)?Stores.DEFAULT_STAT:Stores.STATUS_STORE+pos+Stores.STATUS_STORE;
            time = ""+System.currentTimeMillis();
            text = (context.getString(strARr[pos]));//Connect, Share, Meet...Piccmaq
            image = file.toString();
            fav = "1";

            messages_=new Status_tab();
            messages_.setUser_name(auth);
            messages_.setTime(""+(System.currentTimeMillis()/1000));
            messages_.setText(text);
            messages_.setStatus_id(status_id);
            messages_.setFav(fav);
            messages_.setImage(image);
            messages_.setSeen("1");

            status_tab.setStatus_id(status_id);
            status_tab.setTime(time);
            status_tab.setText(text);
            status_tab.setImage(image);
            if(pos==2 || pos==4){
                type=Stores.VID;
            }else{
                type=Stores.IMG;
            }

            messages_.setType(type);
            if(savee) {
                messages_.save(context);
            }
            status_tab.setType(type);

            status_tab.setFullname(fullname);
            status_tab.setUser_image(user_image);

            seen = "0";
            status_tab_list.add(status_tab);
            pos++;
        }

        String cd=(new Stores(context)).getResUri(R.drawable.profile_btn_superlike).toString();

        Users_prof users_prof=new Users_prof();
        users_prof.setUser_name(auth);
        users_prof.setFullname(auth);
        users_prof.setImage(cd);

        Model__3.setSeen(false);
        Model__3.setStatFav(true);
        Model__3.setUsername(auth);

        Model__3.setStatData(status_tab_list);
        statuses.add(Model__3);
        return statuses;
    }
}
