package com.parking.domain.exception;

public class AccountLockedException extends ParkingException {
  public AccountLockedException(String message) { super(message); }
  public AccountLockedException(String message, Throwable cause) { super(message, cause); }
}
