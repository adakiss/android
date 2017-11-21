package com.dpckou.agoston.timetale.weekday;

/**
 * Created by kissa on 2017. 11. 04..
 */

public class Hour {

    private Integer hour;
    private String event;

    public Hour(Integer hour) {
        if (hour < 0) {
            throw new IllegalArgumentException("Hour of the day cannot be negative number");
        }
        this.hour = hour;
    }

    public Integer getHour() {

        return hour;
    }

    public String getEvent() {

        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Boolean isEventPlanned() {
        return event != null;
    }
}
