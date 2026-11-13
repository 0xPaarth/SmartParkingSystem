package com.parking.app.context;

import java.util.Locale;

public final class AppContext { public static final String CURRENCY = "₹"; public static final Locale LOCALE = Locale.forLanguageTag("en-IN"); public static final int IDLE_TIMEOUT_MINUTES = 15; public static final int MAX_LOGIN_ATTEMPTS = 5; public static final int LOCKOUT_MINUTES = 10; private AppContext() {} }
