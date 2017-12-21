package com.dpckou.agoston.timetale;

import android.support.test.runner.AndroidJUnit4;

import com.dpckou.agoston.timetale.DateTimeModels.DateTime;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class DateTimeTest {

    private int first = 3;
    private int first_2 = 9;
    private int first_3 = 22;

    private DateTime second = new DateTime(2000,4,7,11,2);

    private DateTime third_1;
    private DateTime third_2;

    @Test
    public void testNullify() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        String _first = "03";
        String _first_2 = "09";
        String _first_3 = "22";

        Method method = DateTime.class.getDeclaredMethod("nullify", Integer.TYPE,String.class);
        method.setAccessible(true);

        Assert.assertEquals(_first, method.invoke(null, first));
        Assert.assertEquals(_first_2, method.invoke(null, first_2));
        Assert.assertEquals(_first_3, method.invoke(null, first_3));
    }

    @Test
    public void testFormatDateTime(){
        String _second_date = "2000.04.07";
        String _second_date_2 = "2000:04:07";

        String _second_time = "11:02";
        String _second_time_2 = "11.02";

        Assert.assertEquals(_second_date,DateTime.formatDate(
                second.getYear(),second.getMonth(),second.getDay(),'.'
        ));
        Assert.assertEquals(_second_date_2,DateTime.formatDate(
                second.getYear(),second.getMonth(),second.getDay(),':'
        ));
        Assert.assertEquals(_second_time,DateTime.formatTime(second.getHour(),second.getMinute(),':'));
        Assert.assertEquals(_second_time_2,DateTime.formatTime(second.getHour(),second.getMinute(),'.'));
    }

    @Test
    public void testCompareTo(){
        third_1 = new DateTime(2017,3,11,10,11);
        third_2 = new DateTime(2017,3,11,10,12);

        Assert.assertEquals(third_1.compareTo(third_2),-1); //ours, third_1 is less.
        Assert.assertEquals(third_2.compareTo(third_1),1); //ours, third_2 is greater.

        Assert.assertEquals(third_1.compareByDate(third_2),0);
        Assert.assertEquals(third_1.compareByTime(third_2),-1);
    }

    @Test
    public void testParseLong(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(10000000L));
        String[] _expected = new String[] {"1970.00.01","00:00"}; //lel 0. hónap
        String[] _res = DateTime.parseLong(10000000L);
        Assert.assertEquals(_res[0],_expected[0]);
    }
}
