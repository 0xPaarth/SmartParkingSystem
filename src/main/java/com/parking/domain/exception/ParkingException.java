package com.parking.domain.exception;

public abstract class ParkingException extends RuntimeException {
  protected ParkingException(String message) { super(message); }
  protected ParkingException(String message, Throwable cause) { super(message, cause); }
}
