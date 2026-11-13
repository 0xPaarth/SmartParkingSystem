package com.parking.domain.service;

import java.math.BigDecimal;
import com.parking.domain.model.ParkingSession;

public interface PricingStrategy { BigDecimal calculate(ParkingSession session); }
