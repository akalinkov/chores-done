package com.akalinkou.choresdone;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.akalinkou.choresdone.models.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskViewHolder extends RecyclerView.ViewHolder
        implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.task_title)
    TextView title;

    @BindView(R.id.task_value)
    TextView value;

    private TaskClickListener clickListener;

    public TaskViewHolder(@NonNull View itemView, TaskClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        ButterKnife.bind(this, itemView);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void bind(Context context, Task task) {
        title.setText(task.getTitle());
        value.setText(String.valueOf(task.getValue()));
        Drawable checkmark = getCheckMarkDrawable(context, task.isComplete());
        title.setCompoundDrawablesWithIntrinsicBounds(checkmark, null, null, null);
    }

    private Drawable getCheckMarkDrawable(Context context, boolean isComplete) {
        int drawable;
        if (isComplete) {
            drawable = R.drawable.ic_checkmark_green;
        } else {
            drawable = R.drawable.ic_checkmark_grey;
        }
        return context.getResources().getDrawable(drawable);
    }

    @OnClick
    public void onTaskClick() {
        if (clickListener != null) {
            clickListener.onTaskClick(getAdapterPosition());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.getMenuInflater().inflate(R.menu.task_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (clickListener != null) {
            clickListener.onTaskDelete(getAdapterPosition());
        }
        return false;
    }

    interface TaskClickListener {
        void onTaskDelete(int position);

        void onTaskClick(int position);
    }
}
