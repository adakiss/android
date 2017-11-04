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

import com.dpckou.agoston.timetale.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        mPager.setCurrentItem(c.DAY_OF_WEEK);
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

        private List<WeekDayFragment> mFragments;
        private Date date;
        private int prevDay;

        public WeekDayPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<>();
            mFragments.add(WeekDayFragment.create(WeekDay.Sunday.toString()));
            mFragments.add(WeekDayFragment.create(WeekDay.Monday.toString()));
            mFragments.add(WeekDayFragment.create(WeekDay.Tuesday.toString()));
            mFragments.add(WeekDayFragment.create(WeekDay.Wednesday.toString()));
            mFragments.add(WeekDayFragment.create(WeekDay.Thursday.toString()));
            mFragments.add(WeekDayFragment.create(WeekDay.Friday.toString()));
            mFragments.add(WeekDayFragment.create(WeekDay.Saturday.toString()));

            date = new Date();
            prevDay = 0;
        }

        @Override
        public Fragment getItem(int position) {
            int index = position%mFragments.size();

            getNextDate(position);
            prevDay = position;

            Bundle args = mFragments.get(index).getArguments();
            args.putLong("date", date.getTime());
            mFragments.get(index).setArguments(args);

            return mFragments.get(index);
        }

        private void getNextDate(int position) {

            Calendar c = Calendar.getInstance();
            c.setTime(date);

            if (prevDay < position) {
                c.add(Calendar.DATE, 1);
                date = c.getTime();
            } else if ( prevDay > position) {
                c.add(Calendar.DATE, -1);
                date = c.getTime();
            }

        }

        @Override
        public int getCount() {
            return 30;
        }
    }
}
