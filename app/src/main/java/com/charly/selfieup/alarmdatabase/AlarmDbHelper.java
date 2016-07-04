package com.charly.selfieup.alarmdatabase;

/**
 * Created by Charly on 7/3/16.
 * Based on the tutorial from http://yaronvazana.com/2015/08/01/android-sqlite-database/
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class AlarmDbHelper extends SQLiteOpenHelper{

    private static AlarmDbHelper instance;
    public static final String TABLE_ALARMS = "alarms";
    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_TIME = "time";
    public static final String COL_DAYS = "days";
    private static final String DATABASE_NAME = "my_alarm.db";
    private static final int DATABASE_VERSION = 3;

    public static synchronized AlarmDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AlarmDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private AlarmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ALARMS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TIME + " TEXT NOT NULL, "
                + COL_DAYS + " TEXT NOT NULL"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS + ";");
        onCreate(db);
    }

    public long insert(String tableName, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(tableName, null, values);
        db.close();
        return id;

    }

    public int update(String tableName, long id, ContentValues values){

        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        int ret = db.update(tableName, values, selection, selectionArgs);
        db.close();
        return ret;
    }

    public int delete(String tableName, long id) {

        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        int ret = db.delete(tableName, selection, selectionArgs);
        db.close();
        return ret;

    }

    public Cursor query(SQLiteDatabase db, String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderedBy) {

        if(columns == null) {
            columns = new String[]{COL_ID, COL_TIME, COL_DAYS};
        }
        Cursor c = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderedBy);
        return c;
    }
}
