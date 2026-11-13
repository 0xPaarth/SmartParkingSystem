package com.parking.domain.service;
import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class EvDiscountStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.10");
  @Override public BigDecimal calculate(ParkingSession s) {
    return s.getSlot() != null && s.getSlot().getType() == SlotType.ELECTRIC
        ? s.getCurrentAmount().multiply(BigDecimal.ONE.subtract(RATE)) : s.getCurrentAmount();
  }
}
