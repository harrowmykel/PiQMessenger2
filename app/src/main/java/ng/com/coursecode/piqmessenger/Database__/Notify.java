package ng.com.coursecode.piqmessenger.Database__;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.Model__.Datum;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;

/**
 * Created by harro on 18/12/2017.
 */

public class Notify {

    public String subj;
    public String msg_id;
    public String type;
    public String seen;
    public String time_stamp;
    public String obj_id;

    String last_user="a%";
    Users_prof users_prof;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    Cursor cursor;
    Context context;
    long newId;
    private String link;

    public boolean save(Context context1) {
        context = context1;
        if (context != null) {

            wrtable = DB_Aro.getWDb(context);
            contentValues = getCValues();

            String toFind = Stores2.msg_id + " = ?";
            String sTable = Stores2.notifyTable;
            String[] sArray = new String[]{getMsg_id()};
            Cursor result = wrtable.query(
                    sTable,
                    null,
                    toFind,
                    sArray,
                    null,
                    null,
                    null
            );
            long nuk;
            if (result != null && result.getCount() > 0) {
                nuk = wrtable.update(sTable, contentValues, toFind, sArray);
                result.close();
            } else {
                nuk = wrtable.insert(sTable, null, contentValues);
            }
            newId = nuk;
            return (nuk != -1);
        }

        return false;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private ContentValues getCValues() {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(Stores2.auth, getSubj().toLowerCase());
        contentValues1.put(Stores2.type, getType());
        contentValues1.put(Stores2.seen, getSeen());
        contentValues1.put(Stores2.msg_id, getMsg_id());
        contentValues1.put(Stores2.posts_id, getObj_id());
        contentValues1.put(Stores2.web_link, getLink());
        contentValues1.put(Stores2.time_stamp, getTime_stamp());
        return contentValues1;
    }

    public List<Notify> listAll(Context context) {
        rdbleDb = DB_Aro.getWDb(context);
        List<Notify> msgs_ = new ArrayList<>();

        String[] projection = {
                Stores2.auth,
                Stores2.type,
                Stores2.seen,
                Stores2.msg_id,
                Stores2.posts_id,
                Stores2.web_link,
                Stores2.time_stamp};

        String selection = null;
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.time_stamp + " DESC ";

        cursor = rdbleDb.query(true, Stores2.notifyTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,                                 // The sort order
                ""

        );
        int num = cursor.getCount();

        Stores store=new Stores(context);

        for (int i = 0; i < num; i++) {
            cursor.moveToPosition(i);
            Notify notify=new Notify();

            subj = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            type = cursor.getString(cursor.getColumnIndex(Stores2.type));
            seen = cursor.getString(cursor.getColumnIndex(Stores2.seen));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.msg_id));
            obj_id = cursor.getString(cursor.getColumnIndex(Stores2.posts_id));
            link = cursor.getString(cursor.getColumnIndex(Stores2.web_link));
            time_stamp = cursor.getString(cursor.getColumnIndex(Stores2.time_stamp));

            notify.setSubj(subj);
            notify.setType(type);
            notify.setSeen(seen);
            notify.setMsg_id(msg_id);
            notify.setObj_id(obj_id);
            notify.setLink(link);
            notify.setTime_stamp(time_stamp);
            msgs_.add(notify);
        }
        cursor.close();
        return msgs_;
    }

    public String getSubj() {
        return subj;
    }

    public void setSubj(String subj) {
        this.subj = subj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public void delete(Context context) {
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String selection = null;
        String[] selectionArgs = null;

        rdbleDb.delete(Stores2.notifyTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public void delete(Context context, String text1) {
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String selection = Stores2.msg_id +" = ?";
        String[] selectionArgs = {text1};

        rdbleDb.delete(Stores2.notifyTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public String getObj_id() {
        return obj_id;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public String getLink() {
        return link;
    }
}
