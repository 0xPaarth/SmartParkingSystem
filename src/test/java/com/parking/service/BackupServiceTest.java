package com.parking.service;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BackupServiceTest {
  @Test void restoresCompleteSeedState() throws Exception {
    Path file = Files.createTempFile("parking-backup", ".json");
    try {
      PersistenceState original = new SeedDataLoader().load();
      BackupService service = new BackupService();
      service.exportState(original, file);
      PersistenceState restored = service.importState(file);
      assertEquals(3, restored.getParkingLot().getLevels().size());
      assertEquals(5, restored.getSessions().size());
      assertEquals(20, restored.getTransactions().size());
      assertEquals("0.18", restored.getPricingConfig().getGstRate().toPlainString());
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
