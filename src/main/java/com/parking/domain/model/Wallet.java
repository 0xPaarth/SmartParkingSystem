package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class Wallet {
  private BigDecimal balance = BigDecimal.ZERO; public BigDecimal getBalance(){return balance;} public void setBalance(BigDecimal balance){this.balance=balance;}
}
