package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
public final class PeakHourStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.20");
  private final BigDecimal rate;
  public PeakHourStrategy() { this(PricingConfig.defaults()); }
  public PeakHourStrategy(PricingConfig config) { rate = config.getPeakHourRate(); }
  @Override public BigDecimal calculate(ParkingSession s) { return s.isPeakHour() ? s.getCurrentAmount().multiply(BigDecimal.ONE.add(rate)) : s.getCurrentAmount(); }
}
