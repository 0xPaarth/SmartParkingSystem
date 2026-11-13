package com.parking.app.navigation;

public class NavigationManager { private String currentView = "Splash"; public String getCurrentView(){return currentView;} public void navigateTo(String view){currentView=view;} }
