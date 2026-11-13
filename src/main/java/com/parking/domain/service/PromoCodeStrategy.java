package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class PromoCodeStrategy implements PricingStrategy {
  public static final BigDecimal PERCENT = new BigDecimal("0.10");
  @Override public BigDecimal calculate(ParkingSession s) {
    if (s.getPromoCode() == null || s.getPromoCode().isBlank()) return s.getCurrentAmount();
    if ("FLAT50".equalsIgnoreCase(s.getPromoCode())) return s.getCurrentAmount().subtract(new BigDecimal("50.00")).max(BigDecimal.ZERO);
    if ("SAVE10".equalsIgnoreCase(s.getPromoCode())) return s.getCurrentAmount().multiply(BigDecimal.ONE.subtract(PERCENT));
    return s.getCurrentAmount();
  }
}
