package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Event.class, version = 3)
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
