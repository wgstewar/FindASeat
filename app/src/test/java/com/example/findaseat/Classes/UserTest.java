package com.example.findaseat.Classes;

import static org.junit.Assert.*;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import junit.framework.TestCase;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest extends TestCase {
    private User u;
    @BeforeEach
    public void setUp() throws Exception {
        u = new User();
    }

    @Test
    public void addReservation_Basic() {
        Date d = new Date(LocalDate.now());
        Reservation r1 = new Reservation(1, d, 0, 4);
        r1.setStatus(ReservationStatus.COMPLETED);
        u.addReservation(r1);
        Reservation r2 = new Reservation(1, d, 5, 10);
        boolean added = u.addReservation(r2);


        Reservation activeR = new Reservation(1, d, 5, 10);
        assertTrue(u.getReservations().get(0).equals(activeR));
        assertTrue(added);
        assertEquals(ReservationStatus.ACTIVE, u.getReservations().get(0).getStatus());
    }

    @Test
    public void addReservation_AlreadyActive() {
        Date d = new Date(LocalDate.now());
        Reservation r1 = new Reservation(1, d, 0, 4);
        boolean added = u.addReservation(r1);
        assertTrue(added);

        Reservation r2 = new Reservation(1, d, 5, 10);
        added = u.addReservation(r2);
        assertFalse(added);

        Reservation addedR = u.getReservations().get(0);
        assertFalse(r2.equals(addedR));
    }

    @Test
    public void addReservation_Max() {
        Date d = new Date(LocalDate.now());
        ArrayList<Reservation> eReservations = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Reservation r = new Reservation(1, d, 2*i, 2*i+2);
            r.setStatus(ReservationStatus.COMPLETED);
            u.addReservation(r);
            eReservations.add(0,r);
        }
        Reservation r = new Reservation(1, d, 0,1);
        u.addReservation(r);
        eReservations.add(0,r);
        assertEquals(20, u.getReservations().size());
        for (int i = 0; i < 20; i++) {
            assertTrue(eReservations.get(i).equals(u.getReservations().get(i)));
        }
    }

    @Test
    public void updateActiveReservation_BeforeReservation() {
        Date d = new Date(LocalDate.now());
        Reservation r = new Reservation(1, d, 20, 24);
        u.addReservation(r);
        LocalTime t = LocalTime.of(8,30);
        u.updateActiveReservation(t);
        assertEquals(ReservationStatus.ACTIVE, u.getReservations().get(0).getStatus());
    }

    @Test
    public void updateActiveReservation_DuringReservation() {
        Date d = new Date(LocalDate.now());
        Reservation r = new Reservation(1, d, 20, 24);
        u.addReservation(r);
        LocalTime t = LocalTime.of(11,15);
        u.updateActiveReservation(t);
        assertEquals(ReservationStatus.ACTIVE, u.getReservations().get(0).getStatus());
    }

    @Test
    public void updateActiveReservation_AfterReservation() {
        Date d = new Date(LocalDate.now());
        Reservation r = new Reservation(1, d, 20, 24);
        u.addReservation(r);
        LocalTime t = LocalTime.of(13,00);
        u.updateActiveReservation(t);
        assertEquals(ReservationStatus.COMPLETED, u.getReservations().get(0).getStatus());
    }

    @Test
    public void activeReservationSetCorrectly() {
        Date d = new Date(LocalDate.now());
        Reservation r2 = new Reservation(1, d, 5, 10);
        boolean added = u.addReservation(r2);
        Reservation activeR = new Reservation(1, d, 5, 10);
        assertTrue(u.activeReservation().equals(activeR));
        assertTrue(added);
    }

    @Test
    public void cancelReservation() {
        Date d = new Date(LocalDate.now());
        Reservation r2 = new Reservation(1, d, 5, 10);
        boolean added = u.addReservation(r2);
        u.cancelActiveReservation();
        assertEquals(ReservationStatus.CANCELLED, u.getReservations().get(0).getStatus());
        //assertTrue(u.activeReservation().equals(null));
        assertEquals(u.activeReservation(), null);
        assertTrue(added);
    }

    @Test
    public void incrementSeatAvailability() {
        Building b = new Building();
        HashMap<String, ArrayList<Integer>> avail = new HashMap<>();
        // Creating ArrayList<Integer> with five entries of value 20
        ArrayList<Integer> mondayAvailability = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mondayAvailability.add(20);
        }
        // Add entry to the HashMap
        avail.put("MONDAY", mondayAvailability);
        b.setAvailability(avail);
        b.addSeat(Weekday.MONDAY, 3);
        assertTrue(b.getAvailability().containsKey("MONDAY"));
        ArrayList<Integer> innerarr = b.getAvailability().get("MONDAY");
        assertEquals(Optional.ofNullable(innerarr.get(3)), 21); //debug
    }

    @Test
    public void decrementSeatAvailability() {
        Building b = new Building();
        HashMap<String, ArrayList<Integer>> avail = new HashMap<>();
        // Creating ArrayList<Integer> with five entries of value 20
        ArrayList<Integer> mondayAvailability = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mondayAvailability.add(20);
        }
        // Add entry to the HashMap
        avail.put("MONDAY", mondayAvailability);
        b.setAvailability(avail);
        b.removeSeat(Weekday.MONDAY, 3);
        assertTrue(b.getAvailability().containsKey("MONDAY"));
        ArrayList<Integer> innerarr = b.getAvailability().get("MONDAY");
        assertEquals(Optional.ofNullable(innerarr.get(3)), 19); //debug
    }

    @Test
    public void createNonConsecutiveReservation() {
        //Reservation r = new Reservation();
        HashSet<Integer> resCart = new HashSet<>();
        Date d = new Date(2023, 12, 2 , Weekday.SATURDAY);

        // Adding non-consecutive integers
        resCart.add(5);
        resCart.add(7);
        resCart.add(8);

        assertEquals(Reservation.createReservation(d, 1,10, resCart), null);
    }

    @Test
    public void createTooBigReservation() {
        Reservation r = new Reservation();
        HashSet<Integer> resCart = new HashSet<>();
        Date d = new Date(2023, 12, 2 , Weekday.SATURDAY);

        // Adding too many integers
        resCart.add(5);
        resCart.add(6);
        resCart.add(7);
        resCart.add(8);
        resCart.add(9);

        assertEquals(Reservation.createReservation(d, 1, 10, resCart), null);
    }

    @Test
    public void createValidReservation() {
        //Reservation r = new Reservation();
        HashSet<Integer> resCart = new HashSet<>();
        Date d = new Date(2023, 12, 2 , Weekday.SATURDAY);

        // Adding too many integers
        resCart.add(5);
        resCart.add(6);
        resCart.add(7);
        resCart.add(8);

        /*Reservation fullRes = r.createReservation(1, 10, resCart);
        assertEquals(fullRes.getEndTime(), 19);
        assertEquals(fullRes.getEndTime(), 18);*/
        //assertEquals(Reservation.createReservation(1, 10, resCart), null);
        Reservation fullRes = Reservation.createReservation(d, 1, 10, resCart);
        assertEquals(fullRes.getStartTime(), 15);
        assertEquals(fullRes.getEndTime(), 19);
    }







}