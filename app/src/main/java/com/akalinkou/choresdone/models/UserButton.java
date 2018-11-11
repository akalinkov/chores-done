package com.akalinkou.choresdone.models;

public class UserButton {
    private User user;

    public UserButton() {}

    public UserButton(User user) {
        this.user = user;
    }

    public int getAvatarResourceId() {
        if (user == null) {
            return 0;
        }
        return user.getAvatarResourceId();
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
