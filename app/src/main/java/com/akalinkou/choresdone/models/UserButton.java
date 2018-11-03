package com.akalinkou.choresdone.models;

public class UserButton {
    private User user;

    public UserButton() {}

    public UserButton(User user) {
        this.user = user;
    }

    public String getProfileImagePath() {
        if (user == null) {
            return "";
        }
        return user.getImagePath();
    }

    public String getUserName() {
        if (user == null) {
            return "New User";
        }
        return user.getName();
    }

    public boolean isDefaultButton(){
        return user == null;
    }

    public User getUser() {
        return user;
    }
}
