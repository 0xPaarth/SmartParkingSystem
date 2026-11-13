package com.parking.app.controller;

import com.parking.app.navigation.NavigationManager;

public abstract class BaseController {
  protected final NavigationManager navigationManager;

  protected BaseController(NavigationManager navigationManager) {
    this.navigationManager = navigationManager;
  }
}
