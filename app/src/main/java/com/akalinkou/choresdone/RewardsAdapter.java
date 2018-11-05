package com.akalinkou.choresdone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akalinkou.choresdone.models.Reward;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardViewHolder> {

    private static final String TAG = RewardsAdapter.class.getSimpleName();

    private List<Reward> rewards;

    public RewardsAdapter(List<Reward> rewards) {
        this.rewards = rewards;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d(TAG, "onCreateViewHolder: inflate a task view");
        View rewardItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(rewardItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int i) {
        Log.d(TAG, "onBindViewHolder:");
        holder.bind(rewards.get(i));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        if (rewards == null) {
            return 0;
        }
        return rewards.size();
    }

    public void setData(@NonNull List<Reward> rewards) {
        Log.d(TAG, "setData: update list of rewards in the view. #=" + rewards.size());
        this.rewards = rewards;
        notifyDataSetChanged();
    }
}
