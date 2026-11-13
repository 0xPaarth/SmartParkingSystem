package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.SlotManagementViewModel;
import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.service.AdminService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.FileReader;
import java.io.IOException;

public final class AdminSlotManagementController extends BaseController {
  @FXML private TableView<ParkingSlot> slotsTable;
  @FXML private TableColumn<ParkingSlot, String> idColumn;
  @FXML private TableColumn<ParkingSlot, String> typeColumn;
  @FXML private TableColumn<ParkingSlot, String> stateColumn;
  @FXML private TableColumn<ParkingSlot, String> statusColumn;
  private final SlotManagementViewModel viewModel = new SlotManagementViewModel(AdminService.shared());
  public AdminSlotManagementController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    idColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
    typeColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType().name()));
    stateColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getState().name()));
    statusColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus().name()));
    slotsTable.setItems(viewModel.slots());
  }
  @FXML private void addSlot() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setHeaderText("Slot ID");
    dialog.showAndWait().filter(value -> !value.isBlank())
        .ifPresent(value -> viewModel.save(new ParkingSlot(value, SlotType.STANDARD)));
  }
  @FXML private void deleteSlot() {
    ParkingSlot selected = slotsTable.getSelectionModel().getSelectedItem();
    if (selected != null) viewModel.delete(selected);
  }
  @FXML private void toggleMaintenance() {
    ParkingSlot selected = slotsTable.getSelectionModel().getSelectedItem();
    if (selected != null) viewModel.setMaintenance(selected, selected.getStatus().name().equals("ACTIVE"));
  }
  @FXML private void importCsv() {
    FileChooser chooser = new FileChooser();
    chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
    var file = chooser.showOpenDialog(slotsTable.getScene().getWindow());
    if (file != null) try (FileReader reader = new FileReader(file)) {
      viewModel.importCsv(reader);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to import slots", exception);
    }
  }
  @FXML private void back() { navigationManager.navigateTo(NavigationManager.ADMIN_DASHBOARD); }
}
