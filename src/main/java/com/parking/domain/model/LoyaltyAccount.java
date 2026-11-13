package com.parking.domain.model;

public class LoyaltyAccount {
  private String memberId;
  private int points;
  public LoyaltyAccount() {}
  public LoyaltyAccount(String memberId, int points) { this.memberId = memberId; this.points = points; }
  public String getMemberId(){return memberId;}
  public void setMemberId(String memberId){this.memberId=memberId;}
  public int getPoints(){return points;}
  public void setPoints(int points){this.points=Math.max(0, points);}
  public int redeem(int requested) {
    int redeemed = Math.min(Math.max(0, requested), points);
    points -= redeemed;
    return redeemed;
  }
}
