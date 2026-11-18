package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;
import com.parking.domain.enums.Role;

public abstract class BaseController {
  protected final NavigationManager navigationManager;

  protected BaseController(NavigationManager navigationManager) {
    this.navigationManager = navigationManager;
  }

  protected boolean requireRole(Role... roles) {
    for (Role role : roles) {
      if (navigationManager.getCurrentRole() == role) return true;
    }
    navigationManager.showAccessDenied();
    return false;
  }
}
