package com.parking.domain.state;

import com.parking.domain.exception.InvalidSlotStateException;
import com.parking.domain.model.ParkingSlot;

public interface SlotState {
  com.parking.domain.enums.SlotState value();

  default void occupy(ParkingSlot slot) {
    throw new InvalidSlotStateException("Slot cannot be occupied from " + value());
  }

  default void release(ParkingSlot slot) {
    throw new InvalidSlotStateException("Slot cannot be released from " + value());
  }

  default void takeOutOfService(ParkingSlot slot) {
    throw new InvalidSlotStateException("Slot cannot be taken out of service from " + value());
  }

  default void restore(ParkingSlot slot) {
    throw new InvalidSlotStateException("Slot cannot be restored from " + value());
  }
}
