package com.akalinkou.choresdone.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.db.AppDatabase;
import com.akalinkou.choresdone.models.User;

import java.util.List;

public class GridWidgetService extends RemoteViewsService {

    private static final String TAG = GridWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: invoked");
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = GridRemoteViewsFactory.class.getSimpleName();
    private Context context;
    private List<User> users;

    public GridRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: invoked");
        users = AppDatabase
                .getDatabase(context)
                .userDao()
                .getAllUsersList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (users == null) {
            Log.d(TAG, "getCount: 0");
            return 0;
        }
        Log.d(TAG, "getCount: =" + users.size());
        return users.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt: position#" + position);
        if (users == null || getCount() == 0) {
            return null;
        }
        User user = users.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.user_widget);
        bind(views, user);
        views.setOnClickFillInIntent(R.id.widget_item_layout,
                buildOpenTasksIntent(user));
        return views;
    }

    private Intent buildOpenTasksIntent(User user) {
        Intent intent = new Intent();
        intent.putExtra(User.EXTRAS_KEY, user.getId());
        return intent;
    }

    private void bind(RemoteViews views, User user) {
        String balance = String.valueOf(user.getBalance());
        views.setTextViewText(R.id.widget_balance, balance);
        views.setTextViewText(R.id.widget_user_name, user.getName());
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}


