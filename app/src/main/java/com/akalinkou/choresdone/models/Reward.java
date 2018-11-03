package com.akalinkou.choresdone.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "rewards")
public class Reward {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "cost")
    private int cost;

    @Ignore
    public Reward(@NonNull String title, int cost) {
        this.title = title;
        this.cost = cost;
    }

    public Reward(int id, @NonNull String title, int cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getCost() {
        return cost;
    }
}
