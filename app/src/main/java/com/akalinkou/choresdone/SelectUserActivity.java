package com.akalinkou.choresdone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectUserActivity extends AppCompatActivity {

    @BindView(R.id.txt_screen_title) TextView screenTitle;

    @BindView(R.id.rv_users_list) RecyclerView usersListView;

    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        usersListView.setLayoutManager(layoutManager);
        usersListView.setAdapter(new UsersAdapter(this, new ArrayList<User>()));
    }
}
