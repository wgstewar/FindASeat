package com.example.findaseat.Classes;

import static org.junit.Assert.*;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import junit.framework.TestCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest extends TestCase {
    private User u;
    @BeforeEach
    public void setUp() throws Exception {
        u = new User();
    }

    @Test
    public void addReservation_AddReservationBasic() {
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
        u.addReservation(r1);
        Reservation r2 = new Reservation(1, d, 5, 10);
        boolean added = u.addReservation(r2);

        Reservation addedR = u.getReservations().get(0);
        assertFalse(r2.equals(addedR));
        assertFalse(added);
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
}