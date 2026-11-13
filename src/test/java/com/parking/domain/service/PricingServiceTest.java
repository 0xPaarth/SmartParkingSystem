package com.parking.domain.service;

import com.parking.domain.enums.SlotType;
import com.parking.domain.model.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {
  private ParkingSession session(SlotType type) {
    ParkingSession session = new ParkingSession();
    session.setSlot(new ParkingSlot("A-1", type));
    session.setEntryTime(Instant.now().minus(2, ChronoUnit.HOURS));
    session.setExitTime(Instant.now());
    return session;
  }

  @Test void usesLockedBaseRateAndGstAsSeparateLine() {
    PricingResult result = new PricingService().calculate(session(SlotType.STANDARD));
    assertEquals("118.00", result.getTotal().toPlainString());
    assertEquals("100.00", result.lineItem("BaseRate").toPlainString());
    assertEquals("18.00", result.lineItem("Tax").toPlainString());
    assertEquals(10, result.getLineItems().size());
  }

  @Test void appliesSurgePeakWeekendAndEvDiscount() {
    ParkingSession session = session(SlotType.ELECTRIC);
    session.setSurge(true);
    session.setPeakHour(true);
    session.setWeekend(true);
    PricingResult result = new PricingService().calculate(session);
    assertEquals("219.83", result.getTotal().toPlainString());
    assertTrue(result.lineItem("EvDiscount").signum() < 0);
  }

  @Test void appliesReservationPromoLoyaltyAndOverstayGrace() {
    ParkingSession session = session(SlotType.STANDARD);
    session.setReserved(true);
    session.setPromoCode("SAVE10");
    session.setOverstayMinutes(16);
    session.setGraceMinutes(15);
    User user = new User();
    user.setLoyaltyAccount(new LoyaltyAccount("member", 20));
    session.setUser(user);
    session.setLoyaltyPointsToRedeem(10);
    PricingResult result = new PricingService().calculate(session);
    assertEquals("233.64", result.getTotal().toPlainString());
    assertEquals("20.00", result.lineItem("ReservationFee").toPlainString());
    assertEquals("-10.00", result.lineItem("LoyaltyRedemption").toPlainString());
    assertEquals("100.00", result.lineItem("OverstayPenalty").toPlainString());
  }
}
