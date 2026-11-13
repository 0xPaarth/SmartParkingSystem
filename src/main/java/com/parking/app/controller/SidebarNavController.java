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
}
