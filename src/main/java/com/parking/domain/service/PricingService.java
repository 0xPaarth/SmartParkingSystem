package com.parking.domain.service;

import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.PricingConfig;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PricingService {
  private final List<PricingStrategy> strategies;

  public PricingService() { this(PricingConfig.defaults()); }
  public PricingService(PricingConfig config) {
    strategies = List.of(new BaseRateStrategy(config), new SurgeStrategy(config), new PeakHourStrategy(config),
        new WeekendHolidayStrategy(config), new EvDiscountStrategy(config), new ReservationFeeStrategy(config),
        new PromoCodeStrategy(), new LoyaltyRedemptionStrategy(), new OverstayPenaltyStrategy(),
        new TaxStrategy(config));
  }

  public List<PricingStrategy> getStrategies() { return strategies; }

  public PricingResult calculate(ParkingSession session) {
    if (session == null) throw new IllegalArgumentException("session must not be null");
    BigDecimal amount = BigDecimal.ZERO;
    List<PricingLineItem> items = new ArrayList<>();
    for (PricingStrategy strategy : strategies) {
      session.setCurrentAmount(amount);
      BigDecimal next = strategy.calculate(session).setScale(2, RoundingMode.HALF_UP);
      items.add(new PricingLineItem(strategy.getClass().getSimpleName().replace("Strategy", ""), next.subtract(amount)));
      amount = next;
    }
    session.setCurrentAmount(amount);
    return new PricingResult(amount, items);
  }

  public PricingResult quote(ParkingSession session) { return calculate(session); }
}
