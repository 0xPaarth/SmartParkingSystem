package com.parking.domain.exception;

public class InvalidPaymentException extends ParkingException {
  public InvalidPaymentException(String message) { super(message); }
  public InvalidPaymentException(String message, Throwable cause) { super(message, cause); }
}
