package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class LoyaltyRedemptionStrategy implements PricingStrategy {
  @Override public BigDecimal calculate(ParkingSession s) {
    int points = s.getLoyaltyPointsToRedeem();
    if (points == 0 || s.getUser() == null || s.getUser().getLoyaltyAccount() == null) return s.getCurrentAmount();
    int redeemable = Math.min(Math.min(points, s.getUser().getLoyaltyAccount().getPoints()),
        s.getCurrentAmount().intValue());
    return s.getCurrentAmount().subtract(BigDecimal.valueOf(redeemable));
  }
}
