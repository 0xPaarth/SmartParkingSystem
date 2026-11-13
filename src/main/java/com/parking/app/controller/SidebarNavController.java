package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import javafx.fxml.FXML;

public final class SidebarNavController extends BaseController {
  public SidebarNavController(NavigationManager navigationManager) {
    super(navigationManager);
  }

  @FXML
  private void logout() {
    navigationManager.navigateTo(NavigationManager.LOGIN);
  }

  @FXML private void openSlotMap() { navigationManager.navigateTo(NavigationManager.SLOT_MAP); }
  @FXML private void openReservations() { navigationManager.navigateTo(NavigationManager.RESERVATION); }
  @FXML private void openCheckIn() { navigationManager.navigateTo(NavigationManager.CHECK_IN); }
  @FXML private void openCheckout() { navigationManager.navigateTo(NavigationManager.CHECKOUT); }
  @FXML private void openTransactions() { navigationManager.navigateTo(NavigationManager.TRANSACTIONS); }
  @FXML private void openNotifications() { navigationManager.navigateTo(NavigationManager.NOTIFICATIONS); }
  @FXML private void toggleTheme() { navigationManager.toggleTheme(); }
}
