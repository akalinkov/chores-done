package com.akalinkou.choresdone.db.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.akalinkou.choresdone.models.User;
import com.akalinkou.choresdone.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void addUser(@NonNull User user) {
        userRepository.addUser(user);
    }
}
