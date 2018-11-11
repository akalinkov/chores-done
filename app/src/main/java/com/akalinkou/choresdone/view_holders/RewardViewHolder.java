package com.akalinkou.choresdone.view_holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.models.Reward;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RewardViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = RewardViewHolder.class.getSimpleName();

    @BindView(R.id.reward_title)
    TextView title;

    @BindView(R.id.reward_cost)
    TextView cost;

    private RewardClickListener listener;

    public RewardViewHolder(@NonNull View itemView, RewardClickListener listener) {
        super(itemView);
        Log.d(TAG, "RewardViewHolder: invoked");
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Reward reward) {
        Log.d(TAG, "bind: invoked");
        title.setText(reward.getTitle());
        cost.setText(String.valueOf(reward.getCost()));
    }

    @OnClick
    public void onRewardClick() {
        Log.d(TAG, "onRewardClick: invoked");
        if (listener == null) {
            return;
        }
        listener.onRewardClick(getAdapterPosition());
    }

    public interface RewardClickListener {
        void onRewardClick(int position);
    }
}
