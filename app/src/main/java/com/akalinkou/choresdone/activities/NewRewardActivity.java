package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.db.viewmodels.RewardViewModel;
import com.akalinkou.choresdone.models.Reward;
import com.akalinkou.choresdone.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewRewardActivity extends AppCompatActivity {

    private static final String TAG = NewRewardActivity.class.getSimpleName();

    @BindView(R.id.edt_reward_title)
    EditText titleView;

    @BindView(R.id.edt_reward_cost)
    EditText costView;

    private RewardViewModel rewardViewModel;

    private User user;
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reward);
        analytics = FirebaseAnalytics.getInstance(this);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            parseIntentExtras();
        } else {
            restoreInstanceState(savedInstanceState);
        }

        registerRewardsObserver();
    }

    private void restoreInstanceState(Bundle inState) {
        Log.d(TAG, "restoreInstanceState: restore user value");
        user = inState.getParcelable(User.EXTRAS_KEY);
    }

    private void parseIntentExtras() {
        Log.d(TAG, "parseIntentExtras: ");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.d(TAG, "parseIntentExtras: extras are empty");
            return;
        }
        user = extras.getParcelable(User.EXTRAS_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putParcelable(User.EXTRAS_KEY, user);
        super.onSaveInstanceState(outState);
    }

    private void registerRewardsObserver() {
        Log.d(TAG, "registerRewardsObserver: ");
        rewardViewModel = ViewModelProviders.of(this).get(RewardViewModel.class);
    }

    public static void start(Context context, User user) {
        Log.d(TAG, "start: start NewRewardActivity");
        Intent newRewardActivity = new Intent(context, NewRewardActivity.class);
        newRewardActivity.putExtra(User.EXTRAS_KEY, user);
        context.startActivity(newRewardActivity);
    }

    @OnClick(R.id.btn_save_reward)
    public void onSaveRewardBtnClick() {
        if (isRequiredFieldsEmpty()) {
            Toast.makeText(this, R.string.required_reward_title, Toast.LENGTH_LONG).show();
            return;
        }
        String title = titleView.getText().toString();
        Log.d(TAG, "onSaveRewardBtnClick: title=" + title);
        int cost = Integer.valueOf(costView.getText().toString());
        Log.d(TAG, "onSaveRewardBtnClick: cost=" + cost);
        Reward newReward = new Reward(title, cost);
        rewardViewModel.addReward(newReward);
        RewardsActivity.start(this, user);
    }

    private boolean isRequiredFieldsEmpty() {
        return titleView.getText().length() == 0;
    }
}
