package com.parking.domain.state;

public final class SlotStates {
  private SlotStates() {}

  public static SlotState of(com.parking.domain.enums.SlotState state) {
    return switch (state) {
      case AVAILABLE -> new AvailableState();
      case OCCUPIED -> new OccupiedState();
      case OUT_OF_SERVICE -> new OutOfServiceState();
    };
  }
}
