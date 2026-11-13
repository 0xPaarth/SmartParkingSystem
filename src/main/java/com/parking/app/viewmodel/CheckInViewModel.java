package com.parking.app.viewmodel;

import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.model.Reservation;
import com.parking.service.PersistenceState;
import javafx.beans.property.*;

import java.util.Objects;
import java.util.UUID;

public final class CheckInViewModel {
  private final PersistenceState state;
  private final StringProperty lookup = new SimpleStringProperty("");
  private final ObjectProperty<Reservation> reservation = new SimpleObjectProperty<>();
  private final ObjectProperty<ParkingSlot> suggestedSlot = new SimpleObjectProperty<>();
  private final StringProperty ticket = new SimpleStringProperty("");

  public CheckInViewModel(PersistenceState state) {
    this.state = Objects.requireNonNull(state);
    lookup.addListener((obs, oldValue, newValue) -> find());
  }
  public StringProperty lookupProperty() { return lookup; }
  public ObjectProperty<Reservation> reservationProperty() { return reservation; }
  public ObjectProperty<ParkingSlot> suggestedSlotProperty() { return suggestedSlot; }
  public StringProperty ticketProperty() { return ticket; }
  public void find() {
    String key = lookup.get().trim();
    reservation.set(state.getReservations().stream().filter(item -> item.getId().equalsIgnoreCase(key)
        || state.getUsers().stream().anyMatch(user -> user.getId().equalsIgnoreCase(item.getDriverId())
        && (user.getName().equalsIgnoreCase(key) || user.getId().equalsIgnoreCase(key)))).findFirst().orElse(null));
    ParkingSession plateMatch = state.getSessions().stream()
        .filter(item -> item.getVehicle() != null && item.getVehicle().getPlateNumber() != null
            && item.getVehicle().getPlateNumber().equalsIgnoreCase(key)).findFirst().orElse(null);
    suggestedSlot.set(reservation.get() == null ? state.getSlots().stream()
        .filter(slot -> plateMatch == null && slot.getState().name().equals("AVAILABLE")).findFirst().orElse(null)
        : state.getSlots().stream().filter(slot -> slot.getId().equals(reservation.get().getSlotId())).findFirst().orElse(null));
    if (plateMatch != null) suggestedSlot.set(plateMatch.getSlot());
  }
  public String generateTicket() {
    if (suggestedSlot.get() == null) throw new IllegalStateException("No slot available");
    ticket.set("T-" + UUID.randomUUID());
    ParkingSession session = new ParkingSession();
    session.setId(ticket.get());
    session.setSlot(suggestedSlot.get());
    session.setReserved(reservation.get() != null);
    if (reservation.get() != null) session.setUser(state.getUsers().stream()
        .filter(user -> user.getId().equals(reservation.get().getDriverId())).findFirst().orElse(null));
    state.getSessions().add(session);
    suggestedSlot.get().occupy();
    return ticket.get();
  }
}
