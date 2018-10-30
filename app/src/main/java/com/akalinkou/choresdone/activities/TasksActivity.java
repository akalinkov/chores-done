package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.TasksAdapter;
import com.akalinkou.choresdone.db.viewmodels.TaskViewModel;
import com.akalinkou.choresdone.models.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity
        implements TasksAdapter.TaskActionListener {

    private static final String TAG = TasksActivity.class.getSimpleName();

    private TasksAdapter tasksAdapter;

    @BindView(R.id.rv_tasks)
    RecyclerView tasksRecyclerView;

    @BindView(R.id.label_no_tasks)
    TextView noTasksLabel;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        setupTasksList();
    }

    private void setupTasksList() {
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new TasksAdapter(new ArrayList<Task>(), this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if (tasks == null || tasks.size() == 0 ) {
                    setViewVisibility(noTasksLabel, true);
                    setViewVisibility(tasksRecyclerView, false);
                } else {
                    tasksAdapter.setTasks(tasks);
                    setViewVisibility(tasksRecyclerView, true);
                    setViewVisibility(noTasksLabel, false);
                }
            }
        });
    }

    public static void start(Context context) {
        Log.d(TAG, "start: start TasksActivity intent");
        Intent dailyChoresActivity = new Intent(context, TasksActivity.class);
        context.startActivity(dailyChoresActivity);
    }

    @OnClick(R.id.btn_rewards)
    public void navigateToRewardsBtnClicked() {
        Log.d(TAG, "navigateToRewardsBtnClicked: 'Rewards' button clicked");
        Snackbar.make(findViewById(R.id.all_tasks_layout),
                "Rewards button clicked",
                Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_add_task)
    public void addNewTaskBtnClicked() {
        Log.d(TAG, "addNewTaskBtnClicked: 'New Task' button clicked");
        NewTaskActivity.start(this);
    }

    @Override
    public void toggleTaskStatus(int id, boolean currentCompletionStatus) {
        taskViewModel.toggleTaskStatus(id, currentCompletionStatus);

    }

    @Override
    public void deleteTask(Task task) {
        taskViewModel.deleteTask(task);
    }

    private void setViewVisibility(View view, boolean isVisible) {
        int visibility = View.GONE;
        if (isVisible) {
            visibility = View.VISIBLE;
        }
        view.setVisibility(visibility);
    }
}
