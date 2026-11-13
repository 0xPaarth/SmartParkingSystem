package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.UserManagementViewModel;
import com.parking.domain.enums.Role;
import com.parking.domain.model.User;
import com.parking.domain.service.AdminService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public final class AdminUserManagementController extends BaseController {
  @FXML private TableView<User> usersTable;
  @FXML private TableColumn<User, String> idColumn;
  @FXML private TableColumn<User, String> nameColumn;
  @FXML private TableColumn<User, String> roleColumn;
  private final UserManagementViewModel viewModel = new UserManagementViewModel(AdminService.shared());
  public AdminUserManagementController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    idColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
    nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
    roleColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRole().name()));
    usersTable.setItems(viewModel.users());
  }
  @FXML private void changeRole() {
    User user = usersTable.getSelectionModel().getSelectedItem();
    if (user != null) viewModel.changeRole(user, user.getRole() == Role.DRIVER ? Role.ATTENDANT : Role.DRIVER);
  }
  @FXML private void toggleBlacklist() {
    User user = usersTable.getSelectionModel().getSelectedItem();
    if (user != null && user.getId() != null)
      viewModel.setBlacklisted(user.getId(), !viewModel.isBlacklisted(user.getId()));
  }
  @FXML private void back() { navigationManager.navigateTo(NavigationManager.ADMIN_DASHBOARD); }
}
