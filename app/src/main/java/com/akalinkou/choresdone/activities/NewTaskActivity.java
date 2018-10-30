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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTaskActivity extends AppCompatActivity {

    private static final String TAG = NewTaskActivity.class.getSimpleName();

    @BindView(R.id.edt_task_name)
    EditText taskName;

    @BindView(R.id.edt_task_value)
    EditText taskValue;

    @BindView(R.id.edt_task_due_date)
    EditText taskDueDate;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        ButterKnife.bind(this);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
    }

    public static void start(Context context) {
        Intent newTaskActivity = new Intent(context, NewTaskActivity.class);
        context.startActivity(newTaskActivity);
    }

    @OnClick(R.id.btn_add_task)
    public void onAddTaskBtnClicked() {
        String name = taskName.getText().toString();
        Log.d(TAG, "onAddTaskBtnClicked: taskName=" + name);
        String valueString = taskValue.getText().toString();
        int value = (valueString.isEmpty()) ? 0 : Integer.parseInt(valueString);
        Log.d(TAG, "onAddTaskBtnClicked: taskValue=" + value);
        String dueDate = taskDueDate.getText().toString();
        Log.d(TAG, "onAddTaskBtnClicked: taskDueDate=" + dueDate);
        Task task = new Task(name, value, dueDate, false);
        taskViewModel.addTask(task);
        TasksActivity.start(this);
    }
}
