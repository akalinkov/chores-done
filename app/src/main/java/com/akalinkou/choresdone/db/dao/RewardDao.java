package com.akalinkou.choresdone.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.akalinkou.choresdone.models.Reward;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RewardDao {

    @Query("SELECT * FROM rewards")
    LiveData<List<Reward>> getRewards();

    @Insert
    void addReward(Reward reward);
}
