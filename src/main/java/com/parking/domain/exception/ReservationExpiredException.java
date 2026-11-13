package com.parking.domain.exception;

public class ReservationExpiredException extends ParkingException {
  public ReservationExpiredException(String message) { super(message); }
  public ReservationExpiredException(String message, Throwable cause) { super(message, cause); }
}
