package com.akalinkou.choresdone.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "value")
    private int value;

    @NonNull
    @ColumnInfo(name = "due_date")
    private String dueDate;

    @ColumnInfo(name = "status")
    private boolean isComplete;

    @Ignore
    public Task(@NonNull String title, int value, @NonNull String dueDate, boolean isComplete) {
        this.title = title;
        this.value = value;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }

    public Task(int id, @NonNull String title, int value, @NonNull String dueDate, boolean isComplete) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.dueDate = dueDate;
        this.isComplete = isComplete;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getValue() {
        return value;
    }

    @NonNull
    public String getDueDate() {
        return dueDate;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
