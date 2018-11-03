package com.akalinkou.choresdone.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "tasks",
        indices = {@Index("user_id")},
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "value")
    private int value;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "status")
    private boolean isComplete;

    @Ignore
    public Task(@NonNull String title,
                int value,
                int userId,
                boolean isComplete) {
        this.title = title;
        this.value = value;
        this.userId = userId;
        this.isComplete = isComplete;
    }

    public Task(int id,
                @NonNull String title,
                int value,
                int userId,
                boolean isComplete) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
