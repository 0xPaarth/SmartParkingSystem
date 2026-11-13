package com.parking.app.viewmodel;

import com.parking.domain.enums.ReservationStatus;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.model.Reservation;
import com.parking.service.PersistenceState;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public final class ReservationViewModel {
  private final PersistenceState state;
  private final PriceBreakdownViewModel priceBreakdown = new PriceBreakdownViewModel();
  private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());
  private final ObjectProperty<LocalTime> time = new SimpleObjectProperty<>(LocalTime.now());
  private final StringProperty driverId = new SimpleStringProperty("");
  private final ObjectProperty<ParkingSlot> selectedSlot = new SimpleObjectProperty<>();
  private final ObservableList<ParkingSlot> availableSlots = FXCollections.observableArrayList();

  public ReservationViewModel(PersistenceState state) {
    this.state = Objects.requireNonNull(state);
    state.getSlots().stream().filter(slot -> slot.getState().name().equals("AVAILABLE")).forEach(availableSlots::add);
    selectedSlot.addListener((obs, oldValue, newValue) -> priceBreakdown.slotProperty().set(newValue));
    priceBreakdown.reservedProperty().set(true);
  }
  public ObjectProperty<LocalDate> dateProperty() { return date; }
  public ObjectProperty<LocalTime> timeProperty() { return time; }
  public StringProperty driverIdProperty() { return driverId; }
  public ObjectProperty<ParkingSlot> selectedSlotProperty() { return selectedSlot; }
  public ObservableList<ParkingSlot> availableSlots() { return availableSlots; }
  public PriceBreakdownViewModel priceBreakdown() { return priceBreakdown; }
  public Reservation confirm() {
    if (driverId.get().isBlank() || selectedSlot.get() == null) throw new IllegalStateException("Driver and slot are required");
    Reservation reservation = new Reservation();
    reservation.setId("reservation-" + UUID.randomUUID());
    reservation.setDriverId(driverId.get().trim());
    reservation.setSlotId(selectedSlot.get().getId());
    reservation.setStatus(ReservationStatus.CONFIRMED);
    state.getReservations().add(reservation);
    return reservation;
  }
}
