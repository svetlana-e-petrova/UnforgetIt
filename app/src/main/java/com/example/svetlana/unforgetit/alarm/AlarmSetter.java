package com.example.svetlana.unforgetit.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.svetlana.unforgetit.database.DBHelper;
import com.example.svetlana.unforgetit.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

public class AlarmSetter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper dbHelper = new DBHelper(context);


        AlarmHelper alarmHelper = AlarmHelper.getInstance();
        alarmHelper.init(context);

        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(dbHelper.getQueryManager().getTasks(DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)}, DBHelper.TASK_DATE_COLUMN));

        for (ModelTask task : tasks) {
            if (task.getDate() != 0) {
                alarmHelper.setAlarm(task);
            }
        }

    }
}
