package com.parking.app.controller;

import com.parking.app.context.ParkingAppState;
import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.TransactionsViewModel;
import com.parking.domain.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.IOException;

public final class TransactionsController extends BaseController {
  private final TransactionsViewModel viewModel = new TransactionsViewModel(ParkingAppState.get());
  @FXML private TextField searchField;
  @FXML private TableView<Transaction> table;
  @FXML private Label statusLabel;
  public TransactionsController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    searchField.textProperty().bindBidirectional(viewModel.searchProperty());
    table.setItems(viewModel.filtered());
  }
  @FXML private void export() {
    FileChooser chooser = new FileChooser();
    chooser.setInitialFileName("transactions.csv");
    var file = chooser.showSaveDialog(table.getScene().getWindow());
    if (file != null) {
      try { viewModel.exportCsv(file.toPath()); statusLabel.setText("Exported " + file.getName()); }
      catch (IOException ex) { statusLabel.setText("Export failed: " + ex.getMessage()); }
    }
  }
}
