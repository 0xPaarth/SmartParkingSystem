package com.parking.app.navigation;

import com.parking.domain.enums.Role;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public final class NavigationManager {
  public static final String SPLASH = "splash";
  public static final String LOGIN = "login";
  public static final String DRIVER_DASHBOARD = "driver-dashboard";
  public static final String ATTENDANT_DASHBOARD = "attendant-dashboard";
  public static final String ADMIN_DASHBOARD = "admin-dashboard";
  public static final String ADMIN_SLOTS = "admin-slots";
  public static final String ADMIN_PRICING = "admin-pricing";
  public static final String ADMIN_USERS = "admin-users";
  public static final String ADMIN_REPORTS = "admin-reports";
  public static final String SLOT_MAP = "slot-map";
  public static final String RESERVATION = "reservation";
  public static final String CHECK_IN = "check-in";
  public static final String CHECKOUT = "checkout";
  public static final String TRANSACTIONS = "transactions";

  private final Stage stage;
  private String currentView;

  public NavigationManager(Stage stage) {
    this.stage = Objects.requireNonNull(stage);
  }

  public String getCurrentView() {
    return currentView;
  }

  public void navigateTo(String view) {
    try {
      FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
          getClass().getResource("/com/parking/app/view/" + view + ".fxml")));
      loader.setControllerFactory(type -> {
        try {
          return type.getConstructor(NavigationManager.class).newInstance(this);
        } catch (ReflectiveOperationException exception) {
          throw new IllegalStateException("Unable to create view controller " + type.getName(), exception);
        }
      });
      Parent root = loader.load();
      if (stage.getScene() == null) {
        stage.setScene(new Scene(root, 1100, 700));
      } else {
        stage.getScene().setRoot(root);
      }
      currentView = view;
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to load view " + view, exception);
    }
  }

  public void navigateForRole(Role role) {
    navigateTo(viewForRole(role));
  }

  public static String viewForRole(Role role) {
    if (role == null) {
      return LOGIN;
    }
    return switch (role) {
      case DRIVER -> DRIVER_DASHBOARD;
      case ATTENDANT -> ATTENDANT_DASHBOARD;
      case ADMINISTRATOR -> ADMIN_DASHBOARD;
    };
  }
}
