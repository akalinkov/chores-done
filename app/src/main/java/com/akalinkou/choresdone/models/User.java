package com.akalinkou.choresdone.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.akalinkou.choresdone.R;

@Entity(tableName = "users")
public class User implements Parcelable {

    public static final String EXTRAS_KEY = "user_extra_key";
    public static final String USER_ID_KEY = "user_id_key";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "avatar_resource_id")
    private int avatarResourceId;

    @ColumnInfo(name = "balance")
    private int balance;

    @Ignore
    public User(@NonNull String name, int balance, int avatarResourceId) {
        this.name = name;
        this.avatarResourceId = avatarResourceId;
        this.balance = balance;
    }

    public User(int id, @NonNull String name, int balance, int avatarResourceId) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.avatarResourceId = avatarResourceId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAvatarResourceId() {
        if (avatarResourceId == 0) {
            return R.drawable.ic_default_user;
        }
        return avatarResourceId;
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
        avatarResourceId = in.readInt();
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
        dest.writeInt(avatarResourceId);
        dest.writeInt(balance);
    }
}
