package com.parking.domain.exception;

public class AuthenticationException extends ParkingException {
  public AuthenticationException(String message) { super(message); }
  public AuthenticationException(String message, Throwable cause) { super(message, cause); }
}
