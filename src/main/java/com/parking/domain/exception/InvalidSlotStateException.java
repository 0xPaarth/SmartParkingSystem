package com.parking.domain.exception;

public class InvalidSlotStateException extends ParkingException {
  public InvalidSlotStateException(String message) { super(message); }
  public InvalidSlotStateException(String message, Throwable cause) { super(message, cause); }
}
