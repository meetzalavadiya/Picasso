package com.mynewacc.dreamteam11.cricket.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mynewacc.dreamteam11.cricket.fcm.NotificationHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_PUSH_TABLE = "CREATE TABLE push_table(linkId TEXT, title TEXT, body TEXT, level TEXT, deadLine TEXT, currentTime TEXT, post_url TEXT, is_seen TEXT)";
    private static final String CREATE_TEAM_TABLE = "CREATE TABLE team_table(linkId TEXT, post_title TEXT, level TEXT, post_url TEXT, post_content TEXT, post_img TEXT, post_time TEXT, deadLine TEXT)";
    public static final String DATABASE_NAME = "dream_team_db.db";
    public static final String PUSH_TABLE_NAME = "push_table";
    private static DatabaseHelper instance;
    private Context mContext;

    public static synchronized DatabaseHelper getInstance(Context context) {
        DatabaseHelper databaseHelper;
        synchronized (DatabaseHelper.class) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
                databaseHelper = instance;
            }
            return databaseHelper;
        }
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_PUSH_TABLE);
        sQLiteDatabase.execSQL(CREATE_TEAM_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS push_table");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS team_table");
        onCreate(sQLiteDatabase);
    }

    public boolean pushSeen(String str) {
        try {
            NotificationHelper.getHelper(this.mContext).cancelNotification(AppUtils.getPostId(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        SQLiteDatabase openDatabaseInWritableMode = openDatabaseInWritableMode();
        boolean z = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AppUtils.IS_SEEN, AppUtils.BANNER_YES);
            if (openDatabaseInWritableMode.update(PUSH_TABLE_NAME, contentValues, "linkId = ?", new String[]{str}) > 0) {
                z = true;
            }
        } catch (Exception unused) {
        } catch (Throwable unused2) {
            openDatabaseInWritableMode.close();
        }
        openDatabaseInWritableMode.close();
        return z;
    }

    public SQLiteDatabase openDatabaseInWritableMode() {
        return getWritableDatabase();
    }

    public boolean checkIfExist(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        boolean z = false;
        try {
            if (writableDatabase.rawQuery("SELECT * FROM push_table WHERE linkId= '" + str + "'", null).getCount() > 0) {
                z = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable unused) {
            writableDatabase.close();
            writableDatabase.close();
        }
        writableDatabase.close();
        writableDatabase.close();
        return z;
    }

    public int deleteExtraPushData() {
        SQLiteDatabase openDatabaseInWritableMode = openDatabaseInWritableMode();
        try {
            openDatabaseInWritableMode.execSQL("DELETE FROM push_table  WHERE linkId  NOT IN (" + "SELECT linkId FROM push_table ORDER BY currentTime DESC LIMIT 15" + ")");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable unused) {
            openDatabaseInWritableMode.close();
        }
        openDatabaseInWritableMode.close();
        return 0;
    }

    public int deleteExtraPostData() {
        SQLiteDatabase openDatabaseInWritableMode = openDatabaseInWritableMode();
        try {
            openDatabaseInWritableMode.execSQL("DELETE FROM team_table  WHERE linkId  NOT IN (" + "SELECT linkId FROM team_table ORDER BY deadLine DESC LIMIT 60" + ")");
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable unused) {
            openDatabaseInWritableMode.close();
        }
        openDatabaseInWritableMode.close();
        return 0;
    }
}
