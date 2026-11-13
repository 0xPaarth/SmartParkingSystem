package com.parking.domain.service;
import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
public final class EvDiscountStrategy implements PricingStrategy {
  public static final BigDecimal RATE = new BigDecimal("0.10");
  private final BigDecimal rate;
  public EvDiscountStrategy() { this(PricingConfig.defaults()); }
  public EvDiscountStrategy(PricingConfig config) { rate = config.getElectricDiscount(); }
  @Override public BigDecimal calculate(ParkingSession s) {
    return s.getSlot() != null && s.getSlot().getType() == SlotType.ELECTRIC
        ? s.getCurrentAmount().multiply(BigDecimal.ONE.subtract(rate)) : s.getCurrentAmount();
  }
}
