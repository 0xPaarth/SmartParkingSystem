package com.parking.app.events;

import com.parking.domain.enums.NotificationType;
import java.time.Instant;
import java.util.Objects;

public record NotificationEvent(String title, String message, NotificationType type, Instant createdAt) {
  public NotificationEvent {
    title = Objects.requireNonNull(title).trim();
    message = Objects.requireNonNull(message).trim();
    type = Objects.requireNonNull(type);
    createdAt = createdAt == null ? Instant.now() : createdAt;
  }

  public NotificationEvent(String title, String message, NotificationType type) {
    this(title, message, type, Instant.now());
  }
}
