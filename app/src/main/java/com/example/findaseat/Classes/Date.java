package com.example.findaseat.Classes;

import java.time.LocalDate;

public class Date {
    private int year;
    private int month;
    private int day;
    private Weekday weekday;

    public Date() {
        year = 1900;
        month = 1;
        day = 1;
        weekday = Weekday.SUNDAY;
    }

    public Date(LocalDate d) {
        this.year = LocalDate.now().getYear();
        this.month = LocalDate.now().getMonthValue();
        this.day = LocalDate.now().getDayOfMonth();
        this.weekday = Weekday.valueOf(LocalDate.now().getDayOfWeek().toString());
    }

    public Date(int year, int month, int day, Weekday weekday) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.weekday = weekday;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public boolean equals(Date other) {
        return this.year == other.year && this.day == other.day && this.month == other.month && this.weekday == other.weekday;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
}
