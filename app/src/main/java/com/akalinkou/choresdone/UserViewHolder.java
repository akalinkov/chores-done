package com.akalinkou.choresdone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.models.UserButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

class UserViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = UserViewHolder.class.getSimpleName();

    private ProfileClickListener clickListener;
    @BindView(R.id.txt_user_name) TextView userName;

    @BindView(R.id.profile_image) CircleImageView profileImage;

    UserViewHolder(@NonNull View itemView, ProfileClickListener clickListener) {
        super(itemView);
        Log.d(TAG, "UserViewHolder: new ViewHolder");
        this.clickListener = clickListener;
        ButterKnife.bind(this, itemView);
    }

    void bind(@NonNull UserButton button) {
        Log.d(TAG, "bind: " + button.getUserName());
        userName.setText(button.getUserName());
        // TODO: Change to set image for profile. Should use helper class providing Bitmap or default image based on the url
//        profileImage.setImageBitmap(user.getImagePath());
    }


    @OnClick
    void onClick(View view) {
        clickListener.onProfileClick(getAdapterPosition());
    }

    interface ProfileClickListener {
        void onProfileClick(int position);
    }
}
