package com.example.calendarapptest.models;

import java.util.Arrays;

public class EventInfo {
    public String[] eventtitles;
    public boolean isallday;
    public int id;
    public String accountname;
    public int noofdayevent;
    public long starttime;
    public long endtime;
    public EventInfo nextnode;
    public String title;
    public String timezone;
    public int eventcolor;
    public boolean isalreadyset;

    public EventInfo(String[] eventtitles) {
        this.eventtitles = eventtitles;
    }

    public EventInfo() {
    }

    public EventInfo(String[] eventtitles, boolean isallday, int id, String accountname, int noofdayevent, long starttime, long endtime, EventInfo nextnode, String title, String timezone, int eventcolor, boolean isalreadyset) {
        this.eventtitles = eventtitles;
        this.isallday = isallday;
        this.id = id;
        this.accountname = accountname;
        this.noofdayevent = noofdayevent;
        this.starttime = starttime;
        this.endtime = endtime;
        this.nextnode = nextnode;
        this.title = title;
        this.timezone = timezone;
        this.eventcolor = eventcolor;
        this.isalreadyset = isalreadyset;
    }

    public EventInfo(EventInfo eventInfo) {
        this.eventtitles = eventInfo.eventtitles;
        this.isallday = eventInfo.isallday;
        this.id = eventInfo.id;
        this.accountname = eventInfo.accountname;
        this.noofdayevent = eventInfo.noofdayevent;
        this.starttime = eventInfo.starttime;
        this.endtime = eventInfo.endtime;
        this.title = eventInfo.title;
        this.timezone = eventInfo.timezone;
        this.eventcolor = eventInfo.eventcolor;
        this.isalreadyset = eventInfo.isalreadyset;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
                "eventtitles=" + Arrays.toString(eventtitles) +
                ", isallday=" + isallday +
                ", id=" + id +
                ", accountname='" + accountname + '\'' +
                ", noofdayevent=" + noofdayevent +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", nextnode=" + nextnode +
                ", title='" + title + '\'' +
                ", timezone='" + timezone + '\'' +
                ", eventcolor=" + eventcolor +
                ", isalreadyset=" + isalreadyset +
                '}';
    }
}
