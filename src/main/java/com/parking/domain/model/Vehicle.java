package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class Vehicle {
  private String plateNumber; private VehicleType type; public String getPlateNumber(){return plateNumber;} public void setPlateNumber(String plateNumber){this.plateNumber=plateNumber;} public VehicleType getType(){return type;} public void setType(VehicleType type){this.type=type;}
}
