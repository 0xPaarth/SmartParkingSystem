package com.parking.app.viewmodel;

import com.parking.domain.service.PricingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceBreakdownViewModelTest {
  @Test
  void exposesAllTenPricingSteps() {
    PriceBreakdownViewModel model = new PriceBreakdownViewModel(new PricingService());
    model.recalculate();
    assertEquals(10, model.items().size());
    assertEquals("Tax", model.items().get(9).name());
  }
}
