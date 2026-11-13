package com.parking.domain.service;

import com.parking.domain.enums.ReservationStatus;
import com.parking.domain.exception.ReservationConflictException;
import com.parking.domain.model.Reservation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationServiceTest {
  @Test void rejectsConfirmedReservationForSameSlot() {
    Reservation existing = reservation("slot-1", ReservationStatus.CONFIRMED);
    List<Reservation> reservations = new ArrayList<>(List.of(existing));

    assertThrows(ReservationConflictException.class,
        () -> new ReservationService().create(reservations, "driver-2", "slot-1"));
  }

  @Test void permitsCancelledReservationForSameSlot() {
    List<Reservation> reservations = new ArrayList<>(
        List.of(reservation("slot-1", ReservationStatus.CANCELLED)));

    Reservation created = new ReservationService().create(reservations, "driver-2", "slot-1");

    assertEquals(ReservationStatus.CONFIRMED, created.getStatus());
    assertEquals(2, reservations.size());
  }

  private Reservation reservation(String slotId, ReservationStatus status) {
    Reservation reservation = new Reservation();
    reservation.setId("existing");
    reservation.setSlotId(slotId);
    reservation.setStatus(status);
    return reservation;
  }
}
