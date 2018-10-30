package com.akalinkou.choresdone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akalinkou.choresdone.models.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder>
        implements TaskViewHolder.TaskClickListener {

    private static final String TAG = TasksAdapter.class.getSimpleName();

    private TaskActionListener actionListener;
    private List<Task> tasks;

    public TasksAdapter(@NonNull List<Task> tasks, @NonNull TaskActionListener actionListener) {
        this.tasks = tasks;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d(TAG, "onCreateViewHolder: inflate a task view");
        View taskView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(taskView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: task #" + position);
        holder.bind(holder.itemView.getContext(), tasks.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }

    public void setTasks(@NonNull List<Task> tasks) {
        Log.d(TAG, "setTasks: add tasks. size=" + tasks.size());
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public void onTaskDelete(int position) {
        actionListener.deleteTask(tasks.get(position));
    }

    @Override
    public void onTaskClick(int position) {
        Log.d(TAG, "onTaskClick: task #" + position);
        actionListener.toggleTaskStatus(tasks.get(position).getId(), tasks.get(position).isComplete());
    }

    public interface TaskActionListener {
        void toggleTaskStatus(int taskId, boolean currentCompletionStatus);

        void deleteTask(Task task);
    }
}
