package com.parking.app.viewmodel;

import com.parking.domain.enums.Role;
import com.parking.domain.model.User;
import com.parking.domain.service.AdminService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class UserManagementViewModel {
  private final AdminService service;
  private final ObservableList<User> users = FXCollections.observableArrayList();
  public UserManagementViewModel(AdminService service) { this.service = service; refresh(); }
  public ObservableList<User> users() { return users; }
  public void refresh() { users.setAll(service.users()); }
  public void changeRole(User user, Role role) { service.changeRole(user, role); refresh(); }
  public boolean isBlacklisted(String plate) { return service.isBlacklisted(plate); }
  public void setBlacklisted(String plate, boolean value) { service.setBlacklisted(plate, value); }
}
