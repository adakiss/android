package com.dpckou.agoston.timetale;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dpckou.agoston.timetale.persistence.TimetaleDatabase;

/**
 * Created by kissa on 2017. 11. 10..
 */

public class TimetaleApplication extends Application {
    public TimetaleApplication() {
        super();
    }

    private static TimetaleApplication instance;
    private static final String DB_NAME = "TimetaleDB";

    private TimetaleDatabase database;

    public static TimetaleApplication get() {
        return instance;
    }

    public TimetaleDatabase getDB() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), TimetaleDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        instance=this;
    }
}
