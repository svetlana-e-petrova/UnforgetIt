package com.example.svetlana.unforgetit.repository.jpa;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.svetlana.unforgetit.entity.Task;
import com.example.svetlana.unforgetit.repository.TaskRepositoryTest;
import com.j256.ormlite.table.TableUtils;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.sql.SQLException;

@RunWith(AndroidJUnit4.class)
public class TaskRepositoryJPAImplTest extends TaskRepositoryTest {

    @Before
    public void setUp() throws SQLException {
        Context context = InstrumentationRegistry.getTargetContext();
        TableUtils.clearTable(DataBaseJPAHelper
                .getInstance(context)
                .getConnectionSource(), Task.class);
        repository = TaskRepositoryJPAImpl.getInstance(context);
    }


}
