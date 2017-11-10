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


    //FIXME - this is not the correct query
    @Query("SELECT * FROM event WHERE event_start <= :start AND event_end >= :end")
    public Event[] eventsOnDay(long start, long end);

    @Insert
    public void addNewEvent(Event event);

    @Update
    public void updateEvent(Event event);

    @Delete
    public void deleteEvent(Event event);
}
