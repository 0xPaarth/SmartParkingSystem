package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class ReservationFeeStrategy implements PricingStrategy {
  public static final BigDecimal FEE = new BigDecimal("20.00");
  @Override public BigDecimal calculate(ParkingSession s) { return s.isReserved() ? s.getCurrentAmount().add(FEE) : s.getCurrentAmount(); }
}
