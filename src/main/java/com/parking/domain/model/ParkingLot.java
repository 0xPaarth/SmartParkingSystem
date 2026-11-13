package com.parking.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
  private String id;
  private String name;
  private final List<ParkingLevel> levels = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ParkingLevel> getLevels() {
    return levels;
  }
}
