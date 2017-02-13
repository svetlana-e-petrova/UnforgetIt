package com.example.svetlana.unforgetit.repository.jpa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.svetlana.unforgetit.entity.Task;
import com.example.svetlana.unforgetit.repository.TaskRepository;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseJPAHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 1;


    private Dao<Task, Long> taskDAO;
    private static final AtomicInteger usageCounter = new AtomicInteger(0);

    private static DataBaseJPAHelper helper;

    synchronized static DataBaseJPAHelper getInstance(Context context) {
        usageCounter.incrementAndGet();
        return (helper = helper == null ? new DataBaseJPAHelper(context) : helper);
    }

    private DataBaseJPAHelper(Context context) {
        super(context, TaskRepository.DATABASE_NAME, null, DATABASE_VERSION);
    }

    Dao<Task, Long> getTaskDAO() throws SQLException {
        if (taskDAO == null) {
            taskDAO = getDao(Task.class);
        }
        return taskDAO;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(this.getClass().getName(), "onCreate");
            TableUtils.createTable(connectionSource, Task.class);
        } catch (SQLException e) {
            Log.e(this.getClass().getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(this.getClass().getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(this.getClass().getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }

    }
}
