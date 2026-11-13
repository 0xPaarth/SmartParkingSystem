package com.parking.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
  @Override public void start(Stage stage) { stage.setTitle("Parking Management"); stage.setScene(new Scene(new StackPane(new Label("Splash → Login")), 640, 400)); stage.show(); }
  public static void main(String[] args) { launch(args); }
}
