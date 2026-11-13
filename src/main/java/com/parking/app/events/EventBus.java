package com.parking.app.events;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class EventBus {
  private final Map<Class<?>, CopyOnWriteArrayList<Consumer<?>>> handlers = new ConcurrentHashMap<>();

  public <T> AutoCloseable subscribe(Class<T> type, Consumer<T> handler) {
    handlers.computeIfAbsent(type, ignored -> new CopyOnWriteArrayList<>()).add(handler);
    return () -> unsubscribe(type, handler);
  }

  public <T> void unsubscribe(Class<T> type, Consumer<T> handler) {
    List<Consumer<?>> registered = handlers.get(type);
    if (registered != null) {
      registered.remove(handler);
    }
  }

  public void publish(Object event) {
    if (event == null) return;
    handlers.forEach((type, registered) -> {
      if (type.isInstance(event)) {
        registered.forEach(handler -> dispatch(handler, event));
      }
    });
  }

  @SuppressWarnings("unchecked")
  private static <T> void dispatch(Consumer<?> handler, Object event) {
    ((Consumer<T>) handler).accept((T) event);
  }
}
