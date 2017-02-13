package com.example.svetlana.unforgetit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.svetlana.unforgetit.model.ModelTask;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "unforgetit_database";
    public static final String TASKS_TABLE = "task_table";
    public static final String TASK_TITLE_COLUMN = "task_title";
    public static final String TASK_DATE_COLUMN = "task_date";
    public static final String TASK_TIME_STAMP_COLUMN = "task_time";
    public static final String TASK_PRIORITY_COLUMN = "task_priority";
    public static final String TASK_STATUS_COLUMN = "task_status";

    private static final String TASK_TABLE_CREATE_SCRIPT = "CREATE TABLE " + TASKS_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK_TITLE_COLUMN + " TEXT NOT NULL, "
            + TASK_DATE_COLUMN + " LONG, " + TASK_TIME_STAMP_COLUMN + " LONG, "
            + TASK_PRIORITY_COLUMN + " INTEGER, " + TASK_STATUS_COLUMN + " INTEGER);";

    public static final String SELECTION_STATUS = TASK_STATUS_COLUMN + " = ?";
    public static final String SELECTION_TIME_STAMP = TASK_TIME_STAMP_COLUMN + " = ?";
    public static final String SELECTION_LIKE_TITLE = TASK_TITLE_COLUMN + " LIKE ?";


    private DBQueryManager queryManager;

    private DBUpdateManager updateManager;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        queryManager = new DBQueryManager(getReadableDatabase());
        updateManager = new DBUpdateManager(getWritableDatabase());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE" + TASKS_TABLE);
        onCreate(db);
    }

    public void saveTask(ModelTask newTask) {
        ContentValues newValues = new ContentValues();

        newValues.put(TASK_TITLE_COLUMN, newTask.getTitle());
        newValues.put(TASK_DATE_COLUMN, newTask.getDate());
        newValues.put(TASK_TIME_STAMP_COLUMN, newTask.getTimeStamp());
        newValues.put(TASK_PRIORITY_COLUMN, newTask.getPriority());
        newValues.put(TASK_STATUS_COLUMN, newTask.getStatus());

        getWritableDatabase().insert(TASKS_TABLE, null, newValues);
    }

    public DBQueryManager getQueryManager() {
        return queryManager;
    }

    public DBUpdateManager getUpdateManager() {
        return updateManager;
    }

    public void removeTask(long timeStamp) {
        getWritableDatabase().delete(TASKS_TABLE, SELECTION_TIME_STAMP, new String[]{Long.toString(timeStamp)});
    }
}
