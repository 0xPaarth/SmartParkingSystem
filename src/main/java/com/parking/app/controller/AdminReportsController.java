package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.AuditReportsViewModel;
import com.parking.domain.export.CsvDataExporter;
import com.parking.domain.export.PdfDataExporter;
import com.parking.domain.model.AuditEntry;
import com.parking.domain.service.AuditService;
import com.parking.domain.service.ReportService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.FileOutputStream;
import java.io.IOException;

public final class AdminReportsController extends BaseController {
  @FXML private TableView<AuditEntry> auditTable;
  @FXML private TableColumn<AuditEntry, String> timestampColumn;
  @FXML private TableColumn<AuditEntry, String> actionColumn;
  @FXML private TableColumn<AuditEntry, String> targetColumn;
  @FXML private TableColumn<AuditEntry, String> detailsColumn;
  private final AuditReportsViewModel viewModel =
      new AuditReportsViewModel(new ReportService(AuditService.shared()));
  public AdminReportsController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    timestampColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimestamp().toString()));
    actionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAction()));
    targetColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTarget()));
    detailsColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDetails()));
    auditTable.setItems(viewModel.entries());
  }
  @FXML private void exportCsv() { export(new CsvDataExporter(), "csv", "*.csv"); }
  @FXML private void exportPdf() { export(new PdfDataExporter(), "pdf", "*.pdf"); }
  private void export(com.parking.domain.export.DataExporter exporter, String extension, String pattern) {
    FileChooser chooser = new FileChooser();
    chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extension.toUpperCase(), pattern));
    var file = chooser.showSaveDialog(auditTable.getScene().getWindow());
    if (file != null) try (FileOutputStream output = new FileOutputStream(file)) {
      viewModel.export(exporter, output);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to export report", exception);
    }
  }
  @FXML private void back() { navigationManager.navigateTo(NavigationManager.ADMIN_DASHBOARD); }
}
