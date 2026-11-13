package com.parking.app.viewmodel;

import com.parking.domain.enums.PaymentMethod;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.service.PricingLineItem;
import com.parking.domain.service.PricingService;
import com.parking.service.PersistenceState;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Objects;

public final class CheckoutPaymentViewModel {
  private final PersistenceState state;
  private final PricingService pricingService = new PricingService();
  private final StringProperty lookup = new SimpleStringProperty("");
  private final ObjectProperty<PaymentMethod> paymentMethod = new SimpleObjectProperty<>(PaymentMethod.CASH);
  private final ObjectProperty<ParkingSession> session = new SimpleObjectProperty<>();
  private final ObservableList<PricingLineItem> items = FXCollections.observableArrayList();
  private final ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>(BigDecimal.ZERO);
  private final StringProperty receipt = new SimpleStringProperty("");

  public CheckoutPaymentViewModel(PersistenceState state) {
    this.state = Objects.requireNonNull(state);
    lookup.addListener((obs, oldValue, newValue) -> find());
  }
  public StringProperty lookupProperty() { return lookup; }
  public ObjectProperty<PaymentMethod> paymentMethodProperty() { return paymentMethod; }
  public ObjectProperty<ParkingSession> sessionProperty() { return session; }
  public ObservableList<PricingLineItem> items() { return items; }
  public ObjectProperty<BigDecimal> totalProperty() { return total; }
  public StringProperty receiptProperty() { return receipt; }
  public void find() {
    session.set(state.getSessions().stream().filter(item -> item.getId().equalsIgnoreCase(lookup.get().trim())).findFirst().orElse(null));
    if (session.get() != null) {
      var result = pricingService.calculate(session.get());
      items.setAll(result.getLineItems());
      total.set(result.getTotal());
    } else {
      items.clear();
      total.set(BigDecimal.ZERO);
    }
  }
  public String generateReceipt() {
    if (session.get() == null) throw new IllegalStateException("Ticket not found");
    receipt.set("Receipt " + session.get().getId() + " | " + paymentMethod.get() + " | " + total.get());
    return receipt.get();
  }
}
