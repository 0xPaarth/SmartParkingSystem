package com.parking.app.session;

public class SessionManager { private long lastActivity = System.currentTimeMillis(); public void touch(){lastActivity=System.currentTimeMillis();} public boolean isIdle(){return System.currentTimeMillis()-lastActivity >= 15 * 60_000L;} }
