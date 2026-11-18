package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.domain.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public final class SidebarNavController extends BaseController {
  @FXML private Button slotMapButton;
  @FXML private Button reservationsButton;
  @FXML private Button checkInButton;
  @FXML private Button checkoutButton;
  @FXML private Button transactionsButton;
  @FXML private Button notificationsButton;
  @FXML private Button adminSlotsButton;
  @FXML private Button pricingButton;
  @FXML private Button usersButton;
  @FXML private Button reportsButton;
  @FXML private Button lostTicketButton;
  @FXML private Button shiftReportButton;
  @FXML private Button makeReservationButton;
  @FXML private Button vehiclesButton;

  public SidebarNavController(NavigationManager navigationManager) {
    super(navigationManager);
  }

  @FXML
  private void initialize() {
    configureForRole(navigationManager.getCurrentRole());
  }

  private void configureForRole(Role role) {
    if (role == null) return;
    boolean driver = role == Role.DRIVER;
    boolean attendant = role == Role.ATTENDANT;
    boolean admin = role == Role.ADMINISTRATOR;
    setVisible(slotMapButton, attendant);
    setVisible(reservationsButton, driver);
    setVisible(checkInButton, driver || attendant);
    setVisible(checkoutButton, driver || attendant);
    setVisible(transactionsButton, driver);
    setVisible(notificationsButton, false);
    setVisible(adminSlotsButton, admin);
    setVisible(pricingButton, admin);
    setVisible(usersButton, admin);
    setVisible(reportsButton, admin);
    setVisible(lostTicketButton, attendant);
    setVisible(shiftReportButton, attendant);
    setVisible(makeReservationButton, driver);
    setVisible(vehiclesButton, driver);
  }

  private void setVisible(Button button, boolean visible) {
    if (button != null) {
      button.setVisible(visible);
      button.setManaged(visible);
    }
  }

  @FXML
  private void logout() {
    navigationManager.setCurrentRole(null);
    navigationManager.navigateTo(NavigationManager.LOGIN);
  }

  @FXML private void openSlotMap() { navigationManager.navigateTo(NavigationManager.SLOT_MAP); }
  @FXML private void openReservations() { navigationManager.navigateTo(NavigationManager.RESERVATION); }
  @FXML private void openCheckIn() { navigationManager.navigateTo(NavigationManager.CHECK_IN); }
  @FXML private void openCheckout() { navigationManager.navigateTo(NavigationManager.CHECKOUT); }
  @FXML private void openTransactions() { navigationManager.navigateTo(NavigationManager.TRANSACTIONS); }
  @FXML private void openNotifications() { navigationManager.navigateTo(NavigationManager.NOTIFICATIONS); }
  @FXML private void openAdminSlots() { navigationManager.navigateTo(NavigationManager.ADMIN_SLOTS); }
  @FXML private void openPricing() { navigationManager.navigateTo(NavigationManager.ADMIN_PRICING); }
  @FXML private void openUsers() { navigationManager.navigateTo(NavigationManager.ADMIN_USERS); }
  @FXML private void openReports() { navigationManager.navigateTo(NavigationManager.ADMIN_REPORTS); }
  @FXML private void openLostTicket() { navigationManager.navigateTo(NavigationManager.CHECKOUT); }
  @FXML private void openShiftReport() { navigationManager.navigateTo(NavigationManager.NOTIFICATIONS); }
  @FXML private void openMakeReservation() { navigationManager.navigateTo(NavigationManager.RESERVATION); }
  @FXML private void openVehicles() { navigationManager.navigateTo(NavigationManager.RESERVATION); }
  @FXML private void toggleTheme() { navigationManager.toggleTheme(); }
}
