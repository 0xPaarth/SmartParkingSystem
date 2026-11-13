package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public final class LoginController extends BaseController {
  private final LoginViewModel viewModel = new LoginViewModel();

  @FXML private TextField userIdField;
  @FXML private Label errorLabel;

  public LoginController(NavigationManager navigationManager) {
    super(navigationManager);
  }

  @FXML
  private void login() {
    viewModel.authenticate(userIdField.getText()).ifPresentOrElse(user -> {
      errorLabel.setText("");
      navigationManager.navigateForRole(user.getRole());
    }, () -> errorLabel.setText("Enter a valid seeded user ID, such as driver-1."));
  }

  @FXML
  private void backToSplash() {
    navigationManager.navigateTo(NavigationManager.SPLASH);
  }
}
