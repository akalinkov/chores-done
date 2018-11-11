package com.akalinkou.choresdone.view_holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.models.UserButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = UserViewHolder.class.getSimpleName();

    private ProfileClickListener clickListener;
    @BindView(R.id.txt_user_name)
    TextView userName;

    @BindView(R.id.profile_image)
    CircleImageView avatar;

    public UserViewHolder(@NonNull View itemView, ProfileClickListener clickListener) {
        super(itemView);
        Log.d(TAG, "UserViewHolder: new ViewHolder");
        this.clickListener = clickListener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull UserButton button) {
        Log.d(TAG, "bind: " + button.getUserName());
        userName.setText(button.getUserName());
        int avatarId = button.getAvatarResourceId();
        if (avatarId == 0) {
            avatarId = R.drawable.ic_default_user;
        }
        avatar.setImageResource(avatarId);
    }


    @OnClick
    public void onClick(View view) {
        if (clickListener == null) return;
        clickListener.onProfileClick(getAdapterPosition());
    }

    public interface ProfileClickListener {
        void onProfileClick(int position);
    }
}
