package com.parking.app.viewmodel;

import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.model.PricingConfig;
import com.parking.domain.service.AdminService;
import com.parking.domain.service.PricingService;
import javafx.beans.property.*;
import java.math.BigDecimal;

public final class PricingConfigurationViewModel {
  private final AdminService service;
  private final PricingService pricingService;
  private final ObjectProperty<PricingConfig> config = new SimpleObjectProperty<>();
  private final StringProperty simulatorResult = new SimpleStringProperty("₹0.00");
  public PricingConfigurationViewModel(AdminService service, PricingService pricingService) {
    this.service = service; this.pricingService = pricingService; config.set(service.pricingConfig());
  }
  public ObjectProperty<PricingConfig> configProperty() { return config; }
  public StringProperty simulatorResultProperty() { return simulatorResult; }
  public BigDecimal rate(SlotType type) { return config.get().getHourlyRates().get(type); }
  public void setRate(SlotType type, BigDecimal value) { config.get().getHourlyRates().put(type, value); }
  public void save() { service.updatePricingConfig(config.get()); }
  public void simulate(SlotType type, long minutes) {
    ParkingSession session = new ParkingSession();
    session.setSlot(new ParkingSlot("SIM", type));
    simulatorResult.set("₹" + new PricingService(config.get()).calculate(session).getTotal().setScale(2));
  }
}
