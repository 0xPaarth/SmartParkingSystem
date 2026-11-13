package com.parking.domain.service;

import com.parking.domain.enums.Role;
import com.parking.domain.enums.SlotState;
import com.parking.domain.enums.SlotStatus;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.model.PricingConfig;
import com.parking.domain.model.Transaction;
import com.parking.domain.model.User;
import com.parking.repository.InMemoryRepositoryImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AdminService {
  private static final AdminService INSTANCE = new AdminService();
  private final InMemoryRepositoryImpl<ParkingSlot> slots = new InMemoryRepositoryImpl<>();
  private final InMemoryRepositoryImpl<User> users = new InMemoryRepositoryImpl<>();
  private final InMemoryRepositoryImpl<Transaction> transactions = new InMemoryRepositoryImpl<>();
  private final Set<String> blacklistedVehicles = new HashSet<>();
  private PricingConfig pricingConfig = PricingConfig.defaults();

  private AdminService() {}
  public static AdminService shared() { return INSTANCE; }

  public List<ParkingSlot> slots() { return slots.findAll(); }
  public List<User> users() { return users.findAll(); }
  public List<Transaction> transactions() { return transactions.findAll(); }
  public PricingConfig pricingConfig() { return pricingConfig; }
  public void updatePricingConfig(PricingConfig config) { pricingConfig = config; }
  public void saveSlot(ParkingSlot slot) { slots.save(slot); }
  public void deleteSlot(String id) { slots.deleteById(id); }
  public void setMaintenance(ParkingSlot slot, boolean maintenance) {
    slot.setStatus(maintenance ? SlotStatus.INACTIVE : SlotStatus.ACTIVE);
    slot.setState(maintenance ? SlotState.OUT_OF_SERVICE : SlotState.AVAILABLE);
  }
  public void importSlots(Reader source) {
    try (BufferedReader reader = new BufferedReader(source)) {
      String line;
      boolean first = true;
      while ((line = reader.readLine()) != null) {
        if (first) { first = false; if (line.toLowerCase().contains("id")) continue; }
        String[] parts = line.split(",");
        if (parts.length >= 2 && !parts[0].isBlank()) {
          ParkingSlot slot = new ParkingSlot(parts[0].trim(),
              com.parking.domain.enums.SlotType.valueOf(parts[1].trim().toUpperCase()));
          saveSlot(slot);
        }
      }
    } catch (IOException | IllegalArgumentException exception) {
      throw new IllegalArgumentException("Invalid slot CSV", exception);
    }
  }
  public void saveUser(User user) { users.save(user); }
  public void changeRole(User user, Role role) { user.setRole(role); }
  public boolean isBlacklisted(String plate) { return blacklistedVehicles.contains(plate); }
  public void setBlacklisted(String plate, boolean blacklisted) {
    if (blacklisted) blacklistedVehicles.add(plate); else blacklistedVehicles.remove(plate);
  }
  public Set<String> blacklistedVehicles() { return Set.copyOf(blacklistedVehicles); }
  public BigDecimal revenue() {
    return transactions().stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
  }
  public long occupiedSlots() {
    return slots().stream().filter(slot -> slot.getState() == SlotState.OCCUPIED).count();
  }
  public long availableSlots() {
    return slots().stream().filter(slot -> slot.getState() == SlotState.AVAILABLE).count();
  }
}
