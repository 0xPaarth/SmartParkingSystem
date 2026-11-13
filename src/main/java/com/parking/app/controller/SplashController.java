package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import javafx.fxml.FXML;

public final class SplashController extends BaseController {
  public SplashController(NavigationManager navigationManager) {
    super(navigationManager);
  }

  @FXML
  private void continueToLogin() {
    navigationManager.navigateTo(NavigationManager.LOGIN);
  }
}
