package ng.com.coursecode.piqmessenger.db_Aro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ng.com.coursecode.piqmessenger.model__.Stores2;

/**
 * Created by harro on 18/10/2017.
 */

public class DB_Aro extends SQLiteOpenHelper {

    public static final String KEY_ROWID = "user_id";
    public static final String KEY_LIKED = "user_like";
    public static final String KEY_PROVERB = "user_proverb";
    public static final String KEY_AUTHOR = "user_author";
    public static final String KEY_LANG = "user_lang";
    public static final String KEY_TYPE = "user_type";
    public static final String KEY_WEB_ID = "user_web_id";

    public static final String SQLL_DATEBASE_NAME = "piqmessenger__";
    public static final String SQLL_DATABASE_TABLE = "PROVERB_TABLE";
    private static final int SQLL_DATABASE_VERSION = 3;
    Context context;
    private static DB_Aro instance=null;
    private static SQLiteDatabase Dbinstance=null;

    public DB_Aro(Context context) {
        super(context, SQLL_DATEBASE_NAME, null, SQLL_DATABASE_VERSION);
        this.context=context;
    }

    public static synchronized DB_Aro getHelper(Context context){
        if (instance == null)
            instance = new DB_Aro(context);
        return instance;
    }

    public static synchronized SQLiteDatabase getWDb(Context context){
        if (Dbinstance == null || !(Dbinstance.isOpen()))
            Dbinstance = (DB_Aro.getHelper(context)).getWritableDatabase();

        return Dbinstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Stores2.messagesTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.auth + " TEXT NOT NUll, " +
                Stores2.recip + " TEXT NOT NUll, " +
                Stores2.mess_age + " TEXT NOT NUll, " +
                Stores2.tim_e + " INTEGER(12) NOT NUll, " +
                Stores2.image + " TEXT NOT NUll, " +
                Stores2.confirm + " TEXT NOT NUll, " +
                Stores2.sent + " INTEGER(2) NOT NUll, " +
                Stores2.msg_id + " TEXT NOT NUll)");

        db.execSQL("CREATE TABLE " + Stores2.notifyTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.auth + " TEXT NOT NUll, " +
                Stores2.type + " TEXT NOT NUll, " +
                Stores2.time_stamp + " TEXT NOT NUll, " +
                Stores2.msg_id + " TEXT NOT NUll, " +
                Stores2.posts_id + " TEXT NOT NUll, " +
                Stores2.web_link + " TEXT NOT NUll, " +
                Stores2.seen + " INTEGER(2) NOT NUll)");

        db.execSQL("CREATE TABLE " + Stores2.statusTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.user_name + " TEXT NOT NUll, " +
                Stores2.status_id + " TEXT NOT NUll, " +
                Stores2.time + " TEXT NOT NUll, " +
                Stores2.time_db + " INTEGER(12) NOT NUll, " +
                Stores2.text + " TEXT NOT NUll, " +
                Stores2.type + " TEXT NOT NUll, " +
                Stores2.image + " TEXT NOT NUll, " +
                Stores2.fav + " TEXT NOT NUll, " +
                Stores2.sent + " TEXT NOT NUll, " +
                Stores2.seen + " TEXT NOT NUll)");

        db.execSQL("CREATE TABLE " + Stores2.groupTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.user_name + " TEXT NOT NUll, " +
                Stores2.image + " TEXT NOT NUll, " +
                Stores2.fullname + " TEXT NOT NUll)");

        db.execSQL("CREATE TABLE " + Stores2.postsTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.user_name + " TEXT NOT NUll, " +
                Stores2.reciv + " TEXT NOT NUll, " +
                Stores2.posts_id + " TEXT NOT NUll, " +
                Stores2.time + " TEXT NOT NUll, " +
                Stores2.text + " TEXT NOT NUll, " +
                Stores2.image + " TEXT NOT NUll, " +
                Stores2.fav + " TEXT NOT NUll, " +
                Stores2.liked + " TEXT NOT NUll, " +
                Stores2.likes + " TEXT NOT NUll, " +
                Stores2.Comment + " TEXT NOT NUll)");

        db.execSQL("CREATE TABLE " + Stores2.discoverTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.user_name + " TEXT NOT NUll, " +
                Stores2.discover_id + " TEXT NOT NUll, " +
                Stores2.time + " TEXT NOT NUll, " +
                Stores2.text + " TEXT NOT NUll, " +
                Stores2.image + " TEXT NOT NUll, " +
                Stores2.seen + " TEXT NOT NUll)");

        db.execSQL("CREATE TABLE " + Stores2.profTable + "(" +
                Stores2.id_ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Stores2.user_name + " TEXT NOT NUll, " +
                Stores2.image + " TEXT NOT NUll, " +
                Stores2.fullname + " TEXT NOT NUll)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        clearAll(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldversion, int newVersion) {
        clearAll(db);
    }

    public void editValue(int pos){
    }

    public void clearAll() {
        SQLiteDatabase db = DB_Aro.getWDb(context);
        clearAll(db);
    }

    public void clearAll(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Stores2.groupTable);
        db.execSQL("DROP TABLE IF EXISTS " + Stores2.messagesTable);
        db.execSQL("DROP TABLE IF EXISTS " + Stores2.statusTable);
        db.execSQL("DROP TABLE IF EXISTS " + Stores2.profTable);
        db.execSQL("DROP TABLE IF EXISTS " + Stores2.postsTable);
        db.execSQL("DROP TABLE IF EXISTS " + Stores2.discoverTable);
        onCreate(db);
    }
}
