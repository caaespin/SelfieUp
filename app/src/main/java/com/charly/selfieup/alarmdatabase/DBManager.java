package com.charly.selfieup.alarmdatabase;

/**
 * Created by Charly on 7/3/16.
 * Based on the tutorial from http://yaronvazana.com/2015/08/01/android-sqlite-database/
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import com.charly.selfieup.alarmdatabase.Alarm;

public class DBManager {

    private static DBManager instance = null;
    private static Context context;

    public static DBManager instance() {
        if(instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public static void initialize(Context c) {
        context = c;
    }

    private DBManager() {
    }

    public long insertAlarm(Alarm alarm) {
        try {

            ContentValues values = getContentValues(alarm);
            long id = AlarmDbHelper.getInstance(context).insert(AlarmDbHelper.TABLE_ALARMS, values);
            return id;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Alarm getAlarm(long id) {

        SQLiteDatabase db = AlarmDbHelper.getInstance(context).getReadableDatabase();
        Cursor c = AlarmDbHelper.getInstance(context).query(db, AlarmDbHelper.TABLE_ALARMS, null, AlarmDbHelper.COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, AlarmDbHelper.COL_ID);
        c.moveToFirst();
        Alarm alarm = createAlarmFromCursor(c);
        c.close();
        db.close();
        return alarm;
    }

    public int deleteAlarm(long id) {
        return AlarmDbHelper.getInstance(context).delete(AlarmDbHelper.TABLE_ALARMS, id);
    }

    public int updateAlarm(Alarm alarm) {

        ContentValues values = getContentValues(alarm);
        try {
            return AlarmDbHelper.getInstance(context).update(AlarmDbHelper.TABLE_ALARMS, alarm.getID(), values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private ContentValues getContentValues(Alarm alarm) {
        ContentValues values = new ContentValues(2);
        values.put(AlarmDbHelper.COL_TIME, alarm.getTime());
        values.put(AlarmDbHelper.COL_DAYS, alarm.getDays());
        return values;
    }

    public List<Alarm> getAllAlarms() {
        List<Alarm> alarmsList = new ArrayList<>();
        SQLiteDatabase db = AlarmDbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = AlarmDbHelper.getInstance(context).query(db, AlarmDbHelper.TABLE_ALARMS, null, null, null, null, null, AlarmDbHelper.COL_ID);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alarm a = createAlarmFromCursor(cursor);
                // Adding alarm to list
                alarmsList.add(a);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return alarmsList;
    }

    private Alarm createAlarmFromCursor(Cursor c) {
        Alarm alarm = new Alarm();
        alarm.setID(Long.parseLong(c.getString(0)));
        alarm.setTime(c.getString(1));
        alarm.setDays(c.getString(2));
        return alarm;
    }
}
