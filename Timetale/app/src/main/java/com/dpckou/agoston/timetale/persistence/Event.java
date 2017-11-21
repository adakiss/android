package com.dpckou.agoston.timetale.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.dpckou.agoston.timetale.EventCards.SelectedEventActivity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @ColumnInfo(name = "event_location")
    private String eventLocation;

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

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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
        StringBuilder friendString = new StringBuilder();
        for(String s : friends) {
            friendString.append(s);
            if(friends.indexOf(s) != friends.size()-1 ) {
                friendString.append(SelectedEventActivity.NAMES_SEPARATOR);
            }
        }
        this.eventFriends = friendString.toString();
    }

    public ArrayList<String> getFriends() {
        if(this.eventFriends != null) {
            return new ArrayList<String>(Arrays.asList(eventFriends.split(",")));
        } else {
            return new ArrayList<String>();
        }
    }

    public void setEventEnd(long eventEnd) {
        this.eventEnd = eventEnd;
    }
}
