package com.parking.domain.exception;

public class ReservationConflictException extends ParkingException {
  public ReservationConflictException(String message) { super(message); }
  public ReservationConflictException(String message, Throwable cause) { super(message, cause); }
}
