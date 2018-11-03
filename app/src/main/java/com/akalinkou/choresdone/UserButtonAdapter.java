package com.akalinkou.choresdone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akalinkou.choresdone.activities.TasksActivity;
import com.akalinkou.choresdone.activities.NewUserActivity;
import com.akalinkou.choresdone.models.User;
import com.akalinkou.choresdone.models.UserButton;

import java.util.ArrayList;
import java.util.List;

public class UserButtonAdapter extends RecyclerView.Adapter<UserViewHolder>
implements UserViewHolder.ProfileClickListener {

    private static final String TAG = UserButtonAdapter.class.getSimpleName();

    private final Context context;
    private List<UserButton> buttons;

    public UserButtonAdapter(Context context, @NonNull List<User> users) {
        Log.d(TAG, "UserButtonAdapter: constructor");
        this.context = context;
        this.buttons = wrapUsersIntoButtons(users);
    }

    private List<UserButton> wrapUsersIntoButtons(@NonNull List<User> users) {
        Log.d(TAG, "wrapUsersIntoButtons: users size - " + users.size());
        List<UserButton> buttons = new ArrayList<>();
        for (User user : users) {
            buttons.add(new UserButton(user));
        }
        buttons.add(new UserButton());
        return buttons;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Log.d(TAG, "onCreateViewHolder: inflate view for user #" + position);
        View userView = LayoutInflater.from(context)
                .inflate(R.layout.item_user_profile, viewGroup, false);
        return new UserViewHolder(userView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: bind button at position #" + position);
        userViewHolder.bind(buttons.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        if (buttons == null) {
            return 0;
        }
        return buttons.size();
    }

    @Override
    public void onProfileClick(int position) {
        Log.d(TAG, "onProfileClick: clicked profile #" + position);
        if (buttons.get(position).isDefaultButton()) {
            NewUserActivity.startIntent(context);
        } else {
            TasksActivity.start(context, userAtPosition(position));
        }
    }

    public void setUsers(List<User> users) {
        Log.d(TAG, "setUsers: set list of users for UserButtonAdapter");
        buttons = wrapUsersIntoButtons(users);
        notifyDataSetChanged();
    }

    private User userAtPosition(int position) {
        return buttons.get(position).getUser();
    }
}
