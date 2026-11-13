package com.parking.domain.model;

import com.parking.domain.enums.SlotType;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class PricingConfig {
  private Map<SlotType, BigDecimal> hourlyRates = new EnumMap<>(SlotType.class);
  private BigDecimal surgeRate = new BigDecimal("0.25");
  private BigDecimal peakHourRate = new BigDecimal("0.15");
  private BigDecimal weekendRate = new BigDecimal("0.10");
  private BigDecimal electricDiscount = new BigDecimal("0.10");
  private BigDecimal reservationFee = new BigDecimal("20.00");
  private BigDecimal gstRate = new BigDecimal("0.18");

  public PricingConfig() {
    hourlyRates.put(SlotType.COMPACT, new BigDecimal("30.00"));
    hourlyRates.put(SlotType.STANDARD, new BigDecimal("50.00"));
    hourlyRates.put(SlotType.LARGE, new BigDecimal("70.00"));
    hourlyRates.put(SlotType.ACCESSIBLE, new BigDecimal("50.00"));
    hourlyRates.put(SlotType.ELECTRIC, new BigDecimal("60.00"));
  }
  public static PricingConfig defaults() { return new PricingConfig(); }
  public static PricingConfig defaultConfig() { return defaults(); }
  public Map<SlotType, BigDecimal> getHourlyRates() { return hourlyRates; }
  public void setHourlyRates(Map<SlotType, BigDecimal> hourlyRates) { this.hourlyRates = hourlyRates; }
  public BigDecimal getSurgeRate() { return surgeRate; } public void setSurgeRate(BigDecimal v) { surgeRate=v; }
  public BigDecimal getPeakHourRate() { return peakHourRate; } public void setPeakHourRate(BigDecimal v) { peakHourRate=v; }
  public BigDecimal getWeekendRate() { return weekendRate; } public void setWeekendRate(BigDecimal v) { weekendRate=v; }
  public BigDecimal getElectricDiscount() { return electricDiscount; } public void setElectricDiscount(BigDecimal v) { electricDiscount=v; }
  public BigDecimal getReservationFee() { return reservationFee; } public void setReservationFee(BigDecimal v) { reservationFee=v; }
  public BigDecimal getGstRate() { return gstRate; } public void setGstRate(BigDecimal v) { gstRate=v; }
}
