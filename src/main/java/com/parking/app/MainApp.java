package com.parking.app;

import com.parking.app.navigation.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
  @Override public void start(Stage stage) {
    stage.setTitle("Parking Management");
    NavigationManager navigationManager = new NavigationManager(stage);
    navigationManager.navigateTo(NavigationManager.SPLASH);
    stage.getScene().getStylesheets().add(
        getClass().getResource("/com/parking/app/theme/light.css").toExternalForm());
    stage.show();
  }

  public static void main(String[] args) { launch(args); }
}
