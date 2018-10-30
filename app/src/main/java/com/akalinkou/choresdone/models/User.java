package com.akalinkou.choresdone.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "age")
    private int age;
    @ColumnInfo(name = "profile_image_path")
    private String imagePath;

    @Ignore
    public User(@NonNull String name, int age, String imagePath) {
        this.name = name;
        this.age = age;
        this.imagePath = imagePath;
    }

    @Ignore
    public User(@NonNull String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public User(int id, String name, int age, String imagePath) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }
}
