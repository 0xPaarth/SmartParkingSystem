package com.parking.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parking.domain.enums.SlotType;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class AdminServiceTest {
  @Test
  void importsSlotsAndTogglesMaintenance() {
    AdminService service = AdminService.shared();
    service.importSlots(new StringReader("id,type\nphase7-test,compact\n"));
    var slot = service.slots().stream().filter(item -> item.getId().equals("phase7-test")).findFirst().orElseThrow();
    assertEquals(SlotType.COMPACT, slot.getType());
    service.setMaintenance(slot, true);
    assertTrue(slot.getStatus().name().equals("INACTIVE"));
  }
}
