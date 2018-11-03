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

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getAllUsers() {
        return userRepository.getUsers();
    }

    public void addUser(@NonNull User user) {
        userRepository.addUser(user);
    }

    public LiveData<User> getUser(int userId) {
        return userRepository.getUser(userId);
    }

    public void updateBalance(User user) {
        userRepository.updateUser(user);
    }
}
