package com.parking.domain.model;

import com.parking.domain.enums.Role;

public class User {
  private String id;
  private String name;
  private Role role = Role.DRIVER;
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
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
}
