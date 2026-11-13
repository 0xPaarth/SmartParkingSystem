package com.parking.domain.service;

import com.parking.domain.model.AuditEntry;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class AuditService {
  private static final AuditService INSTANCE = new AuditService();
  private final List<AuditEntry> entries = new ArrayList<>();
  private AuditService() {}
  public static AuditService shared() { return INSTANCE; }
  public List<AuditEntry> entries() { return List.copyOf(entries); }
  public void record(String actor, String action, String target, String details) {
    entries.add(new AuditEntry(Instant.now(), actor, action, target, details));
  }
}
