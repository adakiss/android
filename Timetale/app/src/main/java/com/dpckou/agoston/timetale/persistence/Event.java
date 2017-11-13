package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;
//TODO: add list of people, add description, add location.
/**
 * Created by kissa on 2017. 11. 09..
 */

@Entity(tableName = "event", indices = @Index(value = {"event_start", "event_end"}))
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "event_name")
    private String eventName;

    @ColumnInfo(name = "event_start")
    private long eventStart;

    @ColumnInfo(name = "event_end")
    private long eventEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventStart() {
        return eventStart;
    }

    public void setEventStart(long eventStart) {
        this.eventStart = eventStart;
    }

    public long getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(long eventEnd) {
        this.eventEnd = eventEnd;
    }
}
