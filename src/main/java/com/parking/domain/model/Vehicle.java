package com.parking.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehicle {
  private String plateNumber;
  private com.parking.domain.enums.VehicleType type = com.parking.domain.enums.VehicleType.CAR;
  public Vehicle() {}
  @JsonCreator
  public Vehicle(@JsonProperty("plateNumber") String plateNumber,
      @JsonProperty("type") com.parking.domain.enums.VehicleType type) {
    this.plateNumber = plateNumber;
    this.type = type == null ? com.parking.domain.enums.VehicleType.CAR : type;
  }
  public String getPlateNumber(){return plateNumber;}
  public void setPlateNumber(String plateNumber){this.plateNumber=plateNumber;}
  public com.parking.domain.enums.VehicleType getType(){return type;}
  public void setType(com.parking.domain.enums.VehicleType type){this.type=type;}
}
