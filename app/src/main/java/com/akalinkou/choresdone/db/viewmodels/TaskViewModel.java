package com.akalinkou.choresdone.db.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.akalinkou.choresdone.models.Task;
import com.akalinkou.choresdone.repositories.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private static final String TAG = TaskViewModel.class.getSimpleName();

    private TaskRepository taskRepo;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepo = new TaskRepository(application);
    }


    public LiveData<List<Task>> getAllTasks() {
        return taskRepo.getAllTasks();
    }

    public void addTask(@NonNull Task task) {
        Log.d(TAG, "addTask: " + task.getTitle());
        taskRepo.addTask(task);
    }


    public Task get(int position) {
        Log.d(TAG, "get: a task object at position #" + position);
        return taskRepo
                .getAllTasks()
                .getValue()
                .get(position);
    }

    public int size() {
        Log.d(TAG, "size: get tasks size");
        if (taskRepo.getAllTasks() == null) {
            return 0;
        }
        if (taskRepo.getAllTasks().getValue() == null) {
            return 0;
        }
        return taskRepo.getAllTasks().getValue().size();
    }

    public void toggleTaskStatus(int id, boolean currentStatus) {
        taskRepo.updateTaskStatus(id, !currentStatus);
    }

    public void deleteTask(Task task) {
        taskRepo.deleteTask(task);
    }
}
