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
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.models.Task;
import com.akalinkou.choresdone.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TasksActivity extends AppCompatActivity
        implements TasksAdapter.TaskActionListener {

    private static final String TAG = TasksActivity.class.getSimpleName();
    public static final String USER_EXTRA_KEY = "user_extra_key";

    private TasksAdapter tasksAdapter;

    @BindView(R.id.rv_tasks)
    RecyclerView tasksRecyclerView;

    @BindView(R.id.label_no_tasks)
    TextView noTasksLabel;

    @BindView(R.id.txt_user_balance)
    TextView balance;

    private TaskViewModel taskViewModel;
    private UserViewModel userViewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            parseIntentExtras();
        } else {
            restoreInstanceState(savedInstanceState);
        }

        setupTasksList();
        registerTasksObserver();
        registerUserObserver();
    }

    private void restoreInstanceState(Bundle inState) {
        user = inState.getParcelable(USER_EXTRA_KEY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(USER_EXTRA_KEY, user);
        super.onSaveInstanceState(outState);
    }

    private void parseIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        user = extras.getParcelable(USER_EXTRA_KEY);
    }

    private void setupTasksList() {
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TasksAdapter(new ArrayList<Task>(), this);
        tasksRecyclerView.setAdapter(tasksAdapter);
    }

    private void registerTasksObserver() {
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTasks(user.getId()).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if (tasks == null || tasks.size() == 0) {
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

    private void registerUserObserver() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(user.getId()).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null) {
                    return;
                }
                TasksActivity.this.user = user;
                balance.setText(String.valueOf(TasksActivity.this.user.getBalance()));
            }
        });
    }

    public static void start(Context context, User user) {
        Log.d(TAG, "start: start TasksActivity intent");
        Intent tasksActivity = new Intent(context, TasksActivity.class);
        tasksActivity.putExtra(USER_EXTRA_KEY, user);
        context.startActivity(tasksActivity);
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
        NewTaskActivity.start(this, user);
    }

    @Override
    public void toggleTaskStatus(int id, boolean currentCompletionStatus, int value) {
        if (currentCompletionStatus) {
            withdrawAmount(value);
        } else {
            depositToAccount(value);
        }
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

    private void depositToAccount(int value) {
        user.setBalance(user.getBalance() + value);
        userViewModel.updateBalance(user);
    }

    private void withdrawAmount(int value) {
        user.setBalance(user.getBalance() - value);
        userViewModel.updateBalance(user);
    }
}
