package com.parking.domain.service;

import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BaseRateStrategy implements PricingStrategy {
  public static final BigDecimal HOURLY_COMPACT = new BigDecimal("30.00");
  public static final BigDecimal HOURLY_STANDARD = new BigDecimal("50.00");
  public static final BigDecimal HOURLY_LARGE = new BigDecimal("70.00");
  public static final BigDecimal HOURLY_ACCESSIBLE = new BigDecimal("50.00");
  public static final BigDecimal HOURLY_ELECTRIC = new BigDecimal("60.00");

  @Override public BigDecimal calculate(ParkingSession session) {
    SlotType type = session.getSlot() == null ? SlotType.STANDARD : session.getSlot().getType();
    BigDecimal hourly = switch (type) {
      case COMPACT -> HOURLY_COMPACT; case STANDARD -> HOURLY_STANDARD;
      case LARGE -> HOURLY_LARGE; case ACCESSIBLE -> HOURLY_ACCESSIBLE;
      case ELECTRIC -> HOURLY_ELECTRIC;
    };
    long hours = Math.max(1, (session.durationMinutes() + 59) / 60);
    return hourly.multiply(BigDecimal.valueOf(hours)).setScale(2, RoundingMode.HALF_UP);
  }
}
