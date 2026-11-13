package com.parking.domain.service;

import com.parking.domain.export.DataExporter;
import com.parking.domain.model.AuditEntry;
import java.io.OutputStream;
import java.util.List;

public final class ReportService {
  private final AuditService auditService;
  public ReportService(AuditService auditService) { this.auditService = auditService; }
  public List<AuditEntry> auditEntries() { return auditService.entries(); }
  public void exportAudit(DataExporter exporter, OutputStream output) {
    exporter.export(auditEntries(), output);
  }
}
