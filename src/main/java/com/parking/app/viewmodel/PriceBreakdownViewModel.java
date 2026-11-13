package com.parking.app.viewmodel;

import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.service.PricingLineItem;
import com.parking.domain.service.PricingResult;
import com.parking.domain.service.PricingService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

public final class PriceBreakdownViewModel {
  private final PricingService pricingService;
  private final ObjectProperty<ParkingSlot> slot = new SimpleObjectProperty<>();
  private final LongProperty durationMinutes = new SimpleLongProperty(60);
  private final BooleanProperty reserved = new SimpleBooleanProperty();
  private final StringProperty promoCode = new SimpleStringProperty("");
  private final ObservableList<PricingLineItem> items = FXCollections.observableArrayList();
  private final ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>(BigDecimal.ZERO);

  public PriceBreakdownViewModel() { this(new PricingService()); }
  public PriceBreakdownViewModel(PricingService pricingService) {
    this.pricingService = pricingService;
    slot.addListener((obs, oldValue, newValue) -> recalculate());
    durationMinutes.addListener((obs, oldValue, newValue) -> recalculate());
    reserved.addListener((obs, oldValue, newValue) -> recalculate());
    promoCode.addListener((obs, oldValue, newValue) -> recalculate());
  }
  public ObjectProperty<ParkingSlot> slotProperty() { return slot; }
  public LongProperty durationMinutesProperty() { return durationMinutes; }
  public BooleanProperty reservedProperty() { return reserved; }
  public StringProperty promoCodeProperty() { return promoCode; }
  public ObservableList<PricingLineItem> items() { return items; }
  public ObjectProperty<BigDecimal> totalProperty() { return total; }
  public void recalculate() {
    ParkingSession session = new ParkingSession();
    session.setSlot(slot.get());
    session.setReserved(reserved.get());
    session.setPromoCode(promoCode.get());
    session.setEntryTime(Instant.now().minus(Duration.ofMinutes(Math.max(1, durationMinutes.get()))));
    PricingResult result = pricingService.calculate(session);
    items.setAll(result.getLineItems());
    total.set(result.getTotal());
  }
}
