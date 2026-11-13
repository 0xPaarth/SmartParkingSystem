package com.parking.domain.state;

import com.parking.domain.exception.InvalidSlotStateException;
import com.parking.domain.model.ParkingSlot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SlotStateTest {
  @Test void slotFollowsValidLifecycle() {
    ParkingSlot slot = new ParkingSlot("A-1", com.parking.domain.enums.SlotType.STANDARD);
    slot.occupy();
    assertEquals(com.parking.domain.enums.SlotState.OCCUPIED, slot.getState());
    slot.release();
    assertEquals(com.parking.domain.enums.SlotState.AVAILABLE, slot.getState());
    slot.takeOutOfService();
    assertEquals(com.parking.domain.enums.SlotState.OUT_OF_SERVICE, slot.getState());
    slot.restore();
    assertEquals(com.parking.domain.enums.SlotState.AVAILABLE, slot.getState());
  }

  @Test void invalidTransitionIsRejected() {
    ParkingSlot slot = new ParkingSlot("A-1", com.parking.domain.enums.SlotType.STANDARD);
    slot.takeOutOfService();
    assertThrows(InvalidSlotStateException.class, slot::occupy);
    assertThrows(InvalidSlotStateException.class, slot::release);
  }
}
