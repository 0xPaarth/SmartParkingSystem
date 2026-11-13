package com.parking.service;

import com.parking.domain.enums.*;
import com.parking.domain.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SeedDataLoader {
  public PersistenceState load() {
    PersistenceState state = new PersistenceState();
    ParkingLot lot = new ParkingLot();
    lot.setId("lot-main");
    lot.setName("Main Parking Facility");
    List<ParkingSlot> allSlots = new ArrayList<>();
    for (int levelNumber = 1; levelNumber <= 3; levelNumber++) {
      ParkingLevel level = new ParkingLevel();
      level.setLevelNumber(levelNumber);
      for (int slotNumber = 1; slotNumber <= 20; slotNumber++) {
        SlotType type = SlotType.values()[(slotNumber - 1) % SlotType.values().length];
        ParkingSlot slot = new ParkingSlot("L" + levelNumber + "-S" + slotNumber, type);
        level.getSlots().add(slot);
        allSlots.add(slot);
      }
      lot.getLevels().add(level);
    }
    state.setParkingLot(lot);

    List<User> users = new ArrayList<>();
    for (int i = 1; i <= 10; i++) users.add(user("driver-" + i, "Driver " + i, Role.DRIVER));
    users.add(user("attendant-1", "Attendant 1", Role.ATTENDANT));
    users.add(user("attendant-2", "Attendant 2", Role.ATTENDANT));
    users.add(user("admin-1", "Administrator", Role.ADMINISTRATOR));
    state.setUsers(users);

    List<ParkingSession> sessions = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      ParkingSession session = new ParkingSession();
      session.setId("session-" + i);
      session.setVehicle(new Vehicle("SEED-" + i, VehicleType.CAR));
      session.setSlot(allSlots.get(i - 1));
      session.getSlot().setState(SlotState.OCCUPIED);
      session.setUser(users.get(i - 1));
      session.setStatus(SessionStatus.ACTIVE);
      sessions.add(session);
    }
    state.setSessions(sessions);

    List<Reservation> reservations = new ArrayList<>();
    for (int i = 1; i <= 4; i++) {
      Reservation reservation = new Reservation();
      reservation.setId("reservation-" + i);
      reservation.setDriverId(users.get(i - 1).getId());
      reservation.setSlotId(allSlots.get(10 + i).getId());
      reservation.setStatus(ReservationStatus.CONFIRMED);
      reservations.add(reservation);
    }
    state.setReservations(reservations);

    List<Transaction> transactions = new ArrayList<>();
    for (int i = 1; i <= 20; i++) {
      Transaction transaction = new Transaction();
      transaction.setId("transaction-" + i);
      transaction.setType(TransactionType.PAYMENT);
      transaction.setStatus(TxStatus.COMPLETED);
      transaction.setAmount(new BigDecimal("100.00"));
      transactions.add(transaction);
    }
    state.setTransactions(transactions);
    state.setPricingConfig(PricingConfig.defaults());
    return state;
  }

  public PersistenceState loadSeedData() { return load(); }
  public PersistenceState create() { return load(); }

  private User user(String id, String name, Role role) {
    User user = new User();
    user.setId(id); user.setName(name); user.setRole(role);
    return user;
  }
}
