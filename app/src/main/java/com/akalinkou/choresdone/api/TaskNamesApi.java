package com.akalinkou.choresdone.api;

import android.util.Log;

import com.akalinkou.choresdone.helpers.Network;
import com.akalinkou.choresdone.models.TaskNamesPresets;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TaskNamesApi {

    private static final String TAG = TaskNamesApi.class.getSimpleName();
    private static final String TASK_NAME_URL = "https://api.myjson.com/bins/1geaqu";

    public static TaskNamesPresets getTaskPresets() {
        Log.d(TAG, "getTaskPresets: ");
        String json;
        try {
            json = Network.sendHttpRequest(buildApiUrl());
        } catch (MalformedURLException e) {
            Log.e(TAG, "getTaskPresets: Failed to build URL from string=" + TASK_NAME_URL, e);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e(TAG, "getTaskPresets: Failed to send http request", e);
            e.printStackTrace();
            return null;
        }
        Log.d(TAG, "getTaskPresets: response: " + json);
        return new Gson().fromJson(json, TaskNamesPresets.class);
    }

    private static URL buildApiUrl() throws MalformedURLException {
        Log.d(TAG, "buildApiUrl: " + TASK_NAME_URL);
        return new URL(TASK_NAME_URL);
    }
}
