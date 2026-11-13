package com.parking.domain.payment;

import java.math.BigDecimal;
import com.parking.domain.enums.PaymentMethod;

public interface PaymentProcessor { boolean process(BigDecimal amount, PaymentMethod method); }
