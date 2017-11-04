package com.dpckou.agoston.timetale.DateTimeModels;
import java.util.Calendar;

/**
 * Created by agoston on 2017.11.04..
 * Utterly basic structure to store the datas for FROM and TO in one place.
 */

public class DateTime {
    private int year;
    private int month;
    private int day;
    //the constructor sets them to "now"
    public DateTime() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
    }

    private int hour;
    private int minute;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
