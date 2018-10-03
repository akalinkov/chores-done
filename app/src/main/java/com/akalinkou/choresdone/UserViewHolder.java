package com.akalinkou.choresdone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = UserViewHolder.class.getSimpleName();

    @BindView(R.id.txt_user_name) TextView userName;

    @BindView(R.id.profile_image) CircleImageView profileImage;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.d(TAG, "UserViewHolder: new ViewHolder");
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull User user) {
        Log.d(TAG, "bind: " + user.getUserName());
        userName.setText(user.getUserName());
        // TODO: Change to set image for profile. Should use helper class providing Bitmap or default image based on the url
//        profileImage.setImageBitmap(user.getProfileImagePath());
    }
}
