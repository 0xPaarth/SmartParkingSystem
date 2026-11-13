package com.parking.domain.model;

public class ParkingSlot {
  private String id;
  private com.parking.domain.enums.SlotType type = com.parking.domain.enums.SlotType.STANDARD;
  private com.parking.domain.enums.SlotState state = com.parking.domain.enums.SlotState.AVAILABLE;
  private com.parking.domain.enums.SlotStatus status = com.parking.domain.enums.SlotStatus.ACTIVE;

  public ParkingSlot() {}
  public ParkingSlot(String id, com.parking.domain.enums.SlotType type) { this.id = id; this.type = type; }
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public com.parking.domain.enums.SlotType getType() { return type; }
  public void setType(com.parking.domain.enums.SlotType type) { this.type = type; }
  public com.parking.domain.enums.SlotState getState() { return state; }
  public void setState(com.parking.domain.enums.SlotState state) { this.state = state; }
  public com.parking.domain.enums.SlotStatus getStatus() { return status; }
  public void setStatus(com.parking.domain.enums.SlotStatus status) { this.status = status; }
  public com.parking.domain.state.SlotState stateObject() { return com.parking.domain.state.SlotStates.of(state); }
  public void occupy() { stateObject().occupy(this); }
  public void release() { stateObject().release(this); }
  public void takeOutOfService() { stateObject().takeOutOfService(this); }
  public void restore() { stateObject().restore(this); }
}
