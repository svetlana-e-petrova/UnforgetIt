package com.example.svetlana.unforgetit.repository.jdbc;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.svetlana.unforgetit.entity.Task;
import com.example.svetlana.unforgetit.repository.TaskRepositoryTest;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TaskRepositoryJDBCImplTest extends TaskRepositoryTest {

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        DataBaseJDBCHelper
                .getInstance(context)
                .getWritableDatabase()
                .delete(Task.TABLE_NAME, null, null);

        repository = TaskRepositoryJDBCImpl.getInstance(context);
    }
}
