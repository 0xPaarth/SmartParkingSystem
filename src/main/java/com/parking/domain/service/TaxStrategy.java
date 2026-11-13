package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
public final class TaxStrategy implements PricingStrategy {
  public static final BigDecimal GST_RATE = new BigDecimal("0.18");
  private final BigDecimal rate;
  public TaxStrategy() { this(PricingConfig.defaults()); }
  public TaxStrategy(PricingConfig config) { rate = config.getGstRate(); }
  @Override public BigDecimal calculate(ParkingSession s) { return s.getCurrentAmount().multiply(BigDecimal.ONE.add(rate)); }
}
