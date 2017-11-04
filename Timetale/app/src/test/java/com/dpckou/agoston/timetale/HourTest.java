package com.dpckou.agoston.timetale;

import com.dpckou.agoston.timetale.weekday.Hour;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kissa on 2017. 11. 04..
 */

public class HourTest {

    Hour hour;

    @Before
    public void setUp() {
        hour = new Hour(0);
    }

    @Test
    public void hourTest1() {
        Assert.assertEquals(hour.getHour(), (Integer)0);
        Assert.assertNull(hour.getEvent());
        Assert.assertFalse(hour.isEventPlanned());
    }

    @Test
    public void hourTest2() {
        String event = "Basketball training";
        hour.setEvent(event);

        Assert.assertEquals(hour.getHour(), (Integer)0);
        Assert.assertEquals(hour.getEvent(), event);
        Assert.assertTrue(hour.isEventPlanned());
    }

    @Test(expected = IllegalArgumentException.class)
    public void hourTest3() {
        hour = new Hour(-5);
    }
}
