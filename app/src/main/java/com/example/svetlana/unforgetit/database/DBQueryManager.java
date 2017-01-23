package com.example.svetlana.unforgetit.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.svetlana.unforgetit.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

public class DBQueryManager {

    private SQLiteDatabase database;

    public DBQueryManager(SQLiteDatabase database) {
        this.database = database;
    }

    public ModelTask getTask(long timeStamp) {
        ModelTask task = null;

        Cursor c = database.query(DBHelper.TASKS_TABLE, null, DBHelper.SELECTION_TIME_STAMP, new String[]{Long.toString(timeStamp)}, null, null, null);

        if (c.moveToFirst()) {
            String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
            long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
            int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
            int status = c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN);

            task = new ModelTask(title, date, timeStamp, priority, status);
        }

        return task;
    }

    public List<ModelTask> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<ModelTask> tasks = new ArrayList<>();

        Cursor c = database.query(DBHelper.TASKS_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                long timestamp = c.getLong(c.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));
                int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN);

                ModelTask modelTask = new ModelTask(title, date, timestamp, priority, status);
                tasks.add(modelTask);
            } while (c.moveToNext());
        }

        c.close();

        return tasks;
    }
}
