package com.parking.app.util;

import com.parking.domain.exception.*;

public final class ValidationErrorHandler {
  private ValidationErrorHandler() {}

  public static String message(Throwable error) {
    Throwable cause = error;
    while (cause.getCause() != null && !(cause instanceof ParkingException)) cause = cause.getCause();
    if (cause instanceof ReservationConflictException) return "That reservation conflicts with an existing booking.";
    if (cause instanceof ReservationExpiredException) return "This reservation has expired.";
    if (cause instanceof SlotUnavailableException) return "The selected parking slot is no longer available.";
    if (cause instanceof InsufficientFundsException) return "There are not enough funds to complete this payment.";
    if (cause instanceof InvalidPaymentException) return "The payment details could not be accepted.";
    if (cause instanceof AuthenticationException) return "The credentials could not be verified.";
    if (cause instanceof AccountLockedException) return "This account is temporarily locked. Please try again later.";
    if (cause instanceof NotFoundException) return "The requested parking record could not be found.";
    if (cause instanceof ParkingException && cause.getMessage() != null) return cause.getMessage();
    if (cause instanceof IllegalStateException && cause.getMessage() != null) return cause.getMessage();
    return "Something went wrong. Please try again.";
  }
}
