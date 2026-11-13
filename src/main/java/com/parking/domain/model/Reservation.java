package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class Reservation {
  private String id; private ReservationStatus status = ReservationStatus.PENDING; public String getId(){return id;} public void setId(String id){this.id=id;} public ReservationStatus getStatus(){return status;} public void setStatus(ReservationStatus status){this.status=status;}
}
