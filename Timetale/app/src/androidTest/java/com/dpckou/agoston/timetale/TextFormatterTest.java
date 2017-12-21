package com.dpckou.agoston.timetale;

import android.support.test.runner.AndroidJUnit4;

import com.dpckou.agoston.timetale.TextFormatter.TextFormatter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TextFormatterTest {
    private String first = "myNewEvent";
    private String second = "MyNewEvent";
    private String third = "my_new_event";
    private String fourth = "my123new3e7896vent";
    private String fifth = "my123newEvent_lol";

    @Test
    public void testCapitalizeFirst(){
        String _first = TextFormatter.capitalizeFirst(first);
        Assert.assertEquals(_first,"MyNewEvent");
    }

    @Test
    public void testDivideByCaps(){
        String _second = TextFormatter.divideByCaps(second);
        Assert.assertEquals(_second,"My New Event");
    }

    @Test
    public void testDivideByUnderscore(){
        String _third = TextFormatter.divideByUnderscore(third);
        Assert.assertEquals(_third,"my new event");
    }

    @Test
    public void testDivideNumbers(){
        String _fourth = TextFormatter.separateNumbers(fourth);
        Assert.assertEquals(_fourth,"my 123 new 3 e 7896 vent");
    }

    @Test
    public void testMultiFormat(){
        String _fifth = TextFormatter.multiFormat(fifth,
                new int[] {1,2,3,4});
        Assert.assertEquals(_fifth,"My 123 new Event lol");
    }
}
