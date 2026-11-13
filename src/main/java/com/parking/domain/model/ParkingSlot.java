package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class ParkingSlot {
  private String id; private SlotType type; private SlotState state = SlotState.AVAILABLE; private SlotStatus status = SlotStatus.ACTIVE; public String getId(){return id;} public void setId(String id){this.id=id;} public SlotType getType(){return type;} public void setType(SlotType type){this.type=type;} public SlotState getState(){return state;} public void setState(SlotState state){this.state=state;} public SlotStatus getStatus(){return status;} public void setStatus(SlotStatus status){this.status=status;}
}
