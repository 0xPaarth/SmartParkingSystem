package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import javafx.fxml.FXML;

public final class AttendantDashboardController extends BaseController {
  public AttendantDashboardController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void openOccupancy() { navigationManager.navigateTo(NavigationManager.SLOT_MAP); }
  @FXML private void openWalkIn() { navigationManager.navigateTo(NavigationManager.CHECK_IN); }
  @FXML private void openExit() { navigationManager.navigateTo(NavigationManager.CHECKOUT); }
  @FXML private void openLostTicket() { navigationManager.navigateTo(NavigationManager.CHECKOUT); }
  @FXML private void openShiftReport() { navigationManager.navigateTo(NavigationManager.NOTIFICATIONS); }
}
