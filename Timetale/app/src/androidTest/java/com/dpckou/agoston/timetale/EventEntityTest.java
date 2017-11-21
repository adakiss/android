package com.dpckou.agoston.timetale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.persistence.EventDao;
import com.dpckou.agoston.timetale.persistence.TimetaleDatabase;

import java.sql.Date;

/**
 * Created by kissa on 2017. 11. 09..
 */

@RunWith(AndroidJUnit4.class)
public class EventEntityTest {

    private TimetaleDatabase mDb;
    private EventDao mDao;

    @Before
    public void setUp() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(ctx, TimetaleDatabase.class).build();
        mDao = mDb.getDaoInstance();
    }

    @Test
    public void testAddEvent() {
        Event event = new Event();
        event.setEventName("TestEvent");
        event.setEventStart(new Date(0).getTime());
        event.setEventEnd(new Date(1000).getTime());
        mDao.addNewEvent(event);
        Event[] events = mDao.eventsOnDay(new Date(10).getTime(), new Date(990).getTime());
        AssertEvent(events[0], event);
    }

    @Test
    public void testUpdateEvent() {
        Event event = new Event();
        event.setEventName("TestEvent");
        event.setEventStart(0);
        event.setEventEnd(1000);
        mDao.addNewEvent(event);
        event.setEventName("TestEventName2");
        mDao.updateEvent(event);
        Event[] events = mDao.eventsOnDay(10, 990);
        AssertEvent(events[0], event);
    }

    @Test
    public void testDeleteEvent() {
        Event event = new Event();
        event.setEventName("TestEventName");
        event.setEventStart(0);
        event.setEventEnd(1000);
        mDao.addNewEvent(event);
        Event[] events = mDao.eventsOnDay(10, 990);
        Assert.assertEquals(events.length, 1);
        mDao.deleteEvent(event);
        events = mDao.eventsOnDay(10, 990);
        Assert.assertEquals(events.length, 0);
    }

    private void AssertEvent(Event event1, Event event2) {
        Assert.assertEquals(event1.getEventName(), event2.getEventName());
        Assert.assertEquals(event1.getEventStart(), event2.getEventStart());
        Assert.assertEquals(event1.getEventEnd(), event2.getEventEnd());
        Assert.assertEquals(event1.getId(), event2.getId());
    }

    @After
    public void closeDb() {
        mDb.close();
    }
}
