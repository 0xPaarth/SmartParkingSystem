package com.parking.domain.model;

public class Vehicle {
  private String plateNumber;
  private com.parking.domain.enums.VehicleType type = com.parking.domain.enums.VehicleType.CAR;
  public Vehicle() {}
  public Vehicle(String plateNumber, com.parking.domain.enums.VehicleType type) { this.plateNumber = plateNumber; this.type = type; }
  public String getPlateNumber(){return plateNumber;}
  public void setPlateNumber(String plateNumber){this.plateNumber=plateNumber;}
  public com.parking.domain.enums.VehicleType getType(){return type;}
  public void setType(com.parking.domain.enums.VehicleType type){this.type=type;}
}
