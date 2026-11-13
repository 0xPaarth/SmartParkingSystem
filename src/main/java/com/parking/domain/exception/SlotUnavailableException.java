package com.parking.domain.exception;

public class SlotUnavailableException extends ParkingException {
  public SlotUnavailableException(String message) { super(message); }
  public SlotUnavailableException(String message, Throwable cause) { super(message, cause); }
}
