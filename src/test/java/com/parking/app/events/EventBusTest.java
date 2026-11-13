package com.parking.app.events;

import com.parking.domain.enums.NotificationType;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventBusTest {
  @Test void publishesEventsToMatchingSubscribersAndUnsubscribes() throws Exception {
    EventBus bus = new EventBus();
    AtomicInteger calls = new AtomicInteger();
    AutoCloseable subscription = bus.subscribe(NotificationEvent.class, event -> calls.incrementAndGet());
    bus.publish(new NotificationEvent("Payment successful", "Receipt ready", NotificationType.INFO));
    subscription.close();
    bus.publish(new NotificationEvent("Reservation expiring", "Expires soon", NotificationType.WARNING));
    assertEquals(1, calls.get());
  }
}
