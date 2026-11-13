package com.parking.app.controller;

import com.parking.app.context.ParkingAppState;
import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.SlotMapViewModel;
import com.parking.domain.model.ParkingSlot;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public final class SlotMapController extends BaseController {
  private final SlotMapViewModel viewModel = new SlotMapViewModel(ParkingAppState.get());
  @FXML private ComboBox<String> levelBox;
  @FXML private ComboBox<String> typeBox;
  @FXML private ListView<ParkingSlot> slotList;
  @FXML private Label detailsLabel;

  public SlotMapController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    levelBox.setItems(viewModel.levels());
    typeBox.setItems(viewModel.slotTypes());
    levelBox.getSelectionModel().select(String.valueOf(viewModel.levelProperty().get()));
    typeBox.getSelectionModel().select(SlotMapViewModel.ALL);
    slotList.setItems(viewModel.visibleSlots());
    slotList.setCellFactory(list -> new ListCell<>() {
      @Override protected void updateItem(ParkingSlot item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : item.getId() + "  " + item.getType() + "  [" + item.getState() + "]");
      }
    });
    levelBox.valueProperty().addListener((o, a, b) -> viewModel.levelProperty().set(Integer.parseInt(b)));
    typeBox.valueProperty().addListener((o, a, b) -> viewModel.slotTypeProperty().set(b));
    slotList.getSelectionModel().selectedItemProperty().addListener((o, a, b) ->
        detailsLabel.setText(b == null ? "Select a slot" : b.getId() + " is " + b.getState()));
  }
}
