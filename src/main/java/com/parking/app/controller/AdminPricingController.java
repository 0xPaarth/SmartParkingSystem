package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.PricingConfigurationViewModel;
import com.parking.domain.enums.SlotType;
import com.parking.domain.service.AdminService;
import com.parking.domain.service.PricingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;

public final class AdminPricingController extends BaseController {
  @FXML private TextField compactRate;
  @FXML private TextField standardRate;
  @FXML private TextField surgeRate;
  @FXML private TextField peakRate;
  @FXML private TextField gstRate;
  @FXML private Label simulatorResult;
  private final PricingConfigurationViewModel viewModel =
      new PricingConfigurationViewModel(AdminService.shared(), new PricingService());
  public AdminPricingController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() { load(); }
  private void load() {
    compactRate.setText(viewModel.rate(SlotType.COMPACT).toString());
    standardRate.setText(viewModel.rate(SlotType.STANDARD).toString());
    surgeRate.setText(viewModel.configProperty().get().getSurgeRate().toString());
    peakRate.setText(viewModel.configProperty().get().getPeakHourRate().toString());
    gstRate.setText(viewModel.configProperty().get().getGstRate().toString());
    simulatorResult.textProperty().bind(viewModel.simulatorResultProperty());
  }
  @FXML private void save() {
    viewModel.setRate(SlotType.COMPACT, new BigDecimal(compactRate.getText()));
    viewModel.setRate(SlotType.STANDARD, new BigDecimal(standardRate.getText()));
    viewModel.configProperty().get().setSurgeRate(new BigDecimal(surgeRate.getText()));
    viewModel.configProperty().get().setPeakHourRate(new BigDecimal(peakRate.getText()));
    viewModel.configProperty().get().setGstRate(new BigDecimal(gstRate.getText()));
    viewModel.save();
  }
  @FXML private void simulate() { viewModel.simulate(SlotType.STANDARD, 60); }
  @FXML private void back() { navigationManager.navigateTo(NavigationManager.ADMIN_DASHBOARD); }
}
