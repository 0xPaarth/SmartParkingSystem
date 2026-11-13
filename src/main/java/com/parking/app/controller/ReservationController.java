package com.parking.app.controller;

import com.parking.app.context.ParkingAppState;
import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.ReservationViewModel;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.service.PricingLineItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalTime;

public final class ReservationController extends BaseController {
  private final ReservationViewModel viewModel = new ReservationViewModel(ParkingAppState.get());
  @FXML private TextField driverField;
  @FXML private DatePicker datePicker;
  @FXML private TextField timeField;
  @FXML private ComboBox<ParkingSlot> slotBox;
  @FXML private Label priceLabel;
  @FXML private ListView<PricingLineItem> priceList;
  @FXML private Label resultLabel;
  public ReservationController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    driverField.textProperty().bindBidirectional(viewModel.driverIdProperty());
    datePicker.valueProperty().bindBidirectional(viewModel.dateProperty());
    timeField.setText(viewModel.timeProperty().get().toString());
    timeField.textProperty().addListener((o, a, b) -> {
      try { viewModel.timeProperty().set(LocalTime.parse(b)); }
      catch (RuntimeException exception) { resultLabel.setText("Use time format HH:mm"); }
    });
    slotBox.setItems(viewModel.availableSlots());
    slotBox.valueProperty().bindBidirectional(viewModel.selectedSlotProperty());
    priceLabel.textProperty().bind(viewModel.priceBreakdown().totalProperty().asString("Estimated total: %s"));
    priceList.setItems(viewModel.priceBreakdown().items());
    priceList.setCellFactory(list -> new ListCell<>() {
      @Override protected void updateItem(PricingLineItem item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : item.name() + ": " + item.amount());
      }
    });
  }
  @FXML private void confirm() {
    try { resultLabel.setText("Confirmed " + viewModel.confirm().getId()); }
    catch (IllegalStateException ex) { resultLabel.setText(ex.getMessage()); }
  }
}
