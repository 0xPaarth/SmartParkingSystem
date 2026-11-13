package com.parking.app.viewmodel;

import com.parking.service.SeedDataLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlotMapViewModelTest {
  @Test
  void filtersByLevelAndType() {
    SlotMapViewModel model = new SlotMapViewModel(new SeedDataLoader().load());
    assertEquals(20, model.visibleSlots().size());
    model.levelProperty().set(2);
    model.slotTypeProperty().set("ELECTRIC");
    assertEquals(4, model.visibleSlots().size());
  }
}
