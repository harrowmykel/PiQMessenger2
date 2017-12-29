package ng.com.coursecode.piqmessenger.database__;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ng.com.coursecode.piqmessenger.db_Aro.DB_Aro;
import ng.com.coursecode.piqmessenger.model__.Model__2;
import ng.com.coursecode.piqmessenger.model__.Stores;
import ng.com.coursecode.piqmessenger.model__.Stores2;

/**
 * Created by harro on 10/10/2017.
 */

public class Posts_tab {

    public String user_name;
    public String posts_id;
    public String time;
    public String text;
    public String image;
    public String user_image;
    public String seen;
    public String fav;
    public String liked;

    public String reciv;
    public String likes;
    public String Comment;

    public String fullname;

    ContentValues contentValues;
    SQLiteDatabase wrtable, rdbleDb;
    //DB_Aro dbHelper;
    Context context;
    Cursor cursor;

    public Posts_tab(){

    }

    public Posts_tab(String user_name, String posts_id,
                     String time, String text, String image, String seen){
        setUser_name(user_name);
        setPosts_id(posts_id);
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

            contentValues.put(Stores2.user_name, getUser_name());
            contentValues.put(Stores2.reciv, getReciv());
            contentValues.put(Stores2.posts_id, getPosts_id());
            contentValues.put(Stores2.time, getTime());
            contentValues.put(Stores2.text, getText());
            contentValues.put(Stores2.image, getImage());
            contentValues.put(Stores2.fav, getFav());
            contentValues.put(Stores2.liked, getLiked());
            contentValues.put(Stores2.likes, getLikes());
            contentValues.put(Stores2.Comment, getComment());

            String toFind=Stores2.posts_id + " = ?";
            String sTable=Stores2.postsTable;
            String[] sArray=new String[]{getPosts_id()};
            Cursor result = wrtable.query(
                    sTable,
                    null,
                    toFind,
                    sArray,
                    null,
                    null,
                    null
            );
            try {

                if (result != null && result.getCount() > 0) {
                    String[] sf=sArray;
                    wrtable.update(sTable, contentValues, toFind, sf);
                    result.close();
                }else{
                    wrtable.insert(sTable, null, contentValues);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public Model__2 listAll(Context context_, int page){
        context=context_;
        // dbHelper = DB_Aro.getHelper(context);
        rdbleDb = DB_Aro.getWDb(context);
        List<Posts_tab> msgs=new ArrayList<>();

        String[] projection = {Stores2.user_name};

        String selection = null;
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Stores2.id_ + " DESC";

        cursor = rdbleDb.query(Stores2.postsTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        int num=cursor.getCount();

        projection = new String[]{Stores2.user_name,
                Stores2.posts_id,
                Stores2.reciv,
                Stores2.time,
                Stores2.text,
                Stores2.fav,
                Stores2.liked,
                Stores2.likes,
                Stores2.Comment,
                Stores2.image};

        selection = null;
        selectionArgs = new String[]{};


        int pages_left=((num/ Stores.dbReqCounts))-page;
        String limit=Stores.limitDb(num, Stores.dbReqCounts, page);
        //offset, limit

        cursor = rdbleDb.query(false, Stores2.postsTable,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder,                                 // The sort order
                limit
        );
        num=cursor.getCount();

        for(int i=0; i<num; i++){

            cursor.moveToNext();
            Posts_tab posts_tab=new Posts_tab();

            user_name=cursor.getString(cursor.getColumnIndex(Stores2.user_name));
            posts_id=cursor.getString(cursor.getColumnIndex(Stores2.posts_id));
            reciv=cursor.getString(cursor.getColumnIndex(Stores2.reciv));
            time=cursor.getString(cursor.getColumnIndex(Stores2.time));
            text=cursor.getString(cursor.getColumnIndex(Stores2.text));
            fav=cursor.getString(cursor.getColumnIndex(Stores2.fav));
            liked=cursor.getString(cursor.getColumnIndex(Stores2.liked));
            likes=cursor.getString(cursor.getColumnIndex(Stores2.likes));
            Comment=cursor.getString(cursor.getColumnIndex(Stores2.Comment));
            image=cursor.getString(cursor.getColumnIndex(Stores2.image));

            Users_prof users_prof=Users_prof.getInfo(context_, user_name);

            fullname=users_prof.getFullname();
            user_image=users_prof.getImage();

            posts_tab.setUser_name(user_name);
            posts_tab.setPosts_id(posts_id);
            posts_tab.setReciv(reciv);
            posts_tab.setTime(time);
            posts_tab.setText(text);
            posts_tab.setFav(fav);
            posts_tab.setLiked(liked);
            posts_tab.setLikes(likes);
            posts_tab.setComment(Comment);
            posts_tab.setImage(image);
            posts_tab.setFullname(fullname);
            posts_tab.setUser_image(user_image);

            msgs.add(posts_tab);
        }
        cursor.close();
        //dbHelper.close();
        rdbleDb.close();

        Model__2 model=new Model__2();

        model.setPagesLeft(""+pages_left);
        model.setDb_resultPosts(msgs);
        return model;
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


    public String getPosts_id() {
        return posts_id;
    }

    public void setPosts_id(String posts_id) {
        this.posts_id = posts_id;
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

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getReciv() {
        return reciv;
    }

    public void setReciv(String reciv) {
        this.reciv = reciv;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

}
