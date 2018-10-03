package com.akalinkou.choresdone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private final Context context;
    private List<User> users;

    public UsersAdapter(Context context, @NonNull List<User> users) {
        this.context = context;
        verifyNewUserButton(users);
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(context)
                .inflate(R.layout.item_user_profile, viewGroup, false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {

        userViewHolder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }

    private List<User> verifyNewUserButton(List<User> users) {
        if (!newUserButtonPresent(users)) {
            User newUserButton = new User();
            newUserButton.setNewUserButtonFlag(true);
            users.add(newUserButton);
        }
        return users;
    }

    private boolean newUserButtonPresent(List<User> users) {
        for (User user : users) {
            if (user.isNewUserButton()) {
                return true;
            }
        }
        return false;
    }
}
