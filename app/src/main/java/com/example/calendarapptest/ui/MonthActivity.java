package com.example.calendarapptest.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.calendarapptest.AddEvent;
import com.example.calendarapptest.MonthPageAdapter;
import com.example.calendarapptest.R;
import com.example.calendarapptest.models.DayModel;
import com.example.calendarapptest.models.EventInfo;
import com.example.calendarapptest.models.EventModel;
import com.example.calendarapptest.models.MonthModel;
import com.example.calendarapptest.utils.Utility;
import com.example.calendarapptest.utils.monthview.GooglecalenderView;
import com.example.calendarapptest.utils.monthview.MonthChangeListner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MonthActivity extends AppCompatActivity {
    MonthPageAdapter adapter;
    private HashMap<LocalDate, EventInfo> alleventlist;
    private HashMap<LocalDate, EventInfo> montheventlist;
    private ArrayList<EventModel> eventalllist;
    ViewPager viewPager;
    private GooglecalenderView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        viewPager = findViewById(R.id.viewPager);
        calendarView = findViewById(R.id.calendar);

        if (viewPager == null || calendarView == null) {
            throw new RuntimeException("ViewPager or CalendarView is not properly initialized");
        }

        ArrayList<MonthModel> monthModels = generateMonthModels();

        // Create a HashMap of events

        EventInfo event = new EventInfo(
                new String[]{"Event 1", "Event 2", "Event 3"},
                false,  // isallday
                539,    // id
                "vantrunglast@gmail.com",  // accountname
                0,      // noofdayevent
                1731308400000L,  // starttime (Timestamp for start)
                1731310200000L,  // endtime (Timestamp for end)
                new EventInfo(
                        null,
                        false,
                        540,
                        "vantrunglast@gmail.com",
                        0,
                        1731311100000L,
                        1731314700000L,
                        new EventInfo(
                                null,
                                false,
                                541,
                                "vantrunglast@gmail.com",
                                0,
                                1731303900000L,
                                1731307500000L,
                                new EventInfo(
                                        null,
                                        false,
                                        542,
                                        "vantrunglast@gmail.com",
                                        0,
                                        1731307500000L,
                                        1731311100000L,
                                        new EventInfo(
                                                null,
                                                false,
                                                543,
                                                "vantrunglast@gmail.com",
                                                0,
                                                1731314700000L,
                                                1731318300000L,
                                                new EventInfo(
                                                        null,
                                                        false,
                                                        544,
                                                        "vantrunglast@gmail.com",
                                                        0,
                                                        1731318300000L,
                                                        1731321900000L,
                                                        new EventInfo(
                                                                null,
                                                                false,
                                                                545,
                                                                "vantrunglast@gmail.com",
                                                                0,
                                                                1731321900000L,
                                                                1731325500000L,
                                                                null,
                                                                "Event 6",
                                                                "Asia/Ho_Chi_Minh",
                                                                -2350809,
                                                                false
                                                        ),
                                                        "Event 5",
                                                        "Asia/Ho_Chi_Minh",
                                                        -2350809,
                                                        false
                                                ),
                                                "Event 4",
                                                "Asia/Ho_Chi_Minh",
                                                -2350809,
                                                false
                                        ),
                                        "Event 3",
                                        "Asia/Ho_Chi_Minh",
                                        -2350809,
                                        false
                                ),
                                "Event 2",
                                "Asia/Ho_Chi_Minh",
                                -16738680,
                                false
                        ),
                        "Event 1",
                        "Asia/Ho_Chi_Minh",
                        -16738680,
                        false
                ),
                "Event 1",
                "Asia/Ho_Chi_Minh",
                -16738680,
                false
        );

        EventInfo even2 = new EventInfo(
                new String[]{"Event 2!"},
                false,
                382,
                "Sinh nhật",
                1,
                1726099200000L, // July 14, 2024, 00:00:00 UTC
                1726185599000L,   // July 14, 2024, 23:59:59 UTC
                null,
                "Chúc mừng sinh nhật!",
                "UTC",
                -16738680,
                false
        );

        EventInfo even3 = new EventInfo(
                new String[]{"Event 2!"},
                false,
                383,
                "Sinh nhật",
                1,
                1726099200000L, // July 14, 2024, 00:00:00 UTC
                1726185599000L,   // July 14, 2024, 23:59:59 UTC
                null,
                "event 99!",
                "UTC",
                -16738680,
                false
        );
        alleventlist = new HashMap<>();
        alleventlist.put(new LocalDate(2024, 1, 15), event);
        alleventlist.put(new LocalDate(2024, 1, 16), even2);
        alleventlist.put(new LocalDate(2024, 1, 17), even3);


//        HashMap<LocalDate, EventInfo> monthEventGeneration = new HashMap<>();
//        EventInfo eventInfoTemp2 = new EventInfo();
//        eventInfoTemp2.title = "Sample Event 2";
//        eventInfoTemp2.starttime = new LocalDate(2024, 11, 1).toDate().getTime();
//        eventInfoTemp2.endtime = new LocalDate(2024, 11, 2).toDate().getTime();
//        eventInfoTemp2.eventcolor = 0x0000FF; // Blue color
//        eventInfoTemp2.isallday = true;
//        monthEventGeneration.put(new LocalDate(2024, 1, 1), eventInfoTemp2);
//        monthEventGeneration.put(new LocalDate(2024, 1, 15), eventInfoTemp2);

        montheventlist = new HashMap<>();

//        LocalDate minDate = new LocalDate(2024, 1, 1);
//        LocalDate maxDate = new LocalDate(2024, 12, 31);
//        if (calendarView != null) {
//            calendarView.init(eventHashMap, minDate, maxDate);
//            calendarView.setCurrentmonth(new LocalDate(2024, 11, 1));
//        }


        adapter = new MonthPageAdapter(getSupportFragmentManager(), monthModels, 0, alleventlist, montheventlist);
        viewPager.setAdapter(adapter);

        calendarView.setMonthChangeListner(new MonthChangeListner() {
            @Override
            public void onmonthChange(MonthModel monthModel) {
                LocalDate localDate = new LocalDate();
                String year = monthModel.getYear() == localDate.getYear() ? "" : monthModel.getYear() + "";
            }
        });

        calendarView.setMonthChangeListner(new MonthChangeListner() {
            @Override
            public void onmonthChange(MonthModel monthModel) {
                /**
                 * call when Googlecalendarview is open  scroll viewpager available inside GoogleCalendar
                 */
                LocalDate localDate = new LocalDate();
                String year = monthModel.getYear() == localDate.getYear() ? "" : monthModel.getYear() + "";
//                monthname.setText(monthModel.getMonthnamestr() + " " + year);
//                if (weekviewcontainer.getVisibility() == View.VISIBLE) {
//                    Calendar todaydate = Calendar.getInstance();
//                    todaydate.set(Calendar.DAY_OF_MONTH, 1);
//                    todaydate.set(Calendar.MONTH, monthModel.getMonth() - 1);
//                    todaydate.set(Calendar.YEAR, monthModel.getYear());
//                    mWeekView.goToDate(todaydate);
//
//                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("permission", "not granted");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, 200);
            }
        } else {
            Log.e("permission", "granted");
//            isgivepermission = true;
            LocalDate mintime = new LocalDate().minusYears(5);
            LocalDate maxtime = new LocalDate().plusYears(5);
            alleventlist = Utility.readCalendarEvent(this, mintime, maxtime);

            Log.e("alleventlist", alleventlist.size() + "");


            montheventlist = new HashMap<>();

            for (LocalDate localDate : alleventlist.keySet()) {
                EventInfo eventInfo = alleventlist.get(localDate);
                Log.e("eventInfo", eventInfo.toString());
                while (eventInfo != null) {
                    if (eventInfo.noofdayevent > 1) {

                        LocalDate nextmonth = localDate.plusMonths(1).withDayOfMonth(1);
                        LocalDate enddate = new LocalDate(eventInfo.endtime);
                        while (enddate.isAfter(nextmonth)) {
                            if (montheventlist.containsKey(nextmonth)) {
                                int firstday = nextmonth.dayOfMonth().withMinimumValue().dayOfWeek().get();
                                if (firstday == 7) firstday = 0;
                                int noofdays = Days.daysBetween(nextmonth, enddate).getDays() + firstday;
                                EventInfo newobj = new EventInfo();
                                newobj.title = eventInfo.title;
                                newobj.timezone = eventInfo.timezone;
                                newobj.isallday = eventInfo.isallday;
                                newobj.eventcolor = eventInfo.eventcolor;
                                newobj.endtime = eventInfo.endtime;
                                newobj.accountname = eventInfo.accountname;
                                newobj.isalreadyset = true;
                                newobj.starttime = eventInfo.starttime;
                                newobj.noofdayevent = noofdays;
                                newobj.id = eventInfo.id;
                                EventInfo beginnode = montheventlist.get(nextmonth);
                                newobj.nextnode = beginnode;
                                montheventlist.put(nextmonth, newobj);

                            } else {
                                int firstday = nextmonth.dayOfMonth().withMinimumValue().dayOfWeek().get();
                                if (firstday == 7) firstday = 0;
                                int noofdays = Days.daysBetween(nextmonth, enddate).getDays() + firstday;
                                EventInfo newobj = new EventInfo();
                                newobj.title = eventInfo.title;
                                newobj.timezone = eventInfo.timezone;
                                newobj.accountname = eventInfo.accountname;
                                newobj.isallday = eventInfo.isallday;
                                newobj.eventcolor = eventInfo.eventcolor;
                                newobj.endtime = eventInfo.endtime;
                                newobj.isalreadyset = true;
                                newobj.starttime = eventInfo.starttime;
                                newobj.noofdayevent = noofdays;
                                newobj.id = eventInfo.id;
                                montheventlist.put(nextmonth, newobj);

                            }
                            Log.e("nextmonth", nextmonth.toString());
                            Log.e("jdata" + localDate.toString() + "," + eventInfo.noofdayevent, eventInfo.title + "," + new LocalDate(eventInfo.starttime) + "," + new LocalDate(eventInfo.endtime));
                            nextmonth = nextmonth.plusMonths(1).withDayOfMonth(1);
                        }


                    }
                    eventInfo = eventInfo.nextnode;
                }

            }
            calendarView.init(alleventlist, mintime, maxtime);
            calendarView.setCurrentmonth(new LocalDate());
            calendarView.adjustheight();
            for (int i = 0; i < alleventlist.size(); i++) {
                if (alleventlist.get(i) != null) {
                    Log.e("alleventlist", alleventlist.get(i).toString());
                }
            }
            Log.e("montheventlist", montheventlist.size() + "");
            for (int i = 0; i < montheventlist.size(); i++) {
                Log.e("montheventlist", montheventlist.get(i).title);
            }
//            adapter = new MonthPageAdapter(getSupportFragmentManager(), monthModels, 0, alleventlist, montheventlist);
//            viewPager.setAdapter(adapter);
            //            mIsExpanded = false;
//            mAppBar.setExpanded(false, false);

        }

    }

    private ArrayList<MonthModel> generateMonthModels() {
        ArrayList<MonthModel> monthModels = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            MonthModel monthModel = new MonthModel();
            monthModel.setMonth(i + 1);
            monthModel.setYear(2024);
            monthModel.setNoofday(30); // Example number of days
            monthModel.setNoofweek(5); // Example number of weeks
            monthModel.setFirstday(1); // Example first day of the month
            monthModel.setDayModelArrayList(generateDayModels(i + 1, 2024));
            monthModels.add(monthModel);
        }
        return monthModels;
    }

//    private ArrayList<DayModel> generateDayModels(int month, int year) {
//        ArrayList<DayModel> dayModels = new ArrayList<>();
//        LocalDate firstDayOfMonth = new LocalDate(year, month, 1);
//        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek() % 7;
//        int daysInMonth = firstDayOfMonth.dayOfMonth().getMaximumValue();
//
//
//        for (int i = 0; i < firstDayOfWeek + daysInMonth; i++) {
//            DayModel dayModel = new DayModel();
//            if (i >= firstDayOfWeek) {
//                // Days within the month
//                int day = i - firstDayOfWeek + 1;
//                dayModel.setDay(day);
//                dayModel.setMonth(month);
//                dayModel.setYear(year);
//                dayModel.setToday(day == 15); // Example: 15th is today
//                dayModel.setIsenable(true);
//                dayModel.setSelected(false);
//                dayModel.setEventlist(false);
//                dayModel.setNoofdayevent(0);
//                dayModels.add(dayModel);
//            }
//        }
//        return dayModels;
//    }

    private ArrayList<DayModel> generateDayModels(int month, int year) {
        ArrayList<DayModel> dayModels = new ArrayList<>();
        LocalDate firstDayOfMonth = new LocalDate(year, month, 1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek() % 7;
        int daysInMonth = firstDayOfMonth.dayOfMonth().getMaximumValue();

        // Generate a random list of event days for the month
        ArrayList<Integer> eventDays = generateEventDays(daysInMonth);
        Log.e("MonthActivity", "Event Days for month " + month + ": " + eventDays.toString());

        for (int i = 0; i < firstDayOfWeek + daysInMonth; i++) {
            DayModel dayModel = new DayModel();
            if (i >= firstDayOfWeek) {
                int day = i - firstDayOfWeek + 1;
                dayModel.setDay(day);
                dayModel.setMonth(month);
                dayModel.setYear(year);
                dayModel.setToday(day == 15); // Example: 15th is today
                dayModel.setIsenable(true);
                dayModel.setSelected(false);
                // Assign events to the generated event days
                if (eventDays.contains(day)) {
                    dayModel.setEventlist(true);
                    dayModel.setNoofdayevent(1); // Setting 1 event per selected day
                    Log.e("MonthActivity", "Event set on day: " + day);
                } else {
                    dayModel.setEventlist(false);
                    dayModel.setNoofdayevent(0);
                }

                dayModels.add(dayModel);
            }
        }
        return dayModels;
    }

    private ArrayList<Integer> generateEventDays(int daysInMonth) {
        ArrayList<Integer> eventDays = new ArrayList<>();
        int numberOfEvents = 5; // Set the number of days with events

        while (eventDays.size() < numberOfEvents) {
            int day = 1 + (int) (Math.random() * daysInMonth);
            if (!eventDays.contains(day)) {
                eventDays.add(day);
            }
        }
        return eventDays;
    }



    @Subscribe
    public void onEvent(final AddEvent event) {
        Log.e("evenaat", event.getArrayList().toString());
//        for (int i = 0; i < event.getArrayList().size(); i++) {
//            Log.e("eventOnEvent", event.getArrayList().get(i).getEventname());
//        }
        eventalllist = event.getArrayList();
//        Log.e("eventalllist", eventalllist.toString() + "");
//        for (int i = 0; i < eventalllist.size(); i++) {
//            Log.e("eventOnEvent", eventalllist.get(i).getEventname());
//        }

        final TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {

            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            int monthheight = getDeviceHeight() - actionBarHeight - getnavigationHeight() - getStatusBarHeight();
            int recyheight = monthheight - getResources().getDimensionPixelSize(R.dimen.monthtopspace);
            int singleitem = (recyheight - 18) / 6;

            //monthviewpager.setAdapter(new MonthViewPagerAdapter(MainActivity.this,event.getMonthModels(),singleitem));
            viewPager.setAdapter(new MonthPageAdapter(getSupportFragmentManager(), event.getMonthModels(), singleitem, alleventlist, montheventlist));
            viewPager.setCurrentItem(calendarView.calculateCurrentMonth(LocalDate.now()), false);
        }

        // TODO: 2024-11-15 comment this block
//        indextrack = event.getIndextracker();
//        for (Map.Entry<LocalDate, Integer> entry : indextrack.entrySet()) {
//            dupindextrack.put(entry.getKey(), entry.getValue());
//        }
//
//        if (mNestedView.isAttachedToWindow()) {
//
//            mNestedView.getAdapter().notifyDataSetChanged();
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LocalDate localDate = new LocalDate();
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mNestedView.getLayoutManager();
//                if (indextrack.containsKey(LocalDate.now())) {
//
//                    Integer val = indextrack.get(LocalDate.now());
//                    expandedfirst = val;
//                    topspace = 20;
//                    linearLayoutManager.scrollToPositionWithOffset(expandedfirst, 20);
//                    EventBus.getDefault().post(new MonthChange(localDate, 0));
//                    month = localDate.getDayOfMonth();
//                    lastdate = localDate;
//                }
//            }
//        }, 100);
    }

    private int getDeviceHeight() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int height1 = size.y;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height1;
    }

    private int getDevicewidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getnavigationHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LocalDate mintime = new LocalDate().minusYears(5);
            LocalDate maxtime = new LocalDate().plusYears(5);
            alleventlist = Utility.readCalendarEvent(this, mintime, maxtime);
            for (int i = 0; i < alleventlist.size(); i++) {
                Log.e("event", alleventlist.get(i).title);
            }
            montheventlist = new HashMap<>();

            for (LocalDate localDate : alleventlist.keySet()) {
                EventInfo eventInfo = alleventlist.get(localDate);
                while (eventInfo != null) {
                    if (eventInfo.noofdayevent > 1) {
                        LocalDate nextmonth = localDate.plusMonths(1).withDayOfMonth(1);
                        LocalDate enddate = new LocalDate(eventInfo.endtime);
                        while (enddate.isAfter(nextmonth)) {
                            if (montheventlist.containsKey(nextmonth)) {
                                int firstday = nextmonth.dayOfMonth().withMinimumValue().dayOfWeek().get();
                                if (firstday == 7) firstday = 0;
                                int noofdays = Days.daysBetween(nextmonth, enddate).getDays() + firstday;
                                EventInfo newobj = new EventInfo();
                                newobj.title = eventInfo.title;
                                newobj.timezone = eventInfo.timezone;
                                newobj.isallday = eventInfo.isallday;
                                newobj.eventcolor = eventInfo.eventcolor;
                                newobj.endtime = eventInfo.endtime;
                                newobj.isalreadyset = true;
                                newobj.starttime = eventInfo.starttime;
                                newobj.noofdayevent = noofdays;
                                newobj.id = eventInfo.id;
                                EventInfo beginnode = montheventlist.get(nextmonth);
                                newobj.nextnode = beginnode;
                                montheventlist.put(nextmonth, newobj);
                            } else {
                                int firstday = nextmonth.dayOfMonth().withMinimumValue().dayOfWeek().get();
                                if (firstday == 7) firstday = 0;
                                int noofdays = Days.daysBetween(nextmonth, enddate).getDays() + firstday;
                                EventInfo newobj = new EventInfo();
                                newobj.title = eventInfo.title;
                                newobj.timezone = eventInfo.timezone;
                                newobj.isallday = eventInfo.isallday;
                                newobj.eventcolor = eventInfo.eventcolor;
                                newobj.endtime = eventInfo.endtime;
                                newobj.isalreadyset = true;
                                newobj.starttime = eventInfo.starttime;
                                newobj.noofdayevent = noofdays;
                                newobj.id = eventInfo.id;
                                montheventlist.put(nextmonth, newobj);
                            }
                            Log.e("nextmonth", nextmonth.toString());
                            Log.e("jdata" + localDate.toString() + "," + eventInfo.noofdayevent, eventInfo.title + "," + new LocalDate(eventInfo.starttime) + "," + new LocalDate(eventInfo.endtime));
                            nextmonth = nextmonth.plusMonths(1).withDayOfMonth(1);
                        }
                    }
                    eventInfo = eventInfo.nextnode;
                }
            }
            calendarView.init(alleventlist, mintime, maxtime);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    isgivepermission = true;
//                    lastdate = new LocalDate();
//                    calendarView.setCurrentmonth(new LocalDate());
//                    calendarView.adjustheight();
//                    mIsExpanded = false;
//                    mAppBar.setExpanded(false, false);
//                    mWeekView.notifyDatasetChanged();
//                    LinearLayoutManager linearLayoutManager= (LinearLayoutManager) mNestedView.getLayoutManager();
//                    if (indextrack.containsKey(new LocalDate())){
//                        smoothScroller.setTargetPosition(indextrack.get(new LocalDate()));
//                        linearLayoutManager.scrollToPositionWithOffset(indextrack.get(new LocalDate()),0);
//                    }
//                    else {
//                        for (int i=0;i<eventalllist.size();i++){
//                            if (eventalllist.get(i).getLocalDate().getMonthOfYear()==new LocalDate().getMonthOfYear()&&eventalllist.get(i).getLocalDate().getYear()==new LocalDate().getYear()){
//                                linearLayoutManager.scrollToPositionWithOffset(i,0);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }, 10);
        }
    }

}
