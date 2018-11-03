package com.akalinkou.choresdone.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.akalinkou.choresdone.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void addTask(Task task);

    @Query("SELECT * FROM tasks WHERE user_id = :id ORDER BY title ASC")
    LiveData<List<Task>> getTasks(int id);

    @Query("UPDATE tasks SET status = :status WHERE id = :id")
    void updateTaskStatus(int id, boolean status);

    @Delete
    void deleteTask(Task task);
}
