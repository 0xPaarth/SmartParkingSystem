package com.parking.app.viewmodel;

import com.parking.app.events.EventBus;
import com.parking.app.events.NotificationEvent;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public final class NotificationCenterViewModel implements AutoCloseable {
  private final EventBus eventBus;
  private final ObservableList<NotificationItem> notifications = FXCollections.observableArrayList();
  private final Set<NotificationItem> unread = Collections.newSetFromMap(new IdentityHashMap<>());
  private final SimpleIntegerProperty unreadCount = new SimpleIntegerProperty();
  private final AutoCloseable subscription;

  public NotificationCenterViewModel(EventBus eventBus) {
    this.eventBus = eventBus;
    subscription = eventBus.subscribe(NotificationEvent.class, this::accept);
  }

  public ObservableList<NotificationItem> notifications() { return notifications; }
  public ReadOnlyIntegerProperty unreadCountProperty() { return unreadCount; }

  public void markRead(NotificationItem item) {
    if (item != null && unread.remove(item)) {
      item.markRead();
      unreadCount.set(unread.size());
    }
  }

  public void markAllRead() {
    notifications.forEach(NotificationItem::markRead);
    unread.clear();
    unreadCount.set(0);
  }

  private void accept(NotificationEvent event) {
    Runnable update = () -> {
      NotificationItem item = new NotificationItem(event);
      notifications.add(0, item);
      unread.add(item);
      unreadCount.set(unread.size());
    };
    if (Platform.isFxApplicationThread()) update.run(); else Platform.runLater(update);
  }

  @Override public void close() {
    try { subscription.close(); } catch (Exception exception) {
      throw new IllegalStateException("Unable to unsubscribe notifications", exception);
    }
  }

  public static final class NotificationItem {
    private final NotificationEvent event;
    private boolean read;
    NotificationItem(NotificationEvent event) { this.event = event; }
    public NotificationEvent event() { return event; }
    public boolean isRead() { return read; }
    void markRead() { read = true; }
  }
}
