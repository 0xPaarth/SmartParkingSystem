package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class ParkingSession {
  private String id; private SessionStatus status = SessionStatus.ACTIVE; public String getId(){return id;} public void setId(String id){this.id=id;} public SessionStatus getStatus(){return status;} public void setStatus(SessionStatus status){this.status=status;}
}
