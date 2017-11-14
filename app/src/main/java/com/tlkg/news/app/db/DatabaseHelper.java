package com.tlkg.news.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tlkg.news.app.NewsClientApplication;

/**
 * Created by wuxiaoqi on 2017/11/14.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "QXYK";
    private static final int DB_VERSION = 1;
    private static DatabaseHelper instance = null;
    private static SQLiteDatabase db = null;

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DatabaseHelper getInstance() {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(NewsClientApplication.getAppContext(), DB_NAME, null, DB_VERSION);
                }
            }
        }
        return instance;
    }

    public static SQLiteDatabase getDatabase() {
        if (db == null) {
            synchronized (DatabaseHelper.class) {
                if (db == null) {
                    db = getInstance().getWritableDatabase();
                }
            }
        }
        return db;
    }

    public static void closeDatabase() {
        if (db != null) db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NewsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NewsTable.DROP_TABLE);
        onCreate(db);
    }
}
