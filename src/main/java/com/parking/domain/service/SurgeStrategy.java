package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class SurgeStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.25");
  @Override public BigDecimal calculate(ParkingSession s) { return s.isSurge() ? s.getCurrentAmount().multiply(BigDecimal.ONE.add(RATE)) : s.getCurrentAmount(); }
}
