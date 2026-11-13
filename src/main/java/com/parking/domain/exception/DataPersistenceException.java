package com.parking.domain.exception;

public class DataPersistenceException extends ParkingException {
  public DataPersistenceException(String message) { super(message); }
  public DataPersistenceException(String message, Throwable cause) { super(message, cause); }
}
