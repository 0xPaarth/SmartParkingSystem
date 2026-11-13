package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class Reservation {
  private String id;
  private ReservationStatus status = ReservationStatus.PENDING;
  private String driverId;
  private String slotId;
  public String getId(){return id;} public void setId(String id){this.id=id;}
  public ReservationStatus getStatus(){return status;} public void setStatus(ReservationStatus status){this.status=status;}
  public String getDriverId(){return driverId;} public void setDriverId(String driverId){this.driverId=driverId;}
  public String getSlotId(){return slotId;} public void setSlotId(String slotId){this.slotId=slotId;}
}
