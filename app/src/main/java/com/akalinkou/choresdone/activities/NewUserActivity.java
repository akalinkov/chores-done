package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewUserActivity extends AppCompatActivity {

    @BindView(R.id.edt_user_name)
    EditText userName;

    @BindView(R.id.edt_user_age)
    EditText userAge;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public static void startIntent(Context context) {
        Intent newUserActivity = new Intent(context, NewUserActivity.class);
        context.startActivity(newUserActivity);
    }

    @OnClick(R.id.btn_add_user)
    public void onAddUserBtnClicked() {
        String name = userName.getText().toString();
        String ageString = userAge.getText().toString();
        String path = "";
        User user;
        if (ageString.isEmpty()) {
            user = new User(name, path);
        } else {
            user = new User(name, Integer.parseInt(ageString), path);
        }
        userViewModel.addUser(user);
        SelectUserActivity.start(this);
    }
}
