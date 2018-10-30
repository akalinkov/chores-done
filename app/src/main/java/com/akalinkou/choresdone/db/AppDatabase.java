package com.akalinkou.choresdone.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.akalinkou.choresdone.db.dao.TaskDao;
import com.akalinkou.choresdone.db.dao.UserDao;
import com.akalinkou.choresdone.models.Task;
import com.akalinkou.choresdone.models.User;

@Database(entities = {User.class, Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "chores_database";

    public abstract UserDao userDao();

    public abstract TaskDao taskDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
