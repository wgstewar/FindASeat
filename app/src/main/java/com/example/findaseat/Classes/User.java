package com.example.findaseat.Classes;

import java.util.ArrayList;

public class User {
    private ArrayList<Integer> reservations;
    private String fullName;
    private String uscId;
    private String username;
    private String affiliation;

    public User() {
        reservations = new ArrayList<Integer>();
        fullName = "";
        uscId = "0000000000";
        username = "";
        affiliation = "";
    }
    public User(String fullName, String uscId, String username, String affiliation) {
        reservations = new ArrayList<Integer>();
        this.fullName = fullName;
        this.uscId = uscId;
        this.username = username;
        this.affiliation = affiliation;
    }

    public ArrayList<Integer> getReservations() {
        return reservations;
    }

    public String getName() {
        return fullName;
    }

    public String getUscId() {
        return uscId;
    }

    public String getUsername() {
        return username;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setReservations(ArrayList<Integer> reservations) {
        this.reservations = reservations;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }

    public void setUscId(String uscId) {
        this.uscId = uscId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
}
