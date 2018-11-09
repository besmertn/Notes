package com.example.bessmertnyi.notes;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {
    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
