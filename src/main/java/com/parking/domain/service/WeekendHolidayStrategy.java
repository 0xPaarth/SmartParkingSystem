package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class WeekendHolidayStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.15");
  @Override public BigDecimal calculate(ParkingSession s) { return s.isWeekend() ? s.getCurrentAmount().multiply(BigDecimal.ONE.add(RATE)) : s.getCurrentAmount(); }
}
