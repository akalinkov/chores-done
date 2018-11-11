package com.akalinkou.choresdone.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.akalinkou.choresdone.helpers.AppExecutors;
import com.akalinkou.choresdone.db.AppDatabase;
import com.akalinkou.choresdone.db.dao.RewardDao;
import com.akalinkou.choresdone.models.Reward;

import java.util.List;

public class RewardsRepository {

    private RewardDao rewardsDao;
    private LiveData<List<Reward>> rewards;

    public RewardsRepository(Application application) {
        rewardsDao = AppDatabase.getDatabase(application).rewardDao();
    }

    public LiveData<List<Reward>> getRewards() {
        if (rewards == null) {
            rewards = rewardsDao.getRewards();
        }
        return rewards;
    }

    public void addReward(final Reward reward) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                rewardsDao.addReward(reward);
            }
        });
    }
}
