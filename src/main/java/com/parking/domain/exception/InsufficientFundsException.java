package com.parking.domain.exception;

public class InsufficientFundsException extends ParkingException {
  public InsufficientFundsException(String message) { super(message); }
  public InsufficientFundsException(String message, Throwable cause) { super(message, cause); }
}
