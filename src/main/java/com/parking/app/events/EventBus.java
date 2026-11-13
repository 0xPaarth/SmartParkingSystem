package com.parking.app.events;

import java.util.function.Consumer;

public class EventBus { public <T> void subscribe(Class<T> type, Consumer<T> handler) {} public void publish(Object event) {} }
