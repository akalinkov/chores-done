package com.akalinkou.choresdone;

public class User {

    /**
     * The flag that describes if the user object is a fake one created to display new user button
     * in RecyclerView
     **/
    private boolean newUserButtonFlag;
    private String userName;

    private String profileImagePath;

    public String getUserName() {
        if (isNewUserButton()) {
            return "New User";
        }
        return userName;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfileImagePath(String path) {
        profileImagePath = path;
    }

    public void setNewUserButtonFlag(boolean newUserButtonFlag) {
        this.newUserButtonFlag = newUserButtonFlag;
    }

    public boolean isNewUserButton() {
        return newUserButtonFlag;
    }
}
