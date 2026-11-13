package com.parking.domain.exception;

public class NotFoundException extends ParkingException {
  public NotFoundException(String message) { super(message); }
  public NotFoundException(String message, Throwable cause) { super(message, cause); }
}
