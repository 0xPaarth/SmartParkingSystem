package com.parking.domain.state;

import com.parking.domain.model.ParkingSlot;

public final class OccupiedState implements SlotState {
  @Override public com.parking.domain.enums.SlotState value() { return com.parking.domain.enums.SlotState.OCCUPIED; }
  @Override public void release(ParkingSlot slot) { slot.setState(com.parking.domain.enums.SlotState.AVAILABLE); }
}
