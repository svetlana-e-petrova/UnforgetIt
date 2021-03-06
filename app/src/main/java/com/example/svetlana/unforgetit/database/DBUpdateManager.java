package com.example.svetlana.unforgetit.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.svetlana.unforgetit.model.ModelTask;

public class DBUpdateManager {

    private SQLiteDatabase database;

    public DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    public void updateTitle(long timeStamp, String title) {
        update(DBHelper.TASK_TITLE_COLUMN, timeStamp, title);
    }

    public void updateDate(long timeStamp, Long date) {
        update(DBHelper.TASK_DATE_COLUMN, timeStamp, date);
    }

    public void updatePriority(long timeStamp, int priority) {
        update(DBHelper.TASK_PRIORITY_COLUMN, timeStamp, priority);
    }

    public void updateStatus(long timeStamp, int status) {
        update(DBHelper.TASK_STATUS_COLUMN, timeStamp, status);
    }

    public void updateTask(ModelTask task) {
        updateTitle(task.getTimeStamp(), task.getTitle());
        updateDate(task.getTimeStamp(), task.getDate());
        updatePriority(task.getTimeStamp(), task.getPriority());
        updateStatus(task.getTimeStamp(), task.getStatus());
    }


    private void update(String column, long key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key, null);
    }

    private void update(String column, long key, long value) {
        ContentValues cv = new ContentValues();
        cv.put(column, value);
        database.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key, null);
    }
}
