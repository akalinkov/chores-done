package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewUserActivity extends AppCompatActivity {

    @BindView(R.id.edt_user_name)
    EditText userName;

    @BindView(R.id.civ_avatar)
    CircleImageView avatar;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        ButterKnife.bind(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        avatar.setImageResource(R.drawable.ic_boy);
        avatar.setTag(R.drawable.ic_boy);
    }

    public static void startIntent(Context context) {
        Intent newUserActivity = new Intent(context, NewUserActivity.class);
        context.startActivity(newUserActivity);
    }

    @OnClick(R.id.btn_save_user)
    public void onSaveUserBtnClicked() {
        if (isRequiredFieldsEmpty()) {
            Toast.makeText(this, R.string.required_user_name, Toast.LENGTH_LONG).show();
            return;
        }
        String name = userName.getText().toString();
        String path = "";
        int avatarResourceId = 0;
        if (avatar.getTag() != null) {
            avatarResourceId =  (int) avatar.getTag();
        }
        User user = new User(name, 0, avatarResourceId);
        userViewModel.addUser(user);
        SelectUserActivity.start(this);
    }

    private boolean isRequiredFieldsEmpty() {
        return userName.getText().length() == 0;
    }
}
