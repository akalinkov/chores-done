package com.akalinkou.choresdone.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.akalinkou.choresdone.R;
import com.akalinkou.choresdone.activities.SelectUserActivity;
import com.akalinkou.choresdone.activities.TasksActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BalanceWidget extends AppWidgetProvider {
    private static final String TAG = BalanceWidget.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d(TAG, "updateAppWidget: ");
        RemoteViews views = getGridRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getGridRemoteView(Context context) {
        Log.d(TAG, "getGridRemoteView: ");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.balance_widget);

        Intent gridFactory = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_gv_users, gridFactory);

        Intent intent = new Intent(context, TasksActivity.class);
        PendingIntent openApplication = PendingIntent
                .getActivity(context,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_gv_users, openApplication);

        views.setEmptyView(R.id.widget_gv_users, R.id.widget_label_no_users);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

