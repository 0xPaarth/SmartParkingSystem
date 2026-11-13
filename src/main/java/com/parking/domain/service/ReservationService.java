package com.parking.domain.service;

import com.parking.domain.enums.ReservationStatus;
import com.parking.domain.exception.ReservationConflictException;
import com.parking.domain.model.Reservation;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ReservationService {
  public Reservation create(List<Reservation> reservations, String driverId, String slotId) {
    Objects.requireNonNull(reservations, "reservations must not be null");
    if (driverId == null || driverId.isBlank() || slotId == null || slotId.isBlank()) {
      throw new IllegalArgumentException("driver and slot are required");
    }
    ensureSlotAvailable(reservations, slotId);
    Reservation reservation = new Reservation();
    reservation.setId("reservation-" + UUID.randomUUID());
    reservation.setDriverId(driverId.trim());
    reservation.setSlotId(slotId);
    reservation.setStatus(ReservationStatus.CONFIRMED);
    reservations.add(reservation);
    return reservation;
  }

  public void ensureSlotAvailable(List<Reservation> reservations, String slotId) {
    Objects.requireNonNull(reservations, "reservations must not be null");
    if (slotId == null || slotId.isBlank()) throw new IllegalArgumentException("slot is required");
    boolean conflict = reservations.stream()
        .filter(Objects::nonNull)
        .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
        .anyMatch(reservation -> slotId.equals(reservation.getSlotId()));
    if (conflict) throw new ReservationConflictException("Slot already has a confirmed reservation: " + slotId);
  }
}
