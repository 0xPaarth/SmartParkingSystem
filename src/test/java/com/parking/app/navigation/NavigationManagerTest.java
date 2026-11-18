package com.parking.app.navigation;

import com.parking.domain.enums.Role;
import com.parking.app.session.AuthorizationManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NavigationManagerTest {
  @Test
  void mapsRolesToDashboardRoutes() {
    assertEquals(NavigationManager.DRIVER_DASHBOARD, NavigationManager.viewForRole(Role.DRIVER));
    assertEquals(NavigationManager.ATTENDANT_DASHBOARD, NavigationManager.viewForRole(Role.ATTENDANT));
    assertEquals(NavigationManager.ADMIN_DASHBOARD, NavigationManager.viewForRole(Role.ADMINISTRATOR));
    assertEquals(NavigationManager.LOGIN, NavigationManager.viewForRole(null));
  }

  @Test
  void enforcesFeaturePermissions() {
    assertTrue(AuthorizationManager.isAllowed(Role.DRIVER, AuthorizationManager.MY_RESERVATIONS));
    assertTrue(AuthorizationManager.isAllowed(Role.DRIVER, AuthorizationManager.SELF_CHECK_IN_OUT));
    assertFalse(AuthorizationManager.isAllowed(Role.DRIVER, AuthorizationManager.WALK_IN_ENTRY));

    assertTrue(AuthorizationManager.isAllowed(Role.ATTENDANT, AuthorizationManager.LIVE_OCCUPANCY));
    assertFalse(AuthorizationManager.isAllowed(Role.ATTENDANT, AuthorizationManager.MY_WALLET));
    assertFalse(AuthorizationManager.isAllowed(Role.ATTENDANT, AuthorizationManager.LOYALTY));

    assertTrue(AuthorizationManager.isAllowed(Role.ADMINISTRATOR, AuthorizationManager.MAINTENANCE));
    assertTrue(AuthorizationManager.isAllowed(Role.ADMINISTRATOR, AuthorizationManager.LOYALTY));
  }
}
