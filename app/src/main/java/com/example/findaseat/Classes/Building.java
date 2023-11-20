package com.example.findaseat.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Building {
    private String name;
    private String description;
    private int openTime;
    private int closeTime;
    private int maxAvailability;
    private HashMap<String, ArrayList<Integer>> availability;

    public Building() {
        name = "";
        description = "";
        openTime = -1;
        closeTime = -1;
        maxAvailability = 0;
        availability = new HashMap<>();
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

    public HashMap<String, ArrayList<Integer>> getAvailability() {
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

    public void setAvailability(HashMap<String, ArrayList<Integer>> availability) {
        this.availability = availability;
    }

    public void addSeat(Weekday wkday, int interval) {
        int currSeats = availability.get(wkday.toString()).get(interval);
        availability.get(wkday.toString()).set(interval, currSeats+1);
    }

    public void removeSeat(Weekday wkday, int interval) {
        int currSeats = availability.get(wkday.toString()).get(interval);
        availability.get(wkday.toString()).set(interval, currSeats-1);
    }
}
