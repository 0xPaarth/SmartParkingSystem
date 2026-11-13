package com.parking.domain.model;

public class User {
  private Wallet wallet = new Wallet();
  private LoyaltyAccount loyaltyAccount = new LoyaltyAccount();

  public Wallet getWallet() {
    return wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public LoyaltyAccount getLoyaltyAccount() {
    return loyaltyAccount;
  }

  public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
    this.loyaltyAccount = loyaltyAccount;
  }
}
