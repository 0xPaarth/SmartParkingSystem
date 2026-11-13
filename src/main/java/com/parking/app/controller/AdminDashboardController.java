package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.AdminDashboardViewModel;
import com.parking.domain.service.AdminService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;

public final class AdminDashboardController extends BaseController {
  @FXML private LineChart<String, Number> revenueChart;
  @FXML private BarChart<String, Number> occupancyChart;
  @FXML private PieChart slotTypesChart;
  private final AdminDashboardViewModel viewModel = new AdminDashboardViewModel(AdminService.shared());
  public AdminDashboardController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    viewModel.refresh();
    revenueChart.getData().add(new javafx.scene.chart.XYChart.Series<>(javafx.collections.FXCollections.observableArrayList(
        new javafx.scene.chart.XYChart.Data<>("Today", 0), new javafx.scene.chart.XYChart.Data<>("Current",
            Double.parseDouble(viewModel.revenueProperty().get().replace("₹", ""))))));
    occupancyChart.getData().add(new javafx.scene.chart.XYChart.Series<>(javafx.collections.FXCollections.observableArrayList(
        new javafx.scene.chart.XYChart.Data<>("Occupied", viewModel.occupiedProperty().get()),
        new javafx.scene.chart.XYChart.Data<>("Available", viewModel.availableProperty().get()))));
    slotTypesChart.getData().add(new PieChart.Data("Configured slots", viewModel.occupiedProperty().get() + viewModel.availableProperty().get()));
  }
  @FXML private void openSlots() { navigationManager.navigateTo(NavigationManager.ADMIN_SLOTS); }
  @FXML private void openPricing() { navigationManager.navigateTo(NavigationManager.ADMIN_PRICING); }
  @FXML private void openUsers() { navigationManager.navigateTo(NavigationManager.ADMIN_USERS); }
  @FXML private void openReports() { navigationManager.navigateTo(NavigationManager.ADMIN_REPORTS); }
}
