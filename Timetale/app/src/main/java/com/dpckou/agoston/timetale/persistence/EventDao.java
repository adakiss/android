package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Date;

/**
 * Created by kissa on 2017. 11. 09..
 */

@Dao
public interface EventDao {

    @Query("SELECT * FROM event WHERE event_start <= :end AND event_end >= :start")
    public Event[] eventsOnDay(long start, long end);

    @Insert
    public void addNewEvent(Event event);

    @Update
    public void updateEvent(Event event);

    @Delete
    public void deleteEvent(Event event);

    @Query("DELETE FROM event")
    public void nukeEventTable();
}
