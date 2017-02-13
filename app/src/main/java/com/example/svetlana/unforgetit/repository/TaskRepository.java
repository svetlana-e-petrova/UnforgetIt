package com.example.svetlana.unforgetit.repository;

import com.example.svetlana.unforgetit.entity.Task;

import java.util.List;

public interface TaskRepository {

    public static final String DATABASE_NAME = "unforget_it_db";

    String ADD = "addNew";
    String REMOVE = "remove";
    String UPDATE = "update";
    String GET_ALL = "getAll";
    String ERROR_MESSAGE = " failed";
    String STOPWATCH = " took %d ms.";

    boolean addNew(Task task);

    boolean remove(Task task);

    boolean update(Task task);

    List<Task> getAll();

}
