package com.parking.app.viewmodel;

import com.parking.domain.service.AdminService;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class AdminDashboardViewModel {
  private final AdminService service;
  private final StringProperty revenue = new SimpleStringProperty();
  private final LongProperty occupied = new SimpleLongProperty();
  private final LongProperty available = new SimpleLongProperty();
  private final LongProperty users = new SimpleLongProperty();
  public AdminDashboardViewModel(AdminService service) { this.service = service; refresh(); }
  public void refresh() {
    revenue.set("₹" + service.revenue().setScale(2));
    occupied.set(service.occupiedSlots());
    available.set(service.availableSlots());
    users.set(service.users().size());
  }
  public StringProperty revenueProperty() { return revenue; }
  public LongProperty occupiedProperty() { return occupied; }
  public LongProperty availableProperty() { return available; }
  public LongProperty usersProperty() { return users; }
}
