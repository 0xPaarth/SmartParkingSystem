package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class OverstayPenaltyStrategy implements PricingStrategy {
  public static final BigDecimal PER_HOUR = new BigDecimal("100.00");
  @Override public BigDecimal calculate(ParkingSession s) {
    long minutes = s.getOverstayMinutes();
    if (minutes <= s.getGraceMinutes()) return s.getCurrentAmount();
    long billableHours = (minutes - s.getGraceMinutes() + 59) / 60;
    return s.getCurrentAmount().add(PER_HOUR.multiply(BigDecimal.valueOf(billableHours)));
  }
}
