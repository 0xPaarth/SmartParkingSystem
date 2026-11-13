package com.parking.domain.service;
import com.parking.domain.model.ParkingSession;
import java.math.BigDecimal;
public final class LoyaltyRedemptionStrategy implements PricingStrategy {
  @Override public BigDecimal calculate(ParkingSession s) {
    int points = s.getLoyaltyPointsToRedeem();
    if (points == 0 || s.getUser() == null || s.getUser().getLoyaltyAccount() == null) return s.getCurrentAmount();
    int redeemable = Math.min(points, s.getCurrentAmount().intValue());
    int actual = s.getUser().getLoyaltyAccount().redeem(redeemable);
    return s.getCurrentAmount().subtract(BigDecimal.valueOf(actual));
  }
}
