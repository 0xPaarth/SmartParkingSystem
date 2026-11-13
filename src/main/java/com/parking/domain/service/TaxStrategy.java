package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class TaxStrategy implements PricingStrategy {
  public static final BigDecimal GST_RATE = new BigDecimal("0.18");
  @Override public BigDecimal calculate(ParkingSession s) { return s.getCurrentAmount().multiply(BigDecimal.ONE.add(GST_RATE)); }
}
