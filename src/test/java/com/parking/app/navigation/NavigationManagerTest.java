package com.parking.app.navigation;

import com.parking.domain.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NavigationManagerTest {
  @Test
  void mapsRolesToDashboardRoutes() {
    assertEquals(NavigationManager.DRIVER_DASHBOARD, NavigationManager.viewForRole(Role.DRIVER));
    assertEquals(NavigationManager.ATTENDANT_DASHBOARD, NavigationManager.viewForRole(Role.ATTENDANT));
    assertEquals(NavigationManager.ADMIN_DASHBOARD, NavigationManager.viewForRole(Role.ADMINISTRATOR));
    assertEquals(NavigationManager.LOGIN, NavigationManager.viewForRole(null));
  }
}
