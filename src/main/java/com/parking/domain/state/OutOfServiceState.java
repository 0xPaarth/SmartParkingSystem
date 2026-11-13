package com.parking.domain.state;

import com.parking.domain.model.ParkingSlot;

public final class OutOfServiceState implements SlotState {
  @Override public com.parking.domain.enums.SlotState value() { return com.parking.domain.enums.SlotState.OUT_OF_SERVICE; }
  @Override public void restore(ParkingSlot slot) { slot.setState(com.parking.domain.enums.SlotState.AVAILABLE); }
}
