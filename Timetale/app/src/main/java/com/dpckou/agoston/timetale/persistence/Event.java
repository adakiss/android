package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
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

    @ColumnInfo(name = "event_loc_lat")
    private double eventLocationLat;

    @ColumnInfo(name = "event_loc_lon")
    private double getEventLocationLon;

    @ColumnInfo(name = "event_description")
    private String eventDescription;

    @ColumnInfo(name = "event_friends")
    private String eventFriends;

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

    public double getEventLocationLat() {
        return eventLocationLat;
    }

    public void setEventLocationLat(double eventLocationLat) {
        this.eventLocationLat = eventLocationLat;
    }

    public double getGetEventLocationLon() {
        return getEventLocationLon;
    }

    public void setGetEventLocationLon(double getEventLocationLon) {
        this.getEventLocationLon = getEventLocationLon;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventFriends() {
        return eventFriends;
    }

    public void setEventFriends(String eventFriends) {
        this.eventFriends = eventFriends;
    }

    public void setEventFriends(List<String> friends) {
        String friendString = "";
        for(String s : friends) {
            friendString += s;
            if(friends.indexOf(s) != friends.size()-1 ) {
                friendString += ",";
            }
        }
        this.eventFriends = friendString;
    }

    public List<String> getFriends() {
        return Arrays.asList(eventFriends.split(","));
    }

    public void setEventEnd(long eventEnd) {
        this.eventEnd = eventEnd;
    }
}
