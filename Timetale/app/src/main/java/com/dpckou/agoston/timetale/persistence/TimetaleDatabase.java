package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by kissa on 2017. 11. 09..
 */

@Database(entities = Event.class, version = 1)
public abstract class TimetaleDatabase extends RoomDatabase {
    public abstract EventDao getEventDao();
}
