package com.akalinkou.choresdone.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.akalinkou.choresdone.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();
}
