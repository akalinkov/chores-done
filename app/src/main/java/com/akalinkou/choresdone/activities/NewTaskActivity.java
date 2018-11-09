package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.api.TaskNamesApi;
import com.akalinkou.choresdone.db.viewmodels.TaskViewModel;
import com.akalinkou.choresdone.models.Task;
import com.akalinkou.choresdone.models.TaskNamesPresets;
import com.akalinkou.choresdone.models.User;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskActivity extends AppCompatActivity {

    private static final String TAG = NewTaskActivity.class.getSimpleName();

    @BindView(R.id.edt_task_name)
    AppCompatAutoCompleteTextView taskName;

    @BindView(R.id.edt_task_value)
    EditText taskValue;

    private TaskViewModel taskViewModel;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        if (savedInstanceState == null) {
            parseIntentExtras();
        } else {
            restoreInstanceState(savedInstanceState);
        }

        ButterKnife.bind(this);
        setupAutoSuggestions();
        new DownloadTaskNamePresets().execute();
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
    }

    private void setupAutoSuggestions() {

        ArrayAdapter<String> presetsAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList("Hello", "World"));

        presetsAdapter.setNotifyOnChange(true);
        taskName.setThreshold(2);
        taskName.setAdapter(presetsAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(User.EXTRAS_KEY, user);
        super.onSaveInstanceState(outState);
    }

    private void parseIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        user = extras.getParcelable(User.EXTRAS_KEY);
    }

    private void restoreInstanceState(Bundle inState) {
        user = inState.getParcelable(User.EXTRAS_KEY);
    }

    public static void start(Context context, User user) {
        Intent newTaskActivity = new Intent(context, NewTaskActivity.class);
        newTaskActivity.putExtra(User.EXTRAS_KEY, user);
        context.startActivity(newTaskActivity);
    }

    @OnClick(R.id.btn_save_task)
    public void onSaveTaskBtnClicked() {
        String name = taskName.getText().toString();
        Log.d(TAG, "onSaveTaskBtnClicked: taskName=" + name);
        String valueString = taskValue.getText().toString();
        int value = (valueString.isEmpty()) ? 0 : Integer.parseInt(valueString);
        Log.d(TAG, "onSaveTaskBtnClicked: taskValue=" + value);
        Task task = new Task(name, value, user.getId(), false);
        taskViewModel.addTask(task);
        TasksActivity.start(this, user);
    }

    private class DownloadTaskNamePresets extends AsyncTask<Void, Void, TaskNamesPresets> {

        @Override
        protected TaskNamesPresets doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: start downloading TaskName presets");
            return TaskNamesApi.getTaskPresets();
        }

        @Override
        protected void onPostExecute(TaskNamesPresets taskNamesPresets) {
            if (taskNamesPresets == null || taskNamesPresets.task_names == null) {
                Log.d(TAG, "onPostExecute: null returned");
                return;
            }
            Log.d(TAG, "onPostExecute: presets size = " + taskNamesPresets.task_names.size());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewTaskActivity.this,
                    R.layout.support_simple_spinner_dropdown_item,
                    taskNamesPresets.task_names);
            taskName.setAdapter(adapter);
        }
    }
}
