package com.parking.app.config;

public class AppConfig { private int idleTimeoutMinutes = 15; private int maxLoginAttempts = 5; private int lockoutMinutes = 10; public int getIdleTimeoutMinutes(){return idleTimeoutMinutes;} public int getMaxLoginAttempts(){return maxLoginAttempts;} public int getLockoutMinutes(){return lockoutMinutes;} }
