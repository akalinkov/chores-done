package com.akalinkou.choresdone.db.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.akalinkou.choresdone.models.Reward;
import com.akalinkou.choresdone.repositories.RewardsRepository;

import java.util.List;

public class RewardViewModel extends AndroidViewModel {

    private RewardsRepository repo;

    public RewardViewModel(@NonNull Application application) {
        super(application);
        repo = new RewardsRepository(application);
    }

    public LiveData<List<Reward>> getRewards() {
        return repo.getRewards();
    }

    public void addReward(Reward reward) {
        repo.addReward(reward);
    }
}
