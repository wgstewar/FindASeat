package com.example.findaseat.Classes;

import static org.junit.Assert.*;

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
    public void addReservation_SingleReservation() {
        Date d = new Date(LocalDate.now());
        Reservation r = new Reservation(1, d, 0, 2);
        boolean added = u.addReservation(r);

        Reservation addedR = u.getReservations().get(0);
        assertTrue(r.equals(addedR));
        assertTrue(added);
    }

    public void addReservation_AlreadyActive() {
        Date d = new Date(LocalDate.now());
        Reservation r = new Reservation(1, d, 5, 10);
        boolean added = u.addReservation(r);

        Reservation addedR = u.getReservations().get(0);
        assertFalse(r.equals(addedR));
        assertFalse(added);
    }

    @Test
    public void cancelActiveReservation() {
    }

    @Test
    public void updateActiveReservation_BeforeReservation() {
    }

    @Test
    public void updateActiveReservation_DuringReservation() {
    }

    @Test
    public void updateActiveReservation_AfterReservation() {
    }

    @Test
    public void activeReservation() {
    }
}