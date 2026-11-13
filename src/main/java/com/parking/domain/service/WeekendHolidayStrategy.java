package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
public final class WeekendHolidayStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.15");
  private final BigDecimal rate;
  public WeekendHolidayStrategy() { this(PricingConfig.defaults()); }
  public WeekendHolidayStrategy(PricingConfig config) { rate = config.getWeekendRate(); }
  @Override public BigDecimal calculate(ParkingSession s) { return s.isWeekend() ? s.getCurrentAmount().multiply(BigDecimal.ONE.add(rate)) : s.getCurrentAmount(); }
}
