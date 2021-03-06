package com.akalinkou.choresdone.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.view_holders.RewardViewHolder;
import com.akalinkou.choresdone.models.Reward;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardViewHolder>
        implements RewardViewHolder.RewardClickListener {

    private static final String TAG = RewardsAdapter.class.getSimpleName();

    private List<Reward> rewards;
    private RewardActionListener listener;

    public RewardsAdapter(List<Reward> rewards, RewardActionListener listener) {
        this.rewards = rewards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d(TAG, "onCreateViewHolder: inflate a task view");
        View rewardItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(rewardItemView, this);
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

    @Override
    public void onRewardClick(int position) {
        Log.d(TAG, "claimReward: position #" + position);
        if (listener == null) return;
        listener.claimReward(rewards.get(position).getCost());
    }

    public interface RewardActionListener {
        void claimReward(int cost);
    }
}
