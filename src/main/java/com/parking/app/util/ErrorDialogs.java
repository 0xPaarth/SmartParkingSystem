package com.parking.app.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public final class ErrorDialogs {
  private ErrorDialogs() {}
  public static void show(Window owner, Throwable error) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Parking Management");
    alert.setHeaderText("Unable to complete request");
    alert.setContentText(ValidationErrorHandler.message(error));
    alert.initOwner(owner);
    alert.showAndWait();
  }
}
