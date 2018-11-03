package com.akalinkou.choresdone.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class User implements Parcelable {

    public static final String EXTRAS_KEY = "user_extra_key";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "profile_image_path")
    private String imagePath;

    @ColumnInfo(name = "balance")
    private int balance;

    @Ignore
    public User(@NonNull String name, int balance, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
        this.balance = balance;
    }

    public User(int id, @NonNull String name, int balance, String imagePath) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.imagePath = imagePath;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Ignore
    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imagePath = in.readString();
        balance = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imagePath);
        dest.writeInt(balance);
    }
}
