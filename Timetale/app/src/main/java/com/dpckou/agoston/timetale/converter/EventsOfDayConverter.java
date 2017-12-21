package com.dpckou.agoston.timetale.converter;

import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.weekday.Hour;

import java.util.Calendar;

public class EventsOfDayConverter {

    private static Calendar c = Calendar.getInstance();

    public Hour[] convert(Event[] events, long dayStart, long dayEnd) {
        Hour[] result = new Hour[24];
        for(int i = 0; i < 24; i++) {
            result[i] = new Hour(i);
        }

        for(Event e : events) {
            int startHour = getStartHour(dayStart, e);
            int endHour = getEndHour(dayEnd, e);

            for(int j = startHour; j <= endHour; j++) {
                if(result[j].isEventPlanned()) {
                    result[j].setEvent(result[j].getEvent() + ", " + e.getEventName());
                } else {
                    result[j].setEvent(e.getEventName());
                }
            }
        }


        return result;
    }

    private int getEndHour(long dayEnd, Event e) {
        if(e.getEventEnd() <= dayEnd) {
            c.setTimeInMillis(e.getEventEnd()-1);
            return c.get(Calendar.HOUR_OF_DAY);
        }
        return 23;
    }

    private int getStartHour(long dayStart, Event e) {
        if(e.getEventStart() >= dayStart) {
            c.setTimeInMillis(e.getEventStart());
            return c.get(Calendar.HOUR_OF_DAY);
        }
        return 0;
    }
}
