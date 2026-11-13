package com.parking.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLevel {
  private int levelNumber;
  private final List<ParkingSlot> slots = new ArrayList<>();

  public int getLevelNumber() {
    return levelNumber;
  }

  public void setLevelNumber(int levelNumber) {
    this.levelNumber = levelNumber;
  }

  public List<ParkingSlot> getSlots() {
    return slots;
  }
}
