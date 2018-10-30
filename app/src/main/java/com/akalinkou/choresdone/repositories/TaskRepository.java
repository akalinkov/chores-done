package com.akalinkou.choresdone.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.akalinkou.choresdone.AppExecutors;
import com.akalinkou.choresdone.db.AppDatabase;
import com.akalinkou.choresdone.db.dao.TaskDao;
import com.akalinkou.choresdone.models.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        taskDao = AppDatabase.getDatabase(application).taskDao();
    }

    public LiveData<List<Task>> getAllTasks() {
        if (tasks == null) {
            tasks = taskDao.getAllTasks();
        }
        return tasks;
    }

    public void addTask(final Task task) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.addTask(task);
            }
        });
    }

    public void updateTaskStatus(final int id, final boolean status) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTaskStatus(id, status);
            }
        });
    }

    public void deleteTask(final Task task) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }
}
