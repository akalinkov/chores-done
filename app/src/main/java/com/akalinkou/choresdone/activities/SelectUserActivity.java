package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.models.User;
import com.akalinkou.choresdone.adapters.UserButtonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectUserActivity extends AppCompatActivity {

    private static final String TAG = SelectUserActivity.class.getSimpleName();

    @BindView(R.id.txt_screen_title)
    TextView screenTitle;

    @BindView(R.id.rv_users_list)
    RecyclerView usersListView;

    private UserButtonAdapter userButtonAdapter;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        usersListView.setLayoutManager(layoutManager);
        userButtonAdapter = new UserButtonAdapter(this, new ArrayList<User>());
        usersListView.setAdapter(userButtonAdapter);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                userButtonAdapter.setUsers(users);
            }
        });
    }

    public static void start(Context context) {
        Log.d(TAG, "start: start SelectUserActivity intent");
        Intent selectUserActivity = new Intent(context, SelectUserActivity.class);
        context.startActivity(selectUserActivity);
    }
}
