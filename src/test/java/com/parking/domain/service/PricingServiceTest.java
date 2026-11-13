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

  @Test void rejectsNegativeDuration() {
    ParkingSession session = session(SlotType.STANDARD);
    session.setExitTime(session.getEntryTime().minusSeconds(1));
    assertThrows(IllegalArgumentException.class, () -> new PricingService().calculate(session));
  }

  @Test void pricesMissingSlotAsStandardAndDoesNotChargeForZeroOccupancy() {
    ParkingSession session = new ParkingSession();
    session.setSlot(null);
    session.setEntryTime(Instant.now());
    session.setExitTime(session.getEntryTime());
    PricingResult result = new PricingService().calculate(session);
    assertEquals("59.00", result.getTotal().toPlainString());
  }

  @Test void capsElectricDiscountAtFreeInsteadOfProducingNegativePrice() {
    PricingConfig config = PricingConfig.defaults();
    config.setElectricDiscount(new java.math.BigDecimal("2.00"));
    ParkingSession session = session(SlotType.ELECTRIC);
    PricingResult result = new PricingService(config).calculate(session);
    assertEquals("0.00", result.lineItem("EvDiscount").toPlainString());
    assertEquals("0.00", result.getTotal().toPlainString());
  }

  @Test void quotingDoesNotRedeemLoyaltyPoints() {
    ParkingSession session = session(SlotType.STANDARD);
    User user = new User();
    user.setLoyaltyAccount(new LoyaltyAccount("member", 20));
    session.setUser(user);
    session.setLoyaltyPointsToRedeem(10);
    PricingService pricing = new PricingService();

    pricing.calculate(session);
    pricing.calculate(session);

    assertEquals(20, user.getLoyaltyAccount().getPoints());
  }

  @Test void loyaltyDiscountCannotExceedAvailablePoints() {
    ParkingSession session = session(SlotType.STANDARD);
    User user = new User();
    user.setLoyaltyAccount(new LoyaltyAccount("member", 2));
    session.setUser(user);
    session.setLoyaltyPointsToRedeem(100);

    PricingResult result = new PricingService().calculate(session);

    assertEquals("-2.00", result.lineItem("LoyaltyRedemption").toPlainString());
  }
}
