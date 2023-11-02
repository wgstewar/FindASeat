package com.example.findaseat.Classes;

import java.util.ArrayList;

public class User {
    private ArrayList<Integer> reservations;
    private String fullName;
    private String uscId;
    private String username;
    private String password;
    private String affiliation;
    private String photoPath;

    public User() {
        reservations = new ArrayList<Integer>();
        fullName = "";
        uscId = "0000000000";
        username = "";
        password = "";
        affiliation = "";
        photoPath = "";
    }
    public User(String fullName, String uscId, String username, String password, String affiliation, String photoPath) {
        reservations = new ArrayList<Integer>();
        this.fullName = fullName;
        this.uscId = uscId;
        this.username = username;
        this.password = password;
        this.affiliation = affiliation;
        this.photoPath = photoPath;
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

    public String getPassword() {
        return password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getPhotoPath() {
        return photoPath;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
