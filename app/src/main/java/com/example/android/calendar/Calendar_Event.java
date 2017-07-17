package com.example.android.calendar;

/**
 * Created by Srikanth on 7/14/2017.
 */

public class Calendar_Event {
    int date,month;
    String day="",details="";
    Boolean holiday,remind;

    public void setDate(int date) {
        this.date = date;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setHoliday(Boolean holiday) {
        this.holiday = holiday;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    public String getDay() {
        return day;
    }

    public String getDetails() {
        return details;
    }

    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public Boolean isHoliday() {
        return holiday;
    }
}
