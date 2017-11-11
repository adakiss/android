package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by kissa on 2017. 11. 09..
 */

@Database(entities = Event.class, version = 1)
public abstract class TimetaleDatabase extends RoomDatabase {
    protected abstract EventDao getEventDao();

    private static EventDao daoInstance;

    public EventDao getDaoInstance() {
        if (daoInstance == null) {
            daoInstance = getEventDao();
        }
        return daoInstance;
    }
}
