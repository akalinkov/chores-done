package com.akalinkou.choresdone.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.akalinkou.choresdone.AppExecutors;
import com.akalinkou.choresdone.db.AppDatabase;
import com.akalinkou.choresdone.db.dao.UserDao;
import com.akalinkou.choresdone.models.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> users;

    public UserRepository(Application application) {
        userDao = AppDatabase.getDatabase(application).userDao();
        users = userDao.getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void addUser(final User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.addUser(user);
            }
        });
    }
}
