package com.parking.domain.exception;

public class OverstayException extends ParkingException {
  public OverstayException(String message) { super(message); }
  public OverstayException(String message, Throwable cause) { super(message, cause); }
}
