package com.example.calendarapptest;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.calendarapptest.models.EventInfo;
import com.example.calendarapptest.models.MonthModel;
import com.example.calendarapptest.ui.MonthFragment;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;

public class MonthPageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<MonthModel> monthModels;
    private int singleitemheight;

    private HashMap<LocalDate, EventInfo> alleventlist;

    public HashMap<LocalDate, EventInfo> getAlleventlist() {
        return alleventlist;
    }

    public void setAlleventlist(HashMap<LocalDate, EventInfo> alleventlist) {
        this.alleventlist = alleventlist;
    }

    public HashMap<LocalDate, EventInfo> getMontheventlist() {
        return montheventlist;
    }

    public void setMontheventlist(HashMap<LocalDate, EventInfo> montheventlist) {
        this.montheventlist = montheventlist;
    }

    private HashMap<LocalDate, EventInfo> montheventlist;

    // private ArrayList<MonthFragment> firstFragments=new ArrayList<>();

    public MonthPageAdapter(FragmentManager fragmentManager, ArrayList<MonthModel> monthModels, int singleitemheight, HashMap<LocalDate, EventInfo> alleventlist, HashMap<LocalDate, EventInfo> montheventlist) {

        super(fragmentManager);
        this.monthModels = monthModels;
        this.singleitemheight = singleitemheight;
        this.alleventlist = alleventlist;
        this.montheventlist = montheventlist;
//        for (int position=0;position<monthModels.size();position++){
//            firstFragments.add(MonthFragment.newInstance(monthModels.get(position).getMonth(), monthModels.get(position).getYear(), monthModels.get(position).getFirstday(), monthModels.get(position).getDayModelArrayList(), alleventlist, singleitemheight));
//        }
    }

//        public ArrayList<MonthFragment> getFirstFragments() {
//            return firstFragments;
//        }

    public ArrayList<MonthModel> getMonthModels() {
        return monthModels;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return monthModels.size();
    }


    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        try {
            return MonthFragment.newInstance(monthModels.get(position).getMonth(), monthModels.get(position).getYear(), monthModels.get(position).getFirstday(), monthModels.get(position).getDayModelArrayList(), alleventlist, singleitemheight, montheventlist);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;

        }
    }
}