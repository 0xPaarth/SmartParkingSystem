package com.parking.app.session;

import com.parking.domain.enums.Role;
import java.util.Map;
import java.util.Set;

public final class AuthorizationManager {
  public static final String WALK_IN_ENTRY = "WALK_IN_ENTRY";
  public static final String LOST_TICKET = "LOST_TICKET";
  public static final String MAINTENANCE = "MAINTENANCE";
  public static final String SHIFT_REPORT = "SHIFT_REPORT";
  public static final String LIVE_OCCUPANCY = "LIVE_OCCUPANCY";
  public static final String PROCESS_EXIT = "PROCESS_EXIT";
  public static final String MY_RESERVATIONS = "MY_RESERVATIONS";
  public static final String MAKE_RESERVATION = "MAKE_RESERVATION";
  public static final String MY_WALLET = "MY_WALLET";
  public static final String MY_VEHICLES = "MY_VEHICLES";
  public static final String SELF_CHECK_IN_OUT = "SELF_CHECK_IN_OUT";
  public static final String LOYALTY = "LOYALTY";

  private static final Map<Role, Set<String>> PERMISSIONS = Map.of(
      Role.DRIVER, Set.of(MY_RESERVATIONS, MAKE_RESERVATION, MY_WALLET,
          MY_VEHICLES, SELF_CHECK_IN_OUT),
      Role.ATTENDANT, Set.of(LIVE_OCCUPANCY, WALK_IN_ENTRY, PROCESS_EXIT,
          LOST_TICKET, SHIFT_REPORT),
      Role.ADMINISTRATOR, Set.of(
          LIVE_OCCUPANCY, WALK_IN_ENTRY, PROCESS_EXIT, LOST_TICKET, SHIFT_REPORT,
          MAINTENANCE, MY_RESERVATIONS, MAKE_RESERVATION, MY_WALLET, MY_VEHICLES,
          SELF_CHECK_IN_OUT, LOYALTY));

  private AuthorizationManager() {}

  public static boolean isAllowed(Role role, String permission) {
    return role != null && permission != null
        && PERMISSIONS.getOrDefault(role, Set.of()).contains(permission);
  }
}
