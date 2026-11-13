package com.parking.domain.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

public class ParkingSession {
  private String id;
  private com.parking.domain.enums.SessionStatus status = com.parking.domain.enums.SessionStatus.ACTIVE;
  private Vehicle vehicle;
  private ParkingSlot slot;
  private User user;
  private Instant entryTime = Instant.now();
  private Instant exitTime;
  private boolean weekend;
  private boolean peakHour;
  private boolean surge;
  private boolean reserved;
  private String promoCode;
  private int loyaltyPointsToRedeem;
  private BigDecimal currentAmount = BigDecimal.ZERO;
  private long graceMinutes = 15;
  private long overstayMinutes;

  public String getId(){return id;} public void setId(String id){this.id=id;}
  public com.parking.domain.enums.SessionStatus getStatus(){return status;} public void setStatus(com.parking.domain.enums.SessionStatus status){this.status=status;}
  public Vehicle getVehicle(){return vehicle;} public void setVehicle(Vehicle vehicle){this.vehicle=vehicle;}
  public ParkingSlot getSlot(){return slot;} public void setSlot(ParkingSlot slot){this.slot=slot;}
  public User getUser(){return user;} public void setUser(User user){this.user=user;}
  public Instant getEntryTime(){return entryTime;} public void setEntryTime(Instant entryTime){this.entryTime=entryTime;}
  public Instant getExitTime(){return exitTime;} public void setExitTime(Instant exitTime){this.exitTime=exitTime;}
  public boolean isWeekend(){return weekend;} public void setWeekend(boolean weekend){this.weekend=weekend;}
  public boolean isPeakHour(){return peakHour;} public void setPeakHour(boolean peakHour){this.peakHour=peakHour;}
  public boolean isSurge(){return surge;} public void setSurge(boolean surge){this.surge=surge;}
  public boolean isReserved(){return reserved;} public void setReserved(boolean reserved){this.reserved=reserved;}
  public String getPromoCode(){return promoCode;} public void setPromoCode(String promoCode){this.promoCode=promoCode;}
  public int getLoyaltyPointsToRedeem(){return loyaltyPointsToRedeem;} public void setLoyaltyPointsToRedeem(int value){this.loyaltyPointsToRedeem=Math.max(0,value);}
  public BigDecimal getCurrentAmount(){return currentAmount;} public void setCurrentAmount(BigDecimal amount){this.currentAmount=amount == null ? BigDecimal.ZERO : amount;}
  public long getGraceMinutes(){return graceMinutes;} public void setGraceMinutes(long value){this.graceMinutes=Math.max(0,value);}
  public long getOverstayMinutes(){return overstayMinutes;} public void setOverstayMinutes(long value){this.overstayMinutes=Math.max(0,value);}
  public long durationMinutes() {
    Instant end = exitTime == null ? Instant.now() : exitTime;
    return Math.max(1, Duration.between(entryTime, end).toMinutes());
  }
}
