package com.akalinkou.choresdone.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RewardsActivity extends AppCompatActivity {

    private static final String TAG = RewardsActivity.class.getSimpleName();
    private static final String REWARDS_KEY_ID = "rewards_key_id";
    private User user;

    @BindView(R.id.btn_add_reward)
    ImageButton addRewardButton;

    @BindView(R.id.rv_rewards)
    RecyclerView rewardsView;

    @BindView(R.id.btn_tasks)
    Button tasksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: setup Rewards view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        if (savedInstanceState == null) {
            parseExtras();
        }

        ButterKnife.bind(this);
    }

    private void parseExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        user = extras.getParcelable(REWARDS_KEY_ID);
    }

    public static void start(Context context, User user) {
        Log.d(TAG, "start: build and start intent");
        Intent rewardsActivity = new Intent(context, RewardsActivity.class);
        rewardsActivity.putExtra(REWARDS_KEY_ID, user);
        context.startActivity(rewardsActivity);
    }

    @OnClick(R.id.btn_tasks)
    public void onTasksBtnClick() {
        Log.d(TAG, "onTasksBtnClick: start TasksActivity");
        TasksActivity.start(this, user);
    }

}
