package com.akalinkou.choresdone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.models.Reward;

import butterknife.BindView;
import butterknife.ButterKnife;

class RewardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reward_title)
    TextView title;

    @BindView(R.id.reward_cost)
    TextView cost;

    public RewardViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Reward reward) {
        title.setText(reward.getTitle());
        cost.setText(String.valueOf(reward.getCost()));
    }
}
