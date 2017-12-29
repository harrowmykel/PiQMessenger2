package ng.com.coursecode.piqmessenger.database__;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.conversate.Converse;
import ng.com.coursecode.piqmessenger.db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.extLib.Authorss;
import ng.com.coursecode.piqmessenger.model__.Model__2;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.Stores2;

/**
 * Created by harro on 10/10/2017.
 */

public class Messages{

    public String user_name;
    public String msg_id;//msg_id
    public String mess_age="";
    public int tim_e=1;
    public String time_stamp;
    public String auth;
    public String confirm=Stores.UNSENT_MSG;
    public String image="";
    public String recip;
    String last_user="a%";
    Users_prof users_prof;
    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    Cursor cursor;
    Context context;
    int rand;

    List<Users_prof> usersProfList = new ArrayList<>();

    long newId;
    private String fullname;
    private int sent=0;
    Authorss authors;
    private int id=0;

    public Messages(){

    }

    public Messages(String msg_id, String mess_age,
                    int tim_e, String auth,
                    String confirm, String image, String recip){
        setAuth(auth);
        setRecip(recip);
        setmsg_id(msg_id);
        setTim_e(tim_e);
        setConfirm(confirm);
        setMess_age(mess_age);
        setImage(image);
    }

    public Messages(Context context) {
        setContext(context);
    }

    public void setAuthorss(String username, String fullname, String image) {
        authors=new Authorss(username, fullname, image);
    }

    public boolean save(Context context1) {
        context = context1;
        if (context != null) {

            wrtable = DB_Aro.getWDb(context);
            contentValues = getCValues();

            String toFind = Stores2.msg_id + " = ?";
            String sTable = Stores2.messagesTable;
            String[] sArray = new String[]{getmsg_id()};
            Cursor result = wrtable.query(
                    sTable,
                    null,
                    toFind,
                    sArray,
                    null,
                    null,
                    null
            );
            try{
                long nuk;
                if (result != null && result.getCount() > 0) {
                    nuk = wrtable.update(sTable, contentValues, toFind, sArray);
                    result.close();
                } else {
                    nuk = wrtable.insert(sTable, null, contentValues);
                }
                newId = nuk;
                return (nuk != -1);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean saveNew(Context context1) {
        context = context1;
        if (context != null) {
            wrtable = DB_Aro.getWDb(context);
            setConfirm(Stores.UNSENT_MSG);
            contentValues = getCValues();
            String sTable = Stores2.messagesTable;
            long nuk;
            nuk = wrtable.insert(sTable, null, contentValues);
            return (nuk != -1);
        }
        return false;
    }

    private ContentValues getCValues() {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(Stores2.auth, getAuth().toLowerCase());
        contentValues1.put(Stores2.recip, getRecip().toLowerCase());
        contentValues1.put(Stores2.mess_age, getMess_age());
        contentValues1.put(Stores2.tim_e, getTim_e());
        contentValues1.put(Stores2.image, getImage());
        contentValues1.put(Stores2.confirm, getConfirm());
        contentValues1.put(Stores2.sent, getSent());
        contentValues1.put(Stores2.msg_id, getmsg_id());
        return contentValues1;
    }

    public List<Messages> listAll(Context context) {
        rdbleDb = DB_Aro.getWDb(context);
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
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
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
            tim_e = cursor.getInt(cursor.getColumnIndex(Stores2.tim_e));
            image = cursor.getString(cursor.getColumnIndex(Stores2.image));
            confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
            id = cursor.getInt(cursor.getColumnIndex(Stores2.id_));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.msg_id));

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
            messages.setId(id);
            messages.setFullname(fullname);

            if(!auth.isEmpty())
                msgs.add(messages);
        }
        cursor.close();
        rdbleDb.close();
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
            rdbleDb=DB_Aro.getWDb(context);
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
                tim_e = cursor.getInt(cursor.getColumnIndex(Stores2.tim_e));
                image = cursor.getString(cursor.getColumnIndex(Stores2.image));
                confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
                id = cursor.getInt(cursor.getColumnIndex(Stores2.id_));
                msg_id = cursor.getString(cursor.getColumnIndex(Stores2.msg_id));

                auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
                recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));

                boolean thrwn=(auth.equalsIgnoreCase(store.getUsername()));
                String keyWord=thrwn?recip:auth;
                auth=keyWord;

                messages.setAuth(auth);

                Users_prof user=Users_prof.getInfo(context, auth);
                fullname=user.getFullname();
                image=user.getImage();

                messages.setMess_age(mess_age);
                messages.setTim_e(tim_e);
                messages.setImage(image);
                if(thrwn) {
                    messages.setConfirm(Stores.READ_MSG);
                }else{
                    messages.setConfirm(confirm);
                }
                messages.setmsg_id(msg_id);
                messages.setId(id);
                messages.setFullname(fullname);
                if(!auth.isEmpty())
                    msgs.add(messages);
            }
            cursor.close();
        }
        rdbleDb.close();
        //dbHelper.close();
        return msgs;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getRecip() {
        return recip.trim();
    }

    public void setRecip(String recip) {
        this.recip = recip;
    }


    public String getUser_name() {
        return user_name.trim();
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMess_age() {
        return mess_age;
    }

    public void setMess_age(String mess_age) {
        this.mess_age = mess_age;
    }

    public int getTim_e() {
        return tim_e;
    }

    public void setTim_e(int tim_e) {
        this.tim_e = tim_e;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getAuth() {
        return auth.trim();
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getConfirm() {
        return confirm;
    }

    public boolean isUnread() {
        return confirm.equals(Stores.UNREAD_MSG);
    }

    public boolean isRcvd() {
        return confirm.equals(Stores.RCVD_MSG);
    }

    public boolean isRead() {
        return confirm.equals(Stores.READ_MSG);
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getImage() {
        return image==null?"":image;
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

    public List<Messages> listAllFromUser(Context context, String recipp) {
        tim_e=1;
        this.context=context;
        rdbleDb = DB_Aro.getWDb(context);
        String thisUser=(new Stores(context)).getUsername();

        String[] projection = {Stores2.id_,
                Stores2.auth,
                Stores2.recip,
                Stores2.mess_age,
                Stores2.tim_e,
                Stores2.image,
                Stores2.confirm,
                Stores2.msg_id};

        String selection = "("+Stores2.auth+" = ? AND "+Stores2.recip+" = ?) OR ("+Stores2.auth+" = ? AND "+Stores2.recip+" = ? )";
        String[] selectionArgs = {thisUser, recipp, recipp, thisUser};// username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(Stores2.messagesTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder);
        return displace(cursor, recipp);
    }

    public void setAuthorUp(String auth, Stores store) {
        String username;
        if(auth.equalsIgnoreCase(store.getUsername())){
            username=recip;
        }else {
            username=auth;
        }
        if(!last_user.equalsIgnoreCase(username)){
            users_prof=Users_prof.getInfo(context, username);
        }

        setAuthorss(username, users_prof.getFullname(), users_prof.getImage());
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sentt){
        sent=sentt;
    }

    public String[] getOldMess(Context context) {
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
        String msgs = "[";
        String msgs1 = "0";
        Stores store=new Stores(context);

        String[] projection = {Stores2.id_,
                Stores2.auth,
                Stores2.recip,
                Stores2.mess_age,
                Stores2.tim_e,
                Stores2.image,
                Stores2.confirm,
                Stores2.msg_id};

        String selection = Stores2.sent + " = ? ";
        String[] selectionArgs = {"0"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " ASC ";

        cursor = rdbleDb.query(Stores2.messagesTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder);
        int num = cursor.getCount();

        for (int i = 0; i < num; i++) {
            cursor.moveToPosition(i);
            if(i!=0){
                msgs+=", ";
            }

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));
            mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
            tim_e = cursor.getInt(cursor.getColumnIndex(Stores2.tim_e));
            image = cursor.getString(cursor.getColumnIndex(Stores2.image));
            confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
            id = cursor.getInt(cursor.getColumnIndex(Stores2.id_));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.msg_id));

            msgs+="{'recip':'"+recip+"', 'message':'"+mess_age+
                    "', 'image':'"+image+"'}";

            msgs1=msgs1+", "+id;
        }
        cursor.close();
        msgs=msgs+"]";
        return new String[]{msgs, msgs1};
    }

    public void delete(Context context) {
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String selection = null;
        String[] selectionArgs = null;

        rdbleDb.delete(Stores2.messagesTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public void delete(Context context, String text1) {//this deletes only ids nt msg_id
        text1=text1.trim();
        String[] array = text1.split(",", -1);

        for (String anArray : array) {
            deleteto(context, anArray);
        }
    }

    public void deleteto(Context context, String text1) {
        text1=text1.trim();
        if(text1.isEmpty()){
            return;
        }
        rdbleDb = DB_Aro.getWDb(context);
        String selection = Stores2.id_+" = ?";
        String[] selectionArgs = {text1};

        int hj=rdbleDb.delete(Stores2.messagesTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public void setLastRead(String username, String last_read, String confirm_) {
        rdbleDb = DB_Aro.getWDb(context);
        username=username.trim();
        last_read=""+last_read.trim();
        String selection = Stores2.recip + " = ? AND " + Stores2.confirm + " <> ? AND " + Stores2.tim_e + " = ?";
        String[] selectionArgs = new String[]{username, Stores.READ_MSG, last_read};

        ContentValues contentValues=new ContentValues();
        contentValues.put(Stores2.confirm, confirm_);

        rdbleDb.update(Stores2.messagesTable, contentValues, // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);

        selection = Stores2.recip + " = ? AND " + Stores2.confirm + " <> ? AND " + Stores2.tim_e + " < ?";
        selectionArgs = new String[]{username, Stores.READ_MSG, last_read};
        contentValues=new ContentValues();
        contentValues.put(Stores2.confirm, confirm_);

        rdbleDb.update(Stores2.messagesTable, contentValues, // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public void setReadAll(Context context, String username) {
        rdbleDb = DB_Aro.getWDb(context);
        String selection = Stores2.auth+" = ? ";
        String[] selectionArgs = {username};
        ContentValues contentValues=new ContentValues();
        contentValues.put(Stores2.confirm, Stores.READ_MSG);

        rdbleDb.update(Stores2.messagesTable, contentValues, // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public void deleteFrom(Context context, String text1) {
        text1=text1.trim();
        if(text1.isEmpty()){
            return;
        }
        rdbleDb = DB_Aro.getWDb(context);
        String selection = Stores2.auth+" = ? OR "+ Stores2.recip+" = ? ";
        String[] selectionArgs = {text1, text1};

        int hj=rdbleDb.delete(Stores2.messagesTable,  // The table to query
                selection,                                // The columns for the WHERE clause
                selectionArgs);
    }

    public List<Messages> listAllFromUser(Context context, String recipp, String search) {

        tim_e = 1;
        this.context = context;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
        String thisUser = (new Stores(context)).getUsername();

        String[] projection = {Stores2.id_,
                Stores2.auth,
                Stores2.recip,
                Stores2.mess_age,
                Stores2.tim_e,
                Stores2.image,
                Stores2.confirm,
                Stores2.msg_id};

        String selection = "((" + Stores2.auth + " = ? AND " + Stores2.recip + " = ? ) OR (" + Stores2.auth + " = ? AND " + Stores2.recip + " = ? )) AND " + Stores2.mess_age + " LIKE  ?";
        String[] selectionArgs = {thisUser, recipp, recipp, thisUser, "%"+search+"%"};// username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.tim_e + " DESC ";

        cursor = rdbleDb.query(Stores2.messagesTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder);
        return displace(cursor, recipp);
    }

    public List<Messages> displace(Cursor cursor, String recipp){
        List<Messages> msgs = new ArrayList<>();
        List<String> msgs_ = new ArrayList<>();
        int num = cursor.getCount();

        Stores store=new Stores(context);
        tim_e=1;
        Prefs.putString(Converse.LAST_READ_USER, recipp);
        Prefs.putInt(Converse.LAST_READ, 10);
        for (int i = 0; i < num; i++) {
            cursor.moveToPosition(i);
            Messages messages = new Messages();

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));
            mess_age = cursor.getString(cursor.getColumnIndex(Stores2.mess_age));
            tim_e = cursor.getInt(cursor.getColumnIndex(Stores2.tim_e));
            image = cursor.getString(cursor.getColumnIndex(Stores2.image));
            confirm = cursor.getString(cursor.getColumnIndex(Stores2.confirm));
            msg_id = cursor.getString(cursor.getColumnIndex(Stores2.msg_id));
            id = cursor.getInt(cursor.getColumnIndex(Stores2.id_));

            auth = cursor.getString(cursor.getColumnIndex(Stores2.auth));
            recip = cursor.getString(cursor.getColumnIndex(Stores2.recip));

            messages.setAuth(auth);
            messages.setRecip(recip);

            messages.setMess_age(mess_age);
            messages.setTim_e(tim_e);
            messages.setImage(image);
            messages.setConfirm(confirm);
            messages.setmsg_id(msg_id);
            messages.setId(id);
            messages.setAuthorUp(auth, store);

            if(i==0) {//since desc
                Prefs.putInt(Converse.LAST_READ, tim_e);
            }
            if(!auth.isEmpty())
                msgs.add(messages);
        }
        cursor.close();
        return msgs;
    }
}