package com.parking.app.context;

import com.parking.service.PersistenceState;
import com.parking.service.SeedDataLoader;

public final class ParkingAppState {
  private static final PersistenceState STATE = new SeedDataLoader().load();

  private ParkingAppState() {}

  public static PersistenceState get() {
    return STATE;
  }
}
