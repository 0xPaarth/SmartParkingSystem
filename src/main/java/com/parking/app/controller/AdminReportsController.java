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
  @FXML private ProgressIndicator progressIndicator;
  @FXML private Button csvButton;
  @FXML private Button pdfButton;
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
    if (file == null) return;
    final FileOutputStream output;
    try {
      output = new FileOutputStream(file);
    } catch (IOException exception) {
      com.parking.app.util.ErrorDialogs.show(auditTable.getScene().getWindow(), exception);
      return;
    }
    var task = viewModel.exportAsync(exporter, output);
    progressIndicator.visibleProperty().bind(task.runningProperty());
    csvButton.disableProperty().bind(task.runningProperty());
    pdfButton.disableProperty().bind(task.runningProperty());
    task.setOnSucceeded(event -> close(output));
    task.setOnFailed(event -> {
      close(output);
      com.parking.app.util.ErrorDialogs.show(auditTable.getScene().getWindow(), task.getException());
    });
    new Thread(task, "report-export").start();
  }
  private static void close(FileOutputStream output) {
    try { output.close(); } catch (IOException ignored) { }
  }
  @FXML private void back() { navigationManager.navigateTo(NavigationManager.ADMIN_DASHBOARD); }
}
