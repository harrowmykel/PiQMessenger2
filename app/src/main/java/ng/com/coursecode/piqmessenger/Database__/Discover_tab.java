package ng.com.coursecode.piqmessenger.Database__;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.Model__.Stores2;

/**
 * Created by harro on 10/10/2017.
 */

public class Discover_tab {

    public String user_name;
    public String discover_id;
    public String time;
    public String text;
    public String image;
    public String seen;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    //DB_Aro dbHelper;
    Context context;
    Cursor cursor;

    public Discover_tab(){

    }

    public Discover_tab(String user_name, String discover_id,
                        String time, String text, String image, String seen){
        setUser_name(user_name);
        setDiscover_id(discover_id);
        setTime(time);
        setText(text);
        setImage(image);
        setSeen(seen);
    }

    public boolean save(Context context1){
        context=context1;
        if(context!=null){
            wrtable = DB_Aro.getWDb(context);
            contentValues=new ContentValues();
            contentValues.put(Stores2.discover_id, getDiscover_id());
            contentValues.put(Stores2.time, getTime());
            contentValues.put(Stores2.text, getText());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.seen, getSeen());

            String toFind=Stores2.discover_id + " = ?";
            String sTable=Stores2.discoverTable;
            String[] sArray=new String[]{getDiscover_id()};
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

    public List<Discover_tab> listAll(Context context_){
        context=context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
        List<Discover_tab> msgs=new ArrayList<>();
        
        String[] projection = {Stores2.user_name,
                Stores2.discover_id,
                Stores2.time,
                Stores2.text,
                Stores2.image,
                Stores2.seen};

        String selection = null;
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.id_ + " DESC";

        cursor = rdbleDb.query(Stores2.discoverTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        int num=cursor.getCount();

        for(int i=0; i<num; i++){

            cursor.moveToNext();
            Discover_tab discover_tab=new Discover_tab();

            user_name=cursor.getString(cursor.getColumnIndex(Stores2.user_name));
            discover_id=cursor.getString(cursor.getColumnIndex(Stores2.discover_id));
            time=cursor.getString(cursor.getColumnIndex(Stores2.time));
            text=cursor.getString(cursor.getColumnIndex(Stores2.text));
            image=cursor.getString(cursor.getColumnIndex(Stores2.image));
            seen=cursor.getString(cursor.getColumnIndex(Stores2.seen));

            discover_tab.setUser_name(user_name);
            discover_tab.setDiscover_id(discover_id);
            discover_tab.setTime(time);
            discover_tab.setText(text);
            discover_tab.setSeen(seen);
            discover_tab.setImage(image);
            msgs.add(discover_tab);
        }
        cursor.close();
        return msgs;
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


    public String getDiscover_id() {
        return discover_id;
    }

    public void setDiscover_id(String discover_id) {
        this.discover_id = discover_id;
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
}
