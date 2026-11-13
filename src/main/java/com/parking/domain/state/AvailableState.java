package com.parking.domain.state;

import com.parking.domain.model.ParkingSlot;

public final class AvailableState implements SlotState {
  @Override public com.parking.domain.enums.SlotState value() { return com.parking.domain.enums.SlotState.AVAILABLE; }
  @Override public void occupy(ParkingSlot slot) { slot.setState(com.parking.domain.enums.SlotState.OCCUPIED); }
  @Override public void takeOutOfService(ParkingSlot slot) { slot.setState(com.parking.domain.enums.SlotState.OUT_OF_SERVICE); }
}
