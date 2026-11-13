package com.parking.domain.service;

import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BaseRateStrategy implements PricingStrategy {
  public static final BigDecimal HOURLY_COMPACT = new BigDecimal("30.00");
  public static final BigDecimal HOURLY_STANDARD = new BigDecimal("50.00");
  public static final BigDecimal HOURLY_LARGE = new BigDecimal("70.00");
  public static final BigDecimal HOURLY_ACCESSIBLE = new BigDecimal("50.00");
  public static final BigDecimal HOURLY_ELECTRIC = new BigDecimal("60.00");
  private final PricingConfig config;

  public BaseRateStrategy() { this(PricingConfig.defaults()); }
  public BaseRateStrategy(PricingConfig config) {
    if (config == null || config.getHourlyRates() == null) {
      throw new IllegalArgumentException("pricing config and hourly rates must not be null");
    }
    this.config = config;
  }

  @Override public BigDecimal calculate(ParkingSession session) {
    SlotType type = session.getSlot() == null ? SlotType.STANDARD : session.getSlot().getType();
    BigDecimal hourly = config.getHourlyRates().getOrDefault(type, HOURLY_STANDARD);
    long hours = Math.max(1, (session.durationMinutes() + 59) / 60);
    return hourly.multiply(BigDecimal.valueOf(hours)).setScale(2, RoundingMode.HALF_UP);
  }
}
