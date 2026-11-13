package com.parking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parking.domain.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenceState {
  private ParkingLot parkingLot;
  private List<User> users = new ArrayList<>();
  private List<ParkingSession> sessions = new ArrayList<>();
  private List<Reservation> reservations = new ArrayList<>();
  private List<Transaction> transactions = new ArrayList<>();
  private PricingConfig pricingConfig = PricingConfig.defaults();

  public ParkingLot getParkingLot() { return parkingLot; }
  public void setParkingLot(ParkingLot value) { parkingLot=value; }
  public List<User> getUsers() { return users; }
  public void setUsers(List<User> value) { users=value; }
  public List<ParkingSession> getSessions() { return sessions; }
  public void setSessions(List<ParkingSession> value) { sessions=value; }
  public List<Reservation> getReservations() { return reservations; }
  public void setReservations(List<Reservation> value) { reservations=value; }
  public List<Transaction> getTransactions() { return transactions; }
  public void setTransactions(List<Transaction> value) { transactions=value; }
  public PricingConfig getPricingConfig() { return pricingConfig; }
  public void setPricingConfig(PricingConfig value) { pricingConfig=value; }
  @JsonIgnore public List<ParkingSlot> getSlots() {
    return parkingLot == null ? List.of() : parkingLot.getLevels().stream()
        .flatMap(level -> level.getSlots().stream()).collect(Collectors.toList());
  }
  @JsonIgnore public List<User> getDrivers() { return users.stream().filter(u -> u.getRole() == com.parking.domain.enums.Role.DRIVER).toList(); }
  @JsonIgnore public List<User> getAttendants() { return users.stream().filter(u -> u.getRole() == com.parking.domain.enums.Role.ATTENDANT).toList(); }
  @JsonIgnore public List<User> getAdministrators() { return users.stream().filter(u -> u.getRole() == com.parking.domain.enums.Role.ADMINISTRATOR).toList(); }
}
