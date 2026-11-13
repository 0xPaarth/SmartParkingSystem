# Phase 3 implementation report

Phase 3 adds framework-free domain behavior and services without expanding the JavaFX application:

- `domain.model` now carries session timing, vehicle/slot/user pricing inputs, and loyalty redemption.
- `domain.state` implements the available/occupied/out-of-service slot state machine while retaining the Phase 2 `domain.enums.SlotState` API.
- `domain.service` provides the exact pricing chain: BaseRate, Surge, PeakHour, WeekendHoliday, EvDiscount, ReservationFee, PromoCode, LoyaltyRedemption, OverstayPenalty, and Tax.
- `PricingResult` exposes ordered line items, with GST represented separately as the `Tax` line.
- `ParkingService` is the facade for slot registration, session start/end, and pricing.
- JUnit 5 tests cover state transitions, locked INR rates, discounts, fees, overstay grace, loyalty/promo behavior, and GST.

Validation is limited in this environment: `java` and `mvn` are not installed, so compilation and JUnit execution could not be run. `git diff --check` passed, and the domain/service packages contain no JavaFX imports.
