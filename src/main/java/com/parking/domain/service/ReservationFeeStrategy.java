package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
public final class ReservationFeeStrategy implements PricingStrategy {
  public static final BigDecimal FEE = new BigDecimal("20.00");
  private final BigDecimal fee;
  public ReservationFeeStrategy() { this(PricingConfig.defaults()); }
  public ReservationFeeStrategy(PricingConfig config) { fee = config.getReservationFee(); }
  @Override public BigDecimal calculate(ParkingSession s) { return s.isReserved() ? s.getCurrentAmount().add(fee) : s.getCurrentAmount(); }
}
