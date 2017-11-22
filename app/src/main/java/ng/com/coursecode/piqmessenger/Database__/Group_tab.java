package ng.com.coursecode.piqmessenger.Database__;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.Db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.Model__.Model__2;
import ng.com.coursecode.piqmessenger.Model__.Stores;
import ng.com.coursecode.piqmessenger.Model__.Stores2;

/**
 * Created by harro on 10/10/2017.
 */

public class Group_tab {

    public String user_name;
    public String msg_id;//msg_id
    public String mess_age;
    public String tim_e;
    public String time_stamp;
    public String auth;
    public String confirm;
    public String image;
    public String group_id;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    Cursor cursor;
    DB_Aro dbHelper;
    Context context;
    int rand;

    long newId;
    private String fullname;

    public Group_tab(){

    }

    public boolean save(Context context1){
        context=context1;
        if(context!=null){
            dbHelper=new DB_Aro(getContext());
            wrtable = dbHelper.getWritableDatabase();
            contentValues=new ContentValues();
            contentValues.put(Stores2.auth, getAuth());
            contentValues.put(Stores2.group_id, getGroup_id());
            contentValues.put(Stores2.mess_age, getMess_age());
            contentValues.put(Stores2.tim_e, getTim_e());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.confirm, getConfirm());
            contentValues.put(Stores2.msg_id, getmsg_id());
            contentValues.put(Stores2.fullname, getFullname());

            String toFind=Stores2.msg_id + " = ?";
            String sTable=Stores2.groupTable;
            String[] sArray=new String[]{getmsg_id()};
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
                nuk=wrtable.update(sTable, contentValues, toFind, sArray);
                result.close();
            }else{
                nuk=wrtable.insert(sTable, null, contentValues);
            }
            newId=nuk;
            return (nuk!=-1);
        }

        return false;
    }

    public List<Group_tab> listAll(Context context) {

        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();
        List<String> msgs_ = new ArrayList<>();

        String[] projection = {
                Stores2.group_id};

        String selection = null;
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(true, Stores2.groupTable,  // The table to query
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

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            group_id = cursor.getString(cursor.getColumnIndex(Stores2.group_id));

            String keyWord=(auth.equalsIgnoreCase(store.getUsername()))?group_id:auth;
            if(!msgs_.contains(keyWord))
                msgs_.add(keyWord);
        }
        cursor.close();
        return listAll(context,msgs_);
    }


    public Model__2 search(Context context, String query, int page) {
        List<Group_tab> msgs = new ArrayList<>();
        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();
        List<String> msgs_ = new ArrayList<>();

        String[] projection = {Stores2.id_,
                Stores2.auth,
                Stores2.group_id,
                Stores2.mess_age,
                Stores2.tim_e,
                Stores2.image,
                Stores2.fullname,
                Stores2.confirm,
                Stores2.msg_id};
        query="%"+query+"%";
        String selection = Stores2.auth+" LIKE ? OR "+Stores2.group_id+" LIKE ? OR "+Stores2.mess_age+" LIKE ?";
        String[] selectionArgs = {query, query, query};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(Stores2.groupTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                // The sort order
        );
        int num = cursor.getCount();

        int pages_left=((num/Stores.dbReqCounts))-page;
        String limit=Stores.limitDb(num, Stores.dbReqCounts, page);
        //offset, limit

        cursor = rdbleDb.query(false, Stores2.groupTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,                                 // The sort order
                limit

        );
        num = cursor.getCount();

        Stores store=new Stores(context);

        for (int i = 0; i < num; i++) {
            cursor.moveToPosition(i);
            Group_tab group_tab = new Group_tab();

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            group_id = cursor.getString(cursor.getColumnIndex(Stores2.group_id));
            mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
            tim_e = cursor.getString(cursor.getColumnIndex(Stores2.tim_e));
            image = cursor.getString(cursor.getColumnIndex(Stores2.image));
            confirm = cursor.getString(cursor.getColumnIndex(confirm));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.id_));
            fullname= cursor.getString(cursor.getColumnIndex(Stores2.fullname));



            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            group_id = cursor.getString(cursor.getColumnIndex(Stores2.group_id));

            String keyWord=(auth.equalsIgnoreCase(store.getUsername()))?group_id:auth;
            auth=keyWord;

            group_tab.setAuth(auth);
            group_tab.setGroup_id(group_id);

            group_tab.setMess_age(mess_age);
            group_tab.setTim_e(tim_e);
            group_tab.setImage(image);
            group_tab.setConfirm(confirm);
            group_tab.setmsg_id(msg_id);
            group_tab.setFullname(fullname);

            if(!auth.isEmpty())
                msgs.add(group_tab);
        }
        cursor.close();
        rdbleDb.close();
        dbHelper.close();
        Model__2 model=new Model__2();

        model.setPagesLeft(""+pages_left);
        model.setDb_result_group(msgs);
        return model;
    }

    public List<Group_tab> listAll(Context context, List<String> msg_) {
        List<Group_tab> msgs = new ArrayList<>();
        Stores store=new Stores(context);

        int msgTurn=msg_.size();
        for (String uname:msg_ ) {
            String[] projection = {Stores2.id_,
                    Stores2.auth,
                    Stores2.group_id,
                    Stores2.mess_age,
                    Stores2.tim_e,
                    Stores2.image,
                    confirm,
                    Stores2.msg_id};

            String selection = Stores2.auth + " = ? OR "+ Stores2.group_id + "= ? ";
            String[] selectionArgs = {uname, uname};

            // How you want the results sorted in the resulting Cursor
            String sortOrder = Stores2.tim_e + " DESC ";

            cursor = rdbleDb.query(false, Stores2.groupTable,  // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder,                                 // The sort order
                    "1"
            );
            int num = cursor.getCount();

            for (int i = 0; i < num; i++) {
                cursor.moveToPosition(i);
                Group_tab group_tab = new Group_tab();

                auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
                group_id = cursor.getString(cursor.getColumnIndex(Stores2.group_id));
                mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
                tim_e = cursor.getString(cursor.getColumnIndex(Stores2.tim_e));
                image = cursor.getString(cursor.getColumnIndex(Stores2.image));
                confirm = cursor.getString(cursor.getColumnIndex(confirm));
                msg_id = cursor.getString(cursor.getColumnIndex(Stores2.id_));



                auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
                group_id = cursor.getString(cursor.getColumnIndex(Stores2.group_id));

                String keyWord=(auth.equalsIgnoreCase(store.getUsername()))?group_id:auth;
                auth=keyWord;

                group_tab.setAuth(auth);
                group_tab.setGroup_id(group_id);

                group_tab.setMess_age(mess_age);
                group_tab.setTim_e(tim_e);
                group_tab.setImage(image);
                group_tab.setConfirm(confirm);
                group_tab.setmsg_id(msg_id);
                group_tab.setFullname(Users_prof.getFullname(context, auth));
                if(!auth.isEmpty())
                    msgs.add(group_tab);
            }
            cursor.close();
        }
        rdbleDb.close();
        dbHelper.close();
        return msgs;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getmsg_id() {
        return msg_id;
    }

    public void setmsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMess_age() {
        return mess_age;
    }

    public void setMess_age(String mess_age) {
        this.mess_age = mess_age;
    }

    public String getTim_e() {
        return tim_e;
    }

    public void setTim_e(String tim_e) {
        this.tim_e = tim_e;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getNewId() {
        return newId;
    }

    public void setNewId(long newId) {
        this.newId = newId;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

}
