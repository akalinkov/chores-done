package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.db.viewmodels.TaskViewModel;
import com.akalinkou.choresdone.models.Task;
import com.akalinkou.choresdone.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskActivity extends AppCompatActivity {

    private static final String TAG = NewTaskActivity.class.getSimpleName();

    @BindView(R.id.edt_task_name)
    EditText taskName;

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
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TasksActivity.USER_EXTRA_KEY, user);
        super.onSaveInstanceState(outState);
    }

    private void parseIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        user = extras.getParcelable(TasksActivity.USER_EXTRA_KEY);
    }

    private void restoreInstanceState(Bundle inState) {
        user = inState.getParcelable(TasksActivity.USER_EXTRA_KEY);
    }

    public static void start(Context context, User user) {
        Intent newTaskActivity = new Intent(context, NewTaskActivity.class);
        newTaskActivity.putExtra(TasksActivity.USER_EXTRA_KEY, user);
        context.startActivity(newTaskActivity);
    }

    @OnClick(R.id.btn_add_task)
    public void onAddTaskBtnClicked() {
        String name = taskName.getText().toString();
        Log.d(TAG, "onAddTaskBtnClicked: taskName=" + name);
        String valueString = taskValue.getText().toString();
        int value = (valueString.isEmpty()) ? 0 : Integer.parseInt(valueString);
        Log.d(TAG, "onAddTaskBtnClicked: taskValue=" + value);
        Task task = new Task(name, value, user.getId(), false);
        taskViewModel.addTask(task);
        TasksActivity.start(this, user);
    }
}
