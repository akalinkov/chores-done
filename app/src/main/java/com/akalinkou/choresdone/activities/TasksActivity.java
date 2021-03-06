package com.akalinkou.choresdone.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.adapters.TasksAdapter;
import com.akalinkou.choresdone.db.viewmodels.TaskViewModel;
import com.akalinkou.choresdone.db.viewmodels.UserViewModel;
import com.akalinkou.choresdone.helpers.SharedPrefs;
import com.akalinkou.choresdone.models.Task;
import com.akalinkou.choresdone.models.User;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TasksActivity extends AppCompatActivity
        implements TasksAdapter.TaskActionListener {

    private static final String TAG = TasksActivity.class.getSimpleName();

    private TasksAdapter tasksAdapter;

    @BindView(R.id.rv_tasks)
    RecyclerView tasksRecyclerView;

    @BindView(R.id.label_no_tasks)
    TextView noTasksLabel;

    @BindView(R.id.txt_user_balance)
    TextView balance;

    @BindView(R.id.profile_image)
    CircleImageView avatar;

    private TaskViewModel taskViewModel;
    private UserViewModel userViewModel;
    private User user;
    private int userId;
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        analytics = FirebaseAnalytics.getInstance(this);


        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            parseIntentExtras();
        } else {
            restoreInstanceState(savedInstanceState);
        }

        userId = SharedPrefs.getUserId(this);
        setupTasksList();
        registerTasksObserver();
        registerUserObserver();
    }

    private void restoreInstanceState(Bundle inState) {
        user = inState.getParcelable(User.EXTRAS_KEY);
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

    private void setupTasksList() {
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TasksAdapter(new ArrayList<Task>(), this);
        tasksRecyclerView.setAdapter(tasksAdapter);
    }

    private void registerTasksObserver() {
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getTasks(userId).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                boolean isEmptyTasksList = tasks == null || tasks.size() == 0;
                if (isEmptyTasksList) {
                    setViewVisibility(noTasksLabel, true);
                    setViewVisibility(tasksRecyclerView, false);
                } else {
                    tasksAdapter.setTasks(tasks);
                    setViewVisibility(noTasksLabel, false);
                    setViewVisibility(tasksRecyclerView, true);
                }
            }
        });
    }

    private void registerUserObserver() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser(userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null) {
                    return;
                }
                TasksActivity.this.user = user;
                balance.setText(String.valueOf(TasksActivity.this.user.getBalance()));
                avatar.setImageResource(TasksActivity.this.user.getAvatarResourceId());
            }
        });
    }

    public static void start(Context context, User user) {
        Log.d(TAG, "start: start TasksActivity intent");
        Intent tasksActivity = new Intent(context, TasksActivity.class);
        tasksActivity.putExtra(User.EXTRAS_KEY, user);
        context.startActivity(tasksActivity);
    }

    @OnClick(R.id.btn_rewards)
    public void navigateToRewardsBtnClicked() {
        Log.d(TAG, "navigateToRewardsBtnClicked: 'Rewards' button clicked");
        RewardsActivity.start(this, user);
    }

    @OnClick(R.id.btn_add_task)
    public void addNewTaskBtnClicked() {
        Log.d(TAG, "addNewTaskBtnClicked: 'New Task' button clicked");
        NewTaskActivity.start(this, user);
    }

    @Override
    public void toggleTaskStatus(int taskId, boolean currentCompletionStatus, int value) {
        boolean success = true;
        if (currentCompletionStatus) {
            success = withdrawAmount(value);
        } else {
            depositToAccount(value);
        }
        if (success) taskViewModel.toggleTaskStatus(taskId, currentCompletionStatus);
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

    private boolean withdrawAmount(int value) {
        if (user.getBalance() < value) return false;
        user.setBalance(user.getBalance() - value);
        userViewModel.updateBalance(user);
        return true;
    }
}
