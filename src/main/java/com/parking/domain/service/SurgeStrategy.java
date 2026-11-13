package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
public final class SurgeStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.25");
  private final BigDecimal rate;
  public SurgeStrategy() { this(PricingConfig.defaults()); }
  public SurgeStrategy(PricingConfig config) { rate = config.getSurgeRate(); }
  @Override public BigDecimal calculate(ParkingSession s) { return s.isSurge() ? s.getCurrentAmount().multiply(BigDecimal.ONE.add(rate)) : s.getCurrentAmount(); }
}
