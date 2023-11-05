package com.example.findaseat.Classes;


public class Reservation {
    private int buildingId;
    private String uid;
    private Date date;
    private int startTime;
    private int endTime;
    private ReservationStatus status;

    public Reservation() {
        buildingId = -1;
        date = new Date();
        startTime = -1;
        endTime = -1;
        status = ReservationStatus.ERROR;
    }

    public Reservation(int buildingId, Date date, int startTime, int endTime) {
        this.buildingId = buildingId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ReservationStatus.ACTIVE;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public Date getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String intervalString() {
        int startHr = startTime/2, startMinute = (startTime % 2 == 0) ? 0 : 30,
                endHr = endTime/2, endMinute = (endTime % 2 == 0) ? 0 : 30;
        return String.format("%02d:%02d - %02d:%02d", startHr, startMinute, endHr, endMinute);
    }
}
