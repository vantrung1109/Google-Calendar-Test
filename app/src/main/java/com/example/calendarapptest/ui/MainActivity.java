package com.example.calendarapptest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.calendarapptest.models.MessageEvent;
import com.example.calendarapptest.R;
import com.example.calendarapptest.utils.weekview.DateTimeInterpreter;
import com.example.calendarapptest.utils.weekview.MonthLoader;
import com.example.calendarapptest.utils.weekview.WeekView;
import com.example.calendarapptest.utils.weekview.WeekViewEvent;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements MonthLoader.MonthChangeListener
{
    private WeekView mWeekView;
    private View myshadow;
    public static LocalDate lastdate = LocalDate.now();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeekView = findViewById(R.id.weekView);
        mWeekView.setMonthChangeListener(this);
        myshadow = findViewById(R.id.myshadow);
        mWeekView.setshadow(myshadow);
        mWeekView.setNumberOfVisibleDays(1);
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mWeekView.setAllDayEventHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, getResources().getDisplayMetrics()));
//        Calendar todaydate = Calendar.getInstance();
//        todaydate.set(Calendar.DAY_OF_MONTH, MainActivity.lastdate.getDayOfMonth());
//        todaydate.set(Calendar.MONTH, MainActivity.lastdate.getMonthOfYear() - 1);
//        todaydate.set(Calendar.YEAR, MainActivity.lastdate.getYear());

        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int day = intent.getIntExtra("day", 0);
        Calendar todaydate = Calendar.getInstance();
        todaydate.set(Calendar.DAY_OF_MONTH, day);
        todaydate.set(Calendar.MONTH, month);
        todaydate.set(Calendar.YEAR, year);
//        Log.e("MainActivityDate", "Today: " + todaydate.get(Calendar.DAY_OF_MONTH) + "/" + todaydate.get(Calendar.MONTH) + "/" + todaydate.get(Calendar.YEAR));
        mWeekView.goToDate(todaydate);

        mWeekView.setfont(ResourcesCompat.getFont(this, R.font.googlesans_regular), 0);
        mWeekView.setfont(ResourcesCompat.getFont(this, R.font.googlesansmed), 1);

        // Show a toast message about the touched event.
//        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
//        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
//        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
//        mWeekView.setEmptyViewLongPressListener(this);
//        mWeekView.setScrollListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
    }



    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretday(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (mWeekView.getNumberOfVisibleDays() == 7)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase();
            }

            @Override
            public String interpretDate(Calendar date) {
                int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
                return dayOfMonth + "";
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return getEvents(newYear, newMonth);
    }
    private List<WeekViewEvent> getEvents(int year, int month) {
        List<WeekViewEvent> events = new ArrayList<>();

        // Define the starting day for events.
        int startingDay = 10; // e.g., 10th of the month
        int[] hours = {10, 15, 11, 12, 12, 12, 12, 12, 12, 12}; // Sample start hours for events each day

        for (int day = startingDay; day < startingDay + 6; day++) {
            for (int i = 0; i < hours.length; i++) {
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_MONTH, day);
                startTime.set(Calendar.MONTH, month - 1);
                startTime.set(Calendar.YEAR, year);
                startTime.set(Calendar.HOUR_OF_DAY, hours[i]);
                startTime.set(Calendar.MINUTE, 0);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, hours[i] + 1); // Each event lasts 1 hour

                WeekViewEvent event = new WeekViewEvent(
                        day * 10 + i, // unique ID
                        "Sample Event " + ((day - startingDay) * hours.length + (i + 1)),
                        startTime,
                        endTime,
                        "vantrung",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8fg-r_QHQCXAjJQbrq3jn-rEX0cdx0Ho7qg&s"
                );
                event.setColor(getResources().getColor(R.color.event_color_01 + i % 2));
                events.add(event);
            }
        }
        return events;
    }


    public void selectdateFromMonthPager(int year, int month, int day) {
        MainActivity.lastdate = new LocalDate(year, month, day);
        LocalDate localDate = new LocalDate();
        String yearstr = MainActivity.lastdate.getYear() == localDate.getYear() ? "" : MainActivity.lastdate.getYear() + "";
//        monthname.setText(MainActivity.lastdate.toString("MMMM") + " " + yearstr);
//        calendarView.setCurrentmonth(MainActivity.lastdate);
//        calendarView.adjustheight();
//        mIsExpanded = false;
//        mAppBar.setExpanded(false, false);
        EventBus.getDefault().post(new MessageEvent(new LocalDate(year, month, day)));
//        monthviewpager.setVisibility(View.GONE);
//        yearviewpager.setVisibility(View.GONE);
//        mNestedView.setVisibility(View.VISIBLE);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
//        ((MyAppBarBehavior) layoutParams.getBehavior()).setScrollBehavior(true);
//        mAppBar.setElevation(20);
//        mArrowImageView.setVisibility(View.VISIBLE);
    }
}