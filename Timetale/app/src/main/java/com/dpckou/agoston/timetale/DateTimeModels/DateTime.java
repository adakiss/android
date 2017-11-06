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
    public DateTime(int year, int month, int day,
                    int hour, int minute){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
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

    private static String nullify(int i){
        String parsed = Integer.toString(i);
        if(parsed.length() > 1)
            return parsed;
        else {
            String s = '0' + parsed;
            return s;
        }
    }

    public static String formatDate(int year, int month, int day, char separator){
        //fuck stringbuilder.
        //TODO maybe don't fuck stringbuilder.
        if((int)separator == 0){
            //hopefully this checks for if it is null
            separator = '.';
        }
        return nullify(year) + separator + nullify(month) + separator + nullify(day);
    }

    public static String formatTime(int hour, int minute, char separator){
        if((int)separator == 0){
            separator = ':';
        }
        return nullify(hour) + separator + nullify(minute);
    }
}
