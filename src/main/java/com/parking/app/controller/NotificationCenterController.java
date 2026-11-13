package com.parking.app.controller;

import com.parking.app.context.AppContext;
import com.parking.app.navigation.NavigationManager;
import com.parking.app.viewmodel.NotificationCenterViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class NotificationCenterController extends BaseController {
  @FXML private ListView<NotificationCenterViewModel.NotificationItem> notificationList;
  @FXML private Label unreadLabel;
  private final NotificationCenterViewModel viewModel = new NotificationCenterViewModel(AppContext.EVENT_BUS);

  public NotificationCenterController(NavigationManager navigationManager) { super(navigationManager); }

  @FXML private void initialize() {
    notificationList.setItems(viewModel.notifications());
    notificationList.setCellFactory(list -> new ListCell<>() {
      @Override protected void updateItem(NotificationCenterViewModel.NotificationItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) { setText(null); return; }
        var event = item.event();
        setText((item.isRead() ? "" : "• ") + event.title() + " — " + event.message() + " (" +
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(event.createdAt().atZone(ZoneId.systemDefault()).toLocalDateTime()) + ")");
      }
    });
    unreadLabel.textProperty().bind(viewModel.unreadCountProperty().asString("Unread: %d"));
    notificationList.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> viewModel.markRead(selected));
  }

  @FXML private void markAllRead() { viewModel.markAllRead(); notificationList.refresh(); }
  @FXML private void back() { navigationManager.navigateTo(NavigationManager.DRIVER_DASHBOARD); }
}
