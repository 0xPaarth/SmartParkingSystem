package com.parking.app.viewmodel;

import com.parking.domain.export.DataExporter;
import com.parking.domain.model.AuditEntry;
import com.parking.domain.service.ReportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import java.io.OutputStream;

public final class AuditReportsViewModel {
  private final ReportService service;
  private final ObservableList<AuditEntry> entries = FXCollections.observableArrayList();
  public AuditReportsViewModel(ReportService service) { this.service = service; refresh(); }
  public ObservableList<AuditEntry> entries() { return entries; }
  public void refresh() { entries.setAll(service.auditEntries()); }
  public void export(DataExporter exporter, OutputStream output) { service.exportAudit(exporter, output); }
  public Task<Void> exportAsync(DataExporter exporter, OutputStream output) {
    return new Task<>() {
      @Override protected Void call() {
        service.exportAudit(exporter, output);
        return null;
      }
    };
  }
}
