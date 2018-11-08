package com.akalinkou.choresdone.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.akalinkou.choresdone.models.User;

public class SharedPrefs {

    public static int getUserId(Context context) {
        return prefs(context).getInt(User.USER_ID_KEY, 0);
    }

    public static void setUserId(Context context, int userId) {
        prefs(context).edit().putInt(User.USER_ID_KEY, userId).apply();
    }

    private static SharedPreferences prefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
