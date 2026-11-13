package com.parking.domain.model;

import com.parking.domain.enums.*;
import java.math.BigDecimal;
import java.util.*;

public class Transaction {
  private String id; private TransactionType type; private TxStatus status = TxStatus.PENDING; public String getId(){return id;} public void setId(String id){this.id=id;} public TransactionType getType(){return type;} public void setType(TransactionType type){this.type=type;} public TxStatus getStatus(){return status;} public void setStatus(TxStatus status){this.status=status;}
}
