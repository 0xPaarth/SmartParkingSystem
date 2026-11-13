package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class PeakHourStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.20");
  @Override public BigDecimal calculate(ParkingSession s) { return s.isPeakHour() ? s.getCurrentAmount().multiply(BigDecimal.ONE.add(RATE)) : s.getCurrentAmount(); }
}
