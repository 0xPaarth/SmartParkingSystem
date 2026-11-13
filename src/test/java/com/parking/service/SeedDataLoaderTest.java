package com.parking.service;

import com.parking.domain.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeedDataLoaderTest {
  @Test void createsLockedCountsAndPricingDefaults() {
    PersistenceState state = new SeedDataLoader().load();
    assertEquals(3, state.getParkingLot().getLevels().size());
    assertEquals(60, state.getParkingLot().getLevels().stream().mapToInt(l -> l.getSlots().size()).sum());
    assertEquals(10, state.getUsers().stream().filter(u -> u.getRole() == Role.DRIVER).count());
    assertEquals(2, state.getUsers().stream().filter(u -> u.getRole() == Role.ATTENDANT).count());
    assertEquals(1, state.getUsers().stream().filter(u -> u.getRole() == Role.ADMINISTRATOR).count());
    assertEquals(5, state.getSessions().size());
    assertEquals(4, state.getReservations().size());
    assertEquals(20, state.getTransactions().size());
    assertEquals("50.00", state.getPricingConfig().getHourlyRates().get(com.parking.domain.enums.SlotType.STANDARD).toPlainString());
  }
}
