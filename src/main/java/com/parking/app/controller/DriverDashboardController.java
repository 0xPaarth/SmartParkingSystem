package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import javafx.fxml.FXML;

public final class DriverDashboardController extends BaseController {
  public DriverDashboardController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void openReservations() { navigationManager.navigateTo(NavigationManager.RESERVATION); }
  @FXML private void openWallet() { navigationManager.navigateTo(NavigationManager.TRANSACTIONS); }
  @FXML private void openVehicles() { navigationManager.navigateTo(NavigationManager.RESERVATION); }
  @FXML private void openCheckIn() { navigationManager.navigateTo(NavigationManager.CHECK_IN); }
  @FXML private void openCheckout() { navigationManager.navigateTo(NavigationManager.CHECKOUT); }
}
