package com.parking.app.controller;

import com.parking.app.context.ParkingAppState;
import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.CheckInViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public final class CheckInController extends BaseController {
  private final CheckInViewModel viewModel = new CheckInViewModel(ParkingAppState.get());
  @FXML private TextField lookupField;
  @FXML private Label suggestionLabel;
  @FXML private Label ticketLabel;
  public CheckInController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    lookupField.textProperty().bindBidirectional(viewModel.lookupProperty());
    viewModel.suggestedSlotProperty().addListener((o, a, b) ->
        suggestionLabel.setText(b == null ? "No slot suggestion" : "Suggested: " + b.getId()));
  }
  @FXML private void generateTicket() {
    try { ticketLabel.setText("QR ticket: " + viewModel.generateTicket()); }
    catch (IllegalStateException ex) { ticketLabel.setText(ex.getMessage()); }
  }
}
