package com.parking.app.util;

import com.parking.domain.exception.SlotUnavailableException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationErrorHandlerTest {
  @Test void mapsDomainExceptionsToFriendlyMessages() {
    assertEquals("The selected parking slot is no longer available.",
        ValidationErrorHandler.message(new SlotUnavailableException("slot-1")));
  }
}
