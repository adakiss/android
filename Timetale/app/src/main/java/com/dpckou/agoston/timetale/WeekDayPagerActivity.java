package com.dpckou.agoston.timetale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WeekDayPagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 7;

    private ViewPager mPager;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_day_pager);

        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new WeekDayPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }

    }

    private enum WeekDay {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday
    }

    private class WeekDayPagerAdapter extends FragmentStatePagerAdapter {

        public WeekDayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WeekDayFragment.create(WeekDay.values()[position].toString());
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
