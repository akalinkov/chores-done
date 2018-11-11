package com.akalinkou.choresdone.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.akalinkou.choresdone.helpers.AppExecutors;
import com.akalinkou.choresdone.db.AppDatabase;
import com.akalinkou.choresdone.db.dao.UserDao;
import com.akalinkou.choresdone.models.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> users;
    private LiveData<User> user;
    private int userId;

    public UserRepository(Application application) {
        userDao = AppDatabase.getDatabase(application).userDao();
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = userDao.getAllUsers();
        }
        return users;
    }

    public LiveData<User> getUser(final int userId) {
        if (user == null || this.userId != userId) {
            user = userDao.getUser(userId);
            this.userId = userId;
        }
        return user;
    }

    public void addUser(final User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.addUser(user);
            }
        });
    }


    public void updateUser(final User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.updateUser(user);
            }
        });
    }
}
