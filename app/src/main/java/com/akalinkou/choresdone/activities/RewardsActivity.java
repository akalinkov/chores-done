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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.adapters.RewardsAdapter;
import com.akalinkou.choresdone.db.viewmodels.RewardViewModel;
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.helpers.SharedPrefs;
import com.akalinkou.choresdone.models.Reward;
import com.akalinkou.choresdone.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RewardsActivity extends AppCompatActivity
        implements RewardsAdapter.RewardActionListener {

    private static final String TAG = RewardsActivity.class.getSimpleName();
    private User user;
    private int userId;
    private FirebaseAnalytics analytics;

    @BindView(R.id.btn_add_reward)
    ImageButton addRewardButton;

    @BindView(R.id.rv_rewards)
    RecyclerView rewardsView;

    @BindView(R.id.btn_tasks)
    Button tasksButton;

    @BindView(R.id.txt_user_balance)
    TextView balance;

    @BindView(R.id.label_no_rewards)
    TextView noRewardsLabel;

    @BindView(R.id.profile_image)
    CircleImageView avatar;

    private RewardsAdapter rewardsAdapter;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: setup Rewards view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        analytics = FirebaseAnalytics.getInstance(this);

        if (savedInstanceState == null) {
            parseExtras();
        } else {
            restoreInstanceState(savedInstanceState);
        }

        ButterKnife.bind(this);

        userId = SharedPrefs.getUserId(this);
        setupRewardsList();
        registerUserObserver();
        registerRewardsObserver();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putParcelable(User.EXTRAS_KEY, user);
    }

    private void setupRewardsList() {
        Log.d(TAG, "setupRewardsList: ");
        rewardsView.setLayoutManager(new LinearLayoutManager(this));
        rewardsAdapter = new RewardsAdapter(new ArrayList<Reward>(), this);
        rewardsView.setAdapter(rewardsAdapter);
    }

    private void registerUserObserver() {
        Log.d(TAG, "registerUserObserver: ");
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null) {
                    Log.d(TAG, "onChanged: user returned from DB is null");
                    return;
                }
                RewardsActivity.this.user = user;
                balance.setText(String.valueOf(RewardsActivity.this.user.getBalance()));
                avatar.setImageResource(RewardsActivity.this.user.getAvatarResourceId());
            }
        });
    }

    private void registerRewardsObserver() {
        Log.d(TAG, "registerRewardsObserver: ");
        RewardViewModel rewardViewModel = ViewModelProviders.of(this).get(RewardViewModel.class);
        rewardViewModel.getRewards().observe(this, new Observer<List<Reward>>() {
            @Override
            public void onChanged(@Nullable List<Reward> rewards) {
                boolean isEmptyRewardsList = rewards == null || rewards.size() == 0;
                if (isEmptyRewardsList) {
                    setViewVisibility(noRewardsLabel, true);
                    setViewVisibility(rewardsView, false);
                } else {
                    rewardsAdapter.setData(rewards);
                    setViewVisibility(noRewardsLabel, false);
                    setViewVisibility(rewardsView, true);
                }
            }
        });
    }

    private void restoreInstanceState(Bundle inState) {
        Log.d(TAG, "restoreInstanceState: ");
        user = inState.getParcelable(User.EXTRAS_KEY);
    }

    private void parseExtras() {
        Log.d(TAG, "parseExtras: ");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        User tempUser = extras.getParcelable(User.EXTRAS_KEY);
        if (tempUser == null) {
            return;
        }
        user = tempUser;
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

    @OnClick(R.id.btn_add_reward)
    public void onAddRewardBtnClick() {
        Log.d(TAG, "onAddRewardBtnClick: start NewRewardActivity");
        NewRewardActivity.start(this, user);
    }

    private void setViewVisibility(View view, boolean isVisible) {
        Log.d(TAG, "setViewVisibility: ");
        int visibility = View.GONE;
        if (isVisible) {
            visibility = View.VISIBLE;
        }
        view.setVisibility(visibility);
    }

    @Override
    public void claimReward(int cost) {
        Log.d(TAG, "claimReward: cost=" + cost);
        if (user.getBalance() >= cost) {
            Log.d(TAG, "claimReward: old balance=" + user.getBalance());
            user.setBalance(user.getBalance() - cost);
            Log.d(TAG, "claimReward: new balance=" + user.getBalance());
            userViewModel.updateBalance(user);
        }
    }
}
