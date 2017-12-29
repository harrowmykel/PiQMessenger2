package ng.com.coursecode.piqmessenger.database__;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.extLib.Toasta;
import ng.com.coursecode.piqmessenger.model__.FrndsData;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.Stores2;

/**
 * Created by harro on 10/10/2017.
 */

public class Group_tab implements Parcelable {

    public String user_name;
    public String user_id;
    public String fullname;
    public String image;
    public String friends;
    public FrndsData frndsData;
    private String like;

    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    Context context;
    Cursor cursor;

    public Group_tab(){
    }
    public Group_tab(String user_name,String user_id,
                     String fullname,String image){
        setUser_id(user_id);
        setUser_name(user_name);
        setFullname(fullname);
        setImage(image);
    }

    public boolean save(Context context1){
        context=context1;
        if(context!=null){
            wrtable = DB_Aro.getWDb(context);;
            contentValues=new ContentValues();
            contentValues.put(Stores2.user_name, getUser_name().toLowerCase());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.fullname, getFullname());

            String toFind=Stores2.user_name + " = ?";
            String sTable=Stores2.groupTable;
            String[] sArray={getUser_name()};
            String[] projection = {Stores2.user_name};

            if(getUser_name()==null){
                Toasta.makeText(context1, "null", Toast.LENGTH_SHORT);
                return false;
            }
            Cursor result = wrtable.query(sTable,
                    null,
                    toFind,
                    sArray,
                    null,
                    null,
                    null
            );
            try {
                if (result != null && result.getCount() > 0) {
                    String[] sf = sArray;
                    wrtable.update(sTable, contentValues, toFind, sf);
                    result.close();
                } else {
                    wrtable.insert(sTable, null, contentValues);
                }
                wrtable.close();
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Group_tab> listAll(Context context_){
        context=context_;
        rdbleDb = DB_Aro.getWDb(context);;
        List<Group_tab> msgs=new ArrayList<>();

        String[] projection = {Stores2.user_name,
                Stores2.fullname,
                Stores2.image};

        String selection = null;
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.id_ + " DESC";

        cursor = rdbleDb.query(Stores2.groupTable,  // The table to query
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
            Group_tab Group_tab=new Group_tab();

            user_name=cursor.getString(cursor.getColumnIndex(Stores2.user_name));
            fullname=cursor.getString(cursor.getColumnIndex(Stores2.fullname));
            image=cursor.getString(cursor.getColumnIndex(Stores2.image));

            Group_tab.setUser_name(user_name);
            Group_tab.setFullname(fullname);
            Group_tab.setFriends("1");
            Group_tab.setImage(image);
            msgs.add(Group_tab);
        }
        cursor.close();
        return msgs;
    }

    public static String getFullname(Context context_, String username){
        SQLiteDatabase rdbleDb;
        //DB_Aro dbHelper;
        Context context;

        String fname=username=username.toLowerCase();
        context=context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String[] projection = {Stores2.user_name,
                Stores2.fullname};

        String selection = Stores2.fullname+" = ? ";
        String[] selectionArgs = {username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.id_ + " DESC";

        Cursor cursor1;
        cursor1 = rdbleDb.query(Stores2.groupTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        int num=cursor1.getCount();


        for(int i=0; i<num; i++){
            cursor1.moveToPosition(i);
            fname=cursor1.getString(cursor1.getColumnIndex(Stores2.fullname));
        }
        cursor1.close();
        return fname;
    }


    public String getFullname(String username){

        String fname=username=username.toLowerCase();
        rdbleDb = DB_Aro.getWDb(context);

        String[] projection = {Stores2.user_name,
                Stores2.fullname};

        String selection = Stores2.fullname+" = ? ";
        String[] selectionArgs = {username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.id_ + " DESC";

        Cursor cursor1;
        cursor1 = rdbleDb.query(Stores2.groupTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        int num=cursor1.getCount();

        for(int i=0; i<num; i++){
            cursor1.moveToPosition(1);
            fname=cursor1.getString(cursor1.getColumnIndex(Stores2.fullname));
        }
        cursor1.close();
        rdbleDb.close();
        return fname;
    }


    public FrndsData getFrndsData() {
        return frndsData;
    }

    public void setFrndsData(FrndsData frndsData) {
        this.frndsData = frndsData;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return (image!=null && !image.isEmpty())?image:"";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user_name);
        out.writeString(user_id);
        out.writeString(fullname);
        out.writeString(image);
        out.writeString(friends);
        out.writeString(like);
    }

    public static final Parcelable.Creator<Group_tab> CREATOR
            = new Parcelable.Creator<Group_tab>() {
        public Group_tab createFromParcel(Parcel in) {
            return new Group_tab(in);
        }

        public Group_tab[] newArray(int size) {
            return new Group_tab[size];
        }
    };

    private Group_tab(Parcel in) {
        user_name= in.readString();
        user_id= in.readString();
        fullname= in.readString();
        image= in.readString();
        friends= in.readString();
        like= in.readString();
    }

    public static Group_tab getInfo(Context context_, String username) {
        Group_tab Group_tab=new Group_tab();
        SQLiteDatabase rdbleDb;
        //DB_Aro dbHelper;
        Context context;

        String fname=username=username.toLowerCase();
        String img_loc=Stores.imgDefault;
        context=context_;

        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);

        String[] projection = {Stores2.user_name,
                Stores2.fullname,
                Stores2.image};

        String selection = Stores2.user_name+" = ? ";
        String[] selectionArgs = {username};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.id_ + " DESC";

        Cursor cursor1;
        cursor1 = rdbleDb.query(Stores2.groupTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        int num=cursor1.getCount();

        for(int i=0; i<num; i++){
            cursor1.moveToPosition(i);
            fname=cursor1.getString(cursor1.getColumnIndex(Stores2.fullname));
            img_loc=cursor1.getString(cursor1.getColumnIndex(Stores2.image));
        }
        cursor1.close();
        Group_tab.setFullname(fname);
        Group_tab.setImage(img_loc);
        //dbHelper.close();
        rdbleDb.close();
        return Group_tab;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getLike() {
        return like;
    }

    public void delete(Context context) {
        String fname=getUser_name();
        rdbleDb = DB_Aro.getWDb(context);

        String selection = Stores2.user_name+" = ? ";
        String[] selectionArgs = {fname};
        rdbleDb.delete(Stores2.groupTable, selection, selectionArgs);

        rdbleDb.close();
    }

    public void clear(Context context) {
        rdbleDb = DB_Aro.getWDb(context);
        String selection = null;
        String[] selectionArgs = null;
        rdbleDb.delete(Stores2.groupTable, selection, selectionArgs);
        rdbleDb.close();
    }
}
