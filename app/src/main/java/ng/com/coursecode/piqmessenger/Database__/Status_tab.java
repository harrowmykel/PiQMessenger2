package ng.com.coursecode.piqmessenger.Database__;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ng.com.coursecode.piqmessenger.Db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.Model__.Model__3;
import ng.com.coursecode.piqmessenger.Model__.Model__3;
import ng.com.coursecode.piqmessenger.Model__.Stores2;
import ng.com.coursecode.piqmessenger.Model__.TimeModel;

/**
 * Created by harro on 10/10/2017.
 */

public class Status_tab {

    public static final String UNSEEN = "UNSEEN";
    public static final String FAV = "FAV";
    public static final String SEEN = "SEEN";
    public static final String CREATE = "create";
    public String user_name;
    public String status_id="fskjbjtjktslj;tbobos";
    public String time;
    public String text;
    public String image;
    public String seen;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    DB_Aro dbHelper;
    Context context;
    Cursor cursor;
    public String fav;
    private String fullname;
    String user_image;
    String auth;

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
            dbHelper = new DB_Aro(context);
            wrtable = dbHelper.getWritableDatabase();
            contentValues = new ContentValues();
            contentValues.put(Stores2.user_name, getUser_name().toLowerCase());
            contentValues.put(Stores2.status_id, getStatus_id());
            contentValues.put(Stores2.time, getTime());
            contentValues.put(Stores2.time_db, TimeModel.getLongPhpTime(getTime()));
            contentValues.put(Stores2.text, getText());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.seen, getSeen());
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
                wrtable.update(sTable, contentValues, toFind, sf);
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
        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();
        wrtable=dbHelper.getWritableDatabase();

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
        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();

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
                    Stores2.image,
                    Stores2.seen};

            String selection1 = Stores2.user_name + " = ?";
            String[] selectionArgs1 = {auth};
            // How you want the results sorted in the resulting Cursor
            String sortOrder1 = Stores2.time + " ASC";

            Cursor cursor1 = rdbleDb.query(Stores2.statusTable,  // The table to query
                    projection1,                               // The columns to return
                    selection1,                                // The columns for the WHERE clause
                    selectionArgs1,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder1                                 // The sort order
            );

            int num1 = cursor1.getCount();

            Model__3 Model__3 = new Model__3();

            Users_prof users_prof = Users_prof.getInfo(context, auth);
            fullname = users_prof.getFullname();
            user_image = users_prof.getImage();
            Model__3.setFullname(fullname);
            Model__3.setUser_img(user_image);
            ArrayList<Model__3> status_tab_list = new ArrayList<>();

            boolean seen_ = true;
            boolean fav_ = false;

            //all post from a user are listed
            for (int i1 = 0; i1 < num1; i1++) {
                cursor1.moveToPosition(i1);
                Model__3 status_tab = new Model__3();
                status_id = cursor1.getString(cursor1.getColumnIndex(Stores2.status_id));
                time = cursor1.getString(cursor1.getColumnIndex(Stores2.time));
                text = cursor1.getString(cursor1.getColumnIndex(Stores2.text));
                image = cursor1.getString(cursor1.getColumnIndex(Stores2.image));
                fav = cursor1.getString(cursor1.getColumnIndex(Stores2.fav));

                status_tab.setStatus_id(status_id);
                status_tab.setTime(time);
                status_tab.setText(text);
                status_tab.setImage(image);

                status_tab.setFullname(fullname);
                status_tab.setUser_image(user_image);

                seen = cursor1.getString(cursor1.getColumnIndex(Stores2.seen));

                seen_ = seen_ && seen.equals("1");
                fav_ = fav_ || (!fav.equals("0"));
                status_tab_list.add(status_tab);
            }

            Model__3.setSeen(seen_);
            Model__3.setStatFav(fav_);
            Model__3.setUsername(auth);

            Model__3.setStatData(status_tab_list);
            statuses.add(Model__3);

            cursor1.close();
        }
        cursor.close();
//        wrtable.close();
//        dbHelper.close();
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
}