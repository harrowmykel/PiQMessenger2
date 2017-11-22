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

public class Messages{

    public String user_name;
    public String msg_id;//msg_id
    public String mess_age;
    public String tim_e;
    public String time_stamp;
    public String auth;
    public String confirm;
    public String image;
    public String recip;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    Cursor cursor;
    DB_Aro dbHelper;
    Context context;
    int rand;

    List<Users_prof> usersProfList = new ArrayList<>();

    long newId;
    private String fullname;

    public Messages(){

    }

    public Messages(String msg_id, String mess_age,
                    String tim_e, String auth,
                    String confirm, String image, String recip){
        setAuth(auth);
        setRecip(recip);
        setmsg_id(msg_id);
        setTim_e(tim_e);
        setTime_stamp(tim_e);
        setConfirm(confirm);
        setMess_age(mess_age);
        setImage(image);
    }

    public boolean save(Context context1){
        context=context1;
        if(context!=null){
            dbHelper=new DB_Aro(getContext());
            wrtable = dbHelper.getWritableDatabase();
            contentValues=new ContentValues();
            contentValues.put(Stores2.auth, getAuth().toLowerCase());
            contentValues.put(Stores2.recip, getRecip().toLowerCase());
            contentValues.put(Stores2.mess_age, getMess_age());
            contentValues.put(Stores2.tim_e, getTim_e());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.confirm, getConfirm());
            contentValues.put(Stores2.msg_id, getmsg_id());

            String toFind=Stores2.msg_id + " = ?";
            String sTable=Stores2.messagesTable;
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

    public List<Messages> listAll(Context context) {

        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();
        List<String> msgs_ = new ArrayList<>();

        String[] projection = {
                Stores2.auth,
                Stores2.recip};

        String selection = null;
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(true, Stores2.messagesTable,  // The table to query
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
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));

            String keyWord=(auth.equalsIgnoreCase(store.getUsername()))?recip:auth;
            if(!msgs_.contains(keyWord))
                msgs_.add(keyWord);
        }
        cursor.close();
        return listAll(context,msgs_);
    }


    public Model__2 search(Context context, String query, int page) {
        List<Messages> msgs = new ArrayList<>();
        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();
        List<String> msgs_ = new ArrayList<>();

        String[] projection = {Stores2.id_,
                Stores2.auth,
                Stores2.recip,
                Stores2.mess_age,
                Stores2.tim_e,
                Stores2.image,
                Stores2.confirm,
                Stores2.msg_id};
        query="%"+query+"%";
        String selection = Stores2.auth+" LIKE ? OR "+Stores2.recip+" LIKE ? OR "+Stores2.mess_age+" LIKE ?";
        String[] selectionArgs = {query, query, query};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(Stores2.messagesTable,  // The table to query
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

        cursor = rdbleDb.query(false, Stores2.messagesTable,  // The table to query
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
            Messages messages = new Messages();

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));
            mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
            tim_e = cursor.getString(cursor.getColumnIndex(Stores2.tim_e));
            image = cursor.getString(cursor.getColumnIndex(Stores2.image));
            confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.id_));

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));

            String keyWord=(auth.equalsIgnoreCase(store.getUsername()))?recip:auth;
            auth=keyWord;

            messages.setAuth(auth);
            messages.setRecip(recip);

            Users_prof user=Users_prof.getInfo(context, auth);
            fullname=user.getFullname();
            image=user.getImage();
            messages.setMess_age(mess_age);
            messages.setTim_e(tim_e);
            messages.setImage(image);
            messages.setConfirm(confirm);
            messages.setmsg_id(msg_id);
            messages.setFullname(fullname);

            if(!auth.isEmpty())
                msgs.add(messages);
        }
        cursor.close();
        rdbleDb.close();
        dbHelper.close();
        Model__2 model=new Model__2();

        model.setPagesLeft(""+pages_left);
        model.setDb_result(msgs);
        return model;
    }

    public List<Messages> listAll(Context context, List<String> msg_) {
        List<Messages> msgs = new ArrayList<>();
        Stores store=new Stores(context);

        int msgTurn=msg_.size();
        for (String uname:msg_ ) {
            String[] projection = {Stores2.id_,
                    Stores2.auth,
                    Stores2.recip,
                    Stores2.mess_age,
                    Stores2.tim_e,
                    Stores2.image,
                    Stores2.confirm,
                    Stores2.msg_id};

            String selection = Stores2.auth + " = ? OR "+ Stores2.recip + "= ? ";
            String[] selectionArgs = {uname, uname};

            // How you want the results sorted in the resulting Cursor
            String sortOrder = Stores2.tim_e + " DESC ";

            cursor = rdbleDb.query(false, Stores2.messagesTable,  // The table to query
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
                Messages messages = new Messages();

                auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
                recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));
                mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
                tim_e = cursor.getString(cursor.getColumnIndex(Stores2.tim_e));
                image = cursor.getString(cursor.getColumnIndex(Stores2.image));
                confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
                msg_id = cursor.getString(cursor.getColumnIndex(Stores2.id_));



                auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
                recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));

                String keyWord=(auth.equalsIgnoreCase(store.getUsername()))?recip:auth;
                auth=keyWord;

                messages.setAuth(auth);

                Users_prof user=Users_prof.getInfo(context, auth);
                fullname=user.getFullname();
                image=user.getImage();

                messages.setMess_age(mess_age);
                messages.setTim_e(tim_e);
                messages.setImage(image);
                messages.setConfirm(confirm);
                messages.setmsg_id(msg_id);
                messages.setFullname(fullname);
                if(!auth.isEmpty())
                    msgs.add(messages);
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

    public String getRecip() {
        return recip;
    }

    public void setRecip(String recip) {
        this.recip = recip;
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

    public List<Messages> listAllFromUser(Context context, String username) {
        List<Messages> msgs = new ArrayList<>();
        dbHelper = new DB_Aro(context);
        rdbleDb = dbHelper.getReadableDatabase();
        List<String> msgs_ = new ArrayList<>();

        String[] projection = {Stores2.id_,
                Stores2.auth,
                Stores2.recip,
                Stores2.mess_age,
                Stores2.tim_e,
                Stores2.image,
                Stores2.confirm,
                Stores2.msg_id};

        String selection = Stores2.auth+" = ? OR "+Stores2.auth+" = ? ";
        String[] selectionArgs = {username, username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(true, Stores2.messagesTable,  // The table to query
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
            cursor.moveToPosition(i);
            Messages messages = new Messages();

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));
            mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
            tim_e = cursor.getString(cursor.getColumnIndex(Stores2.tim_e));
            image = cursor.getString(cursor.getColumnIndex(Stores2.image));
            confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.id_));



            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));

            messages.setAuth(auth);
            messages.setRecip(recip);

            messages.setMess_age(mess_age);
            messages.setTim_e(tim_e);
            messages.setImage(image);
            messages.setConfirm(confirm);
            messages.setmsg_id(msg_id);

            if(!auth.isEmpty())
                msgs.add(messages);
        }
        cursor.close();
        return msgs;
    }
}