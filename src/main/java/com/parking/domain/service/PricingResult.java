package com.parking.domain.service;

import java.math.BigDecimal;
import java.util.List;

public final class PricingResult {
  private final BigDecimal total;
  private final List<PricingLineItem> lineItems;

  public PricingResult(BigDecimal total, List<PricingLineItem> lineItems) {
    this.total = total;
    this.lineItems = List.copyOf(lineItems);
  }
  public BigDecimal getTotal() { return total; }
  public List<PricingLineItem> getLineItems() { return lineItems; }
  public BigDecimal lineItem(String name) {
    return lineItems.stream().filter(item -> item.name().equals(name))
        .map(PricingLineItem::amount).findFirst().orElse(BigDecimal.ZERO);
  }
}
