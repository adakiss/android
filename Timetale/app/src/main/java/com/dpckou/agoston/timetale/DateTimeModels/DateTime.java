package com.dpckou.agoston.timetale.DateTimeModels;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by agoston on 2017.11.04..
 * Utterly basic structure to store the datas for FROM and TO in one place.
 */

public class DateTime implements Comparable<DateTime>{
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

    public DateTime(Date d){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.hour = cal.get(Calendar.HOUR);
        this.minute = cal.get(Calendar.MINUTE);
    }

    public DateTime(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        this.year=c.get(Calendar.YEAR);
        this.month=c.get(Calendar.MONTH)+1;
        this.day=c.get(Calendar.DAY_OF_MONTH);
        this.hour=c.get(Calendar.HOUR_OF_DAY);
        this.minute=c.get(Calendar.MINUTE);
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

    public int compareByDate(DateTime o){
        if(this.year == o.getYear()
                && this.month == o.getMonth()
                && this.day == o.getDay())
            return 0;
        //in any case this is less -> short circuiting should make do bc of the order.
        if(this.year < o.getYear()
                || this.month < o.getMonth()
                || this.day < o.getDay())
            return -1;
        //not equal, not less then greater.
        return 1;
    }

    public int compareByTime(DateTime o){
        if(this.hour == o.getHour() && this.minute == o.getMinute())
            return 0;
        //in any case this is less -> short circuiting should make do bc of the order.
        if(this.hour < o.getHour()
                || this.minute < o.getMinute())
            return -1;
        //not equal, not less then greater.
        return 1;
    }

    @Override
    public int compareTo(@NonNull DateTime dateTime) {
        //if all equals
        if(this.year == dateTime.getYear()
                && this.month == dateTime.getMonth()
                && this.day == dateTime.getDay()
                && this.hour == dateTime.getHour()
                && this.minute == dateTime.getMinute())
            return 0;
        //in any case this is less -> short circuiting should make do bc of the order.
        if(this.year < dateTime.getYear()
                || this.month < dateTime.getMonth()
                || this.day < dateTime.getDay()
                || this.hour < dateTime.getHour()
                || this.minute < dateTime.getMinute())
            return -1;
        //not equal, not less then greater.
        return 1;
    }

    public long generateLong(){
        /*//TODO has to be tested and verified.
        String myDate = nullify(hour) + "-" + nullify(minute) + "-" +
                nullify(day) + "-" + nullify(month) + "-" + nullify(year);
        SimpleDateFormat sf = new SimpleDateFormat("HH-mm-dd-MM-yyyy");
        Date d = new Date();
        try {
            d = sf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();*/
        Calendar c = Calendar.getInstance();

        c.set(year, month-1, day, hour, minute);

        return c.getTimeInMillis();

    }

    /**
     * String[]'s 0: date, 1:time.
     * @param input the input in milliseconds
     * @return a String[]
     */
    public static String[] parseLong(long input){
        //input is in milliseconds, no need to multiply further.
        Date date = new Date(input);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        DateTime dateTime = new DateTime(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
        String[] s = new String[2];

        //s[0] -> date
        //s[1] -> time, both are converted to string.

        s[0] = formatDate(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(),
                '.');
        s[1] = formatTime(dateTime.getHour(), dateTime.getMinute(), ':');
        return s;
    }


}
