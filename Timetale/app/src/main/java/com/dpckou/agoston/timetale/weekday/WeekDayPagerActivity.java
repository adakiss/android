package com.dpckou.agoston.timetale.weekday;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dpckou.agoston.timetale.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeekDayPagerActivity extends FragmentActivity {

    private static final int NUM_PAGES = 30;

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

    private class WeekDayPagerAdapter extends FragmentStatePagerAdapter {

        private List<WeekDayFragment> mFragments;
        private Date date;

        public WeekDayPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<>();

            date = new Date();
            Calendar c = Calendar.getInstance();

            for (int i = 0; i < NUM_PAGES ; i++) {
                c.setTime(date);
                c.add(Calendar.DATE, i);
                mFragments.add(WeekDayFragment.create(c.getTime().getTime()));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
