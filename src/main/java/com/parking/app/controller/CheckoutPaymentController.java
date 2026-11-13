package com.parking.app.controller;

import com.parking.app.context.ParkingAppState;
import com.parking.app.navigation.NavigationManager;
import com.parking.app.context.AppContext;
import com.parking.app.events.NotificationEvent;
import com.parking.app.util.ErrorDialogs;
import com.parking.app.viewmodel.CheckoutPaymentViewModel;
import com.parking.domain.enums.PaymentMethod;
import com.parking.domain.service.PricingLineItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public final class CheckoutPaymentController extends BaseController {
  private final CheckoutPaymentViewModel viewModel = new CheckoutPaymentViewModel(ParkingAppState.get());
  @FXML private TextField ticketField;
  @FXML private ComboBox<PaymentMethod> methodBox;
  @FXML private Label totalLabel;
  @FXML private ListView<PricingLineItem> priceList;
  @FXML private Label receiptLabel;
  public CheckoutPaymentController(NavigationManager navigationManager) { super(navigationManager); }
  @FXML private void initialize() {
    ticketField.textProperty().bindBidirectional(viewModel.lookupProperty());
    methodBox.getItems().setAll(PaymentMethod.CASH, PaymentMethod.CARD, PaymentMethod.WALLET);
    methodBox.valueProperty().bindBidirectional(viewModel.paymentMethodProperty());
    totalLabel.textProperty().bind(viewModel.totalProperty().asString("Amount due: %s"));
    priceList.setItems(viewModel.items());
    priceList.setCellFactory(list -> new ListCell<>() {
      @Override protected void updateItem(PricingLineItem item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty || item == null ? null : item.name() + ": " + item.amount());
      }
    });
  }
  @FXML private void generateReceipt() {
    try {
      receiptLabel.setText(viewModel.generateReceipt());
      AppContext.EVENT_BUS.publish(new NotificationEvent("Payment successful",
          "Payment was recorded and the receipt is ready.", com.parking.domain.enums.NotificationType.INFO));
    } catch (RuntimeException ex) {
      ErrorDialogs.show(receiptLabel.getScene().getWindow(), ex);
    }
  }
}
