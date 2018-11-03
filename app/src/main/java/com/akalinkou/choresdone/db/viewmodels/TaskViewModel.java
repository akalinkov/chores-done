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

    public LiveData<List<Task>> getTasks(int userId) {
        return taskRepo.getTasks(userId);
    }

    public void addTask(@NonNull Task task) {
        Log.d(TAG, "addTask: " + task.getTitle());
        taskRepo.addTask(task);
    }

    public void toggleTaskStatus(int id, boolean currentStatus) {
        taskRepo.updateTaskStatus(id, !currentStatus);
    }

    public void deleteTask(Task task) {
        taskRepo.deleteTask(task);
    }
}
