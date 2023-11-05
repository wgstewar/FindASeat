package com.example.findaseat.Classes;

import java.util.HashMap;

public class Building {
    private String name;
    private String description;
    private int openTime;
    private int closeTime;
    private int maxAvailability;
    private HashMap<Weekday, int[]> availability;

    public Building() {
        name = "";
        description = "";
        openTime = -1;
        closeTime = -1;
        availability = new HashMap<Weekday, int[]>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getOpenTime() {
        return openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public HashMap<Weekday, int[]> getAvailability() {
        return availability;
    }

    public int getMaxAvailability() {
        return maxAvailability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public void setMaxAvailability(int maxAvailability) {
        this.maxAvailability = maxAvailability;
    }

    public void setAvailability(HashMap<Weekday, int[]> availability) {
        this.availability = availability;
    }

    public Reservation makeReservation(int start, int close, int day) {
        return new Reservation();
    }
}
