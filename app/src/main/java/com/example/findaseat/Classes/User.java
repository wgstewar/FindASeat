package com.example.findaseat.Classes;

import java.util.ArrayList;

public class User {
    private ArrayList<Reservation> reservations;
    private String fullName;
    private String uscId;
    private String username;
    private String affiliation;
    private String email;

    public User() {
        reservations = new ArrayList<Reservation>();
        fullName = "";
        uscId = "0000000000";
        username = "";
        affiliation = "";
        email = "";
    }
    public User(String fullName, String uscId, String username, String email, String affiliation) {
        reservations = new ArrayList<Reservation>();
        this.fullName = fullName;
        this.uscId = uscId;
        this.username = username;
        this.email = email;
        this.affiliation = affiliation;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUscId() {
        return uscId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() { return email; }

    public String getAffiliation() {
        return affiliation;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUscId(String uscId) {
        this.uscId = uscId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) { this.email = email; }
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void addReservation(Reservation r) {
        if (reservations.size() == 20) {
            reservations.remove(0);
        }
        reservations.add(r);
    }
}
