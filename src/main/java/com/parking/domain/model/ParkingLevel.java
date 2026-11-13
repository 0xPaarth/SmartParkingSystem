package com.parking.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class ParkingLevel {
  private int levelNumber;
  private List<ParkingSlot> slots = new ArrayList<>();

  public ParkingLevel() {}

  @JsonCreator
  public ParkingLevel(@JsonProperty("levelNumber") int levelNumber,
      @JsonProperty("slots") List<ParkingSlot> slots) {
    this.levelNumber = levelNumber;
    this.slots = slots == null ? new ArrayList<>() : new ArrayList<>(slots);
  }

  public int getLevelNumber() {
    return levelNumber;
  }

  public void setLevelNumber(int levelNumber) {
    this.levelNumber = levelNumber;
  }

  @JsonProperty("slots")
  public List<ParkingSlot> getSlots() {
    return slots;
  }

  @JsonProperty("slots")
  public void setSlots(List<ParkingSlot> slots) {
    this.slots = slots == null ? new ArrayList<>() : new ArrayList<>(slots);
  }
}
