package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.RewardsAdapter;
import com.akalinkou.choresdone.db.viewmodels.RewardViewModel;
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.models.Reward;
import com.akalinkou.choresdone.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RewardsActivity extends AppCompatActivity {

    private static final String TAG = RewardsActivity.class.getSimpleName();
    private User user;
    private List<Reward> rewards;

    @BindView(R.id.btn_add_reward)
    ImageButton addRewardButton;

    @BindView(R.id.rv_rewards)
    RecyclerView rewardsView;

    @BindView(R.id.btn_tasks)
    Button tasksButton;

    @BindView(R.id.txt_user_balance)
    TextView balance;

    private UserViewModel userViewModel;
    private RewardViewModel rewardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: setup Rewards view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        if (savedInstanceState == null) {
            parseExtras();
        } else {
            restoreInstanceState(savedInstanceState);
        }

        ButterKnife.bind(this);

        setupRewardsList();
        registerUserObserver();
        registerRewardsObserver();
    }

    private void setupRewardsList() {
        rewardsView.setLayoutManager(new LinearLayoutManager(this));
        rewardsView.setAdapter(new RewardsAdapter(new ArrayList<Reward>()));
    }

    private void registerUserObserver() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(user.getId()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null) {
                    Log.d(TAG, "onChanged: user returned from DB is null");
                    return;
                }
                RewardsActivity.this.user = user;
                balance.setText(String.valueOf(RewardsActivity.this.user.getBalance()));
            }
        });
    }

    private void registerRewardsObserver() {
        rewardViewModel = ViewModelProviders.of(this).get(RewardViewModel.class);
        rewardViewModel.getRewards().observe(this, new Observer<List<Reward>>() {
            @Override
            public void onChanged(@Nullable List<Reward> rewards) {
                RewardsActivity.this.rewards = rewards;
            }
        });
    }

    private void restoreInstanceState(Bundle inState) {
        user = inState.getParcelable(User.EXTRAS_KEY);
    }

    private void parseExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        user = extras.getParcelable(User.EXTRAS_KEY);
    }

    public static void start(Context context, User user) {
        Log.d(TAG, "start: build and start intent");
        Intent rewardsActivity = new Intent(context, RewardsActivity.class);
        rewardsActivity.putExtra(User.EXTRAS_KEY, user);
        context.startActivity(rewardsActivity);
    }

    @OnClick(R.id.btn_tasks)
    public void onTasksBtnClick() {
        Log.d(TAG, "onTasksBtnClick: start TasksActivity");
        TasksActivity.start(this, user);
    }

}
