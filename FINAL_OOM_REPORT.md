# Smart Parking Management System
## Object-Oriented Modelling and Design Project Report

**University/Department:** ______________________________  
**Course:** Object-Oriented Modelling and Design  
**Group Members:**  
1. ______________________________  
2. ______________________________  
3. ______________________________  
4. ______________________________  

**Project Repository:** SmartParkingSystem  
**Technology:** Java 17, JavaFX 21, Maven  
**Submission Date:** ______________________________

## Abstract

The Smart Parking Management System is a Java 17 and JavaFX 21 desktop application for managing parking operations across drivers, attendants, and administrators. It supports role-based navigation, multi-level parking slots, reservations, QR-based entry tickets, checkout and payment receipts, locked INR pricing, notifications, audit reports, CSV/PDF export, and JSON persistence. The implementation applies object-oriented modelling through encapsulated domain entities, inheritance-based exceptions, polymorphic strategies and repositories, and abstract contracts for services and infrastructure. Eleven implemented patterns organize the design: Strategy, State, Factory, Repository, Singleton, Observer, Adapter, Facade, MVVM, Front Controller, and Service Layer. This report documents the implemented architecture, UML views, OOP evidence, pricing chain, tests, and exception model without claiming patterns or components absent from the source code.

## Introduction, Problem, and Objectives

Parking operations involve several roles, resources, state transitions, transactions, and pricing rules. A manual or unstructured system makes it difficult to identify available slots, prevent conflicting reservations, apply consistent charges, process payments, persist data, and provide role-appropriate workflows. The project addresses these requirements with a desktop application whose UI routes users by role and whose domain services coordinate parking, reservations, pricing, administration, reporting, and persistence.

The principal objectives are to:

1. Provide separate driver, attendant, and administrator workflows.
2. Represent parking lots, levels, slots, vehicles, sessions, reservations, users, wallets, loyalty accounts, and transactions as domain objects.
3. Enforce legal parking-slot lifecycle transitions and reject unavailable slots.
4. Support reservation, check-in, QR ticket, checkout, payment, receipt, and notification flows.
5. Apply a deterministic ten-step pricing chain with locked INR rates and 18% GST.
6. Persist domain data in memory and JSON, with backup and export services.
7. Test services, view models, state transitions, repositories, persistence, and UI-support components using JUnit.

## Layered Architecture and ParkingService Facade

The implemented structure follows a layered flow:

**UI → Service → Repository → Domain**

The **UI layer** consists of JavaFX FXML views, controllers, and MVVM view models. `NavigationManager` loads screens and routes authenticated users by `Role`. `ParkingAppState` and `PersistenceState` expose application state to the UI, while `EventBus` publishes notification events.

The **Service layer** centralizes use cases. `ReservationService` creates and validates reservations, `PricingService` calculates quotes and totals, `AdminService` manages administrative operations, `AuditService` records audit activity, `ReportService` exports reports, and `BackupService` handles persistence backups. `SeedDataLoader` supplies deterministic initial data.

The **Repository layer** provides persistence contracts through `Repository<T>`. `InMemoryRepositoryImpl` stores entities in memory, while `JsonRepositoryImpl` adapts JSON file storage to the repository contract and delegates storage operations to the in-memory implementation. `RepositoryFactory` creates repository implementations.

The **Domain layer** contains models such as `User`, `Vehicle`, `ParkingLot`, `ParkingLevel`, `ParkingSlot`, `ParkingSession`, `Reservation`, `Transaction`, `Wallet`, `LoyaltyAccount`, and `PricingConfig`. Domain services and state objects enforce business rules.

`ParkingService` is the Facade for direct parking entry and exit operations. It exposes slot registration and lookup, starts a session by checking and occupying an available slot, quotes a session through `PricingService`, and ends a session by setting its exit time, marking it completed, releasing its slot, and calculating the final result. The Facade hides coordination between slot state, sessions, and pricing behind a focused API.

## UML Design Explanation

The repository includes Mermaid diagrams that document the implemented design:

| Diagram | Design explanation |
|---|---|
| `class-diagram.mmd` | Shows domain composition and associations, `ParkingService`–`PricingService` collaboration, pricing and slot-state implementations, repository implementations, JSON delegation, event publication, and the `ParkingException` hierarchy. |
| `usecase-diagram.mmd` | Shows Driver, Attendant, and Administrator use cases including login, slot viewing, reservations, check-in, checkout, receipts, notifications, slot management, pricing configuration, user management, and reports. |
| `navigation-map.mmd` | Shows Splash-to-Login navigation, role-based dashboards, dashboard features, and logout routing. |
| `sequence-reservation.mmd` | Shows reservation form input, pricing quote, conflict checking, confirmation, and confirmation ID generation. |
| `sequence-vehicle-entry.mmd` | Shows ticket lookup, availability verification, slot occupation, session creation, persistence, and QR ticket ID return. |
| `sequence-vehicle-exit-payment.mmd` | Shows session lookup, ten-step pricing, payment-method selection, and receipt generation. |
| `state-parking-slot.mmd` | Shows `AVAILABLE`, `OCCUPIED`, and `OUT_OF_SERVICE` states, valid lifecycle transitions, and invalid-transition exceptions. |

Together, these views connect static structure, user requirements, screen navigation, runtime interactions, and the lifecycle of a parking slot.

## OOP Examples

The following examples are the implemented evidence documented in `OOM_PATTERN_MAPPING.md`:

| OOP concept | Exact implemented example |
|---|---|
| **Encapsulation** | Domain fields are private and accessed through methods in `ParkingSession`, `ParkingSlot`, `User`, `PricingConfig`, and related models. `ParkingSession` validates duration and clamps loyalty/grace inputs. |
| **Inheritance** | `ParkingException` is the abstract runtime base for `InvalidSlotStateException`, `SlotUnavailableException`, `ReservationConflictException`, `DataPersistenceException`, and `InvalidPaymentException`. |
| **Polymorphism** | `PricingService` iterates over `List<PricingStrategy>`; `ParkingSlot` delegates through `SlotState`; `ReportService` accepts `DataExporter`; repositories are used through `Repository<T>`. |
| **Abstraction** | `PricingStrategy`, `SlotState`, `Repository<T>`, `DataExporter`, and `PaymentProcessor` expose stable contracts while hiding implementations. |

## Implemented Design Patterns

Exactly the following eleven patterns and architectural patterns are implemented. The Decorator pattern is not claimed.

| Pattern | Implemented classes and responsibilities |
|---|---|
| **1. Strategy** | `PricingStrategy` and `BaseRateStrategy`, `SurgeStrategy`, `PeakHourStrategy`, `WeekendHolidayStrategy`, `EvDiscountStrategy`, `ReservationFeeStrategy`, `PromoCodeStrategy`, `LoyaltyRedemptionStrategy`, `OverstayPenaltyStrategy`, and `TaxStrategy` encapsulate interchangeable pricing rules. |
| **2. State** | `ParkingSlot`, `SlotState`, `AvailableState`, `OccupiedState`, `OutOfServiceState`, and `SlotStates` model and enforce slot lifecycle behavior. |
| **3. Factory** | `RepositoryFactory` creates repository implementations; `SlotStates.of` creates concrete slot states. |
| **4. Repository** | `Repository<T>`, `InMemoryRepositoryImpl`, and `JsonRepositoryImpl` separate persistence access from domain use cases. |
| **5. Singleton** | `AdminService.shared()`, `AuditService.shared()`, `AppContext.EVENT_BUS`, and static `ParkingAppState` provide shared application services or state. |
| **6. Observer** | `EventBus`, `subscribe`, `publish`, and `NotificationEvent` provide event-driven notifications to subscribers. |
| **7. Adapter** | `JsonRepositoryImpl` adapts JSON persistence to `Repository<T>` and delegates storage operations to `InMemoryRepositoryImpl`. |
| **8. Facade** | `ParkingService` presents one entry, exit, and pricing API over slots, sessions, state transitions, and pricing. |
| **9. MVVM** | FXML views/controllers bind to `LoginViewModel`, `CheckInViewModel`, `CheckoutPaymentViewModel`, `ReservationViewModel`, and related view models. |
| **10. Front Controller** | `NavigationManager` is the single FXML navigation and role-routing entry point. |
| **11. Service Layer** | `ReservationService`, `PricingService`, `AdminService`, `AuditService`, `ReportService`, and `BackupService` centralize use-case operations. |

## Pricing Chain and Strategy Handling

`PricingConfig` locks the hourly rates to the following INR values:

| Slot type | Hourly rate |
|---|---:|
| Compact | ₹30.00 |
| Standard | ₹50.00 |
| Large | ₹70.00 |
| Accessible | ₹50.00 |
| Electric | ₹60.00 |

`PricingService` constructs and executes the exact ten-step chain below, in order:

1. `BaseRateStrategy`
2. `SurgeStrategy`
3. `PeakHourStrategy`
4. `WeekendHolidayStrategy`
5. `EvDiscountStrategy`
6. `ReservationFeeStrategy`
7. `PromoCodeStrategy`
8. `LoyaltyRedemptionStrategy`
9. `OverstayPenaltyStrategy`
10. `TaxStrategy`

Each strategy receives the current `ParkingSession` and current amount. The result is clamped at zero and rounded to two decimal places before the next strategy runs. `PricingService` records a `PricingLineItem` for every step and returns a `PricingResult`. The tenth step, `TaxStrategy`, applies the configured 18% GST after the preceding nine steps. The implementation also caps discounts at a free total rather than producing a negative price, preserves loyalty points during quoting, limits redemption to available points, and applies overstay charges after the configured grace period.

The pricing tests demonstrate the chain with a two-hour Standard session: the locked base rate is ₹100.00, the Tax line is ₹18.00, and the total is ₹118.00. The tests also cover surge, peak-hour, weekend, electric discount, reservation fee, promo code, loyalty redemption, overstay penalty, negative duration rejection, missing-slot fallback, and discount clamping.

## JUnit Coverage and Custom Exceptions

The test suite is organized around the implemented application layers and behavior:

| Test area | Representative coverage |
|---|---|
| Pricing | Locked rates, ten line items, GST, all pricing modifiers, overstay grace, negative duration rejection, missing-slot pricing, discount clamping, and non-mutating quotes. |
| Slot state | Valid lifecycle transitions and rejection of illegal occupation, release, and maintenance transitions. |
| Reservations and administration | Reservation behavior, conflicts, and administrative service operations. |
| Repositories and persistence | In-memory/JSON round trips, seed data, and backup behavior. |
| UI support | Event bus, navigation, validation, login, price breakdown, and slot-map view models. |

The project defines `ParkingException` as the common abstract runtime base and the following eleven custom exceptions:

| Exception | Purpose |
|---|---|
| `AccountLockedException` | Represents an account-lock condition in authentication flows. |
| `AuthenticationException` | Represents authentication failures. |
| `DataPersistenceException` | Reports JSON repository read/write failures and backup failures. |
| `InsufficientFundsException` | Represents insufficient funds during payment handling. |
| `InvalidPaymentException` | Represents invalid payment processing paths. |
| `InvalidSlotStateException` | Rejects illegal `ParkingSlot` state transitions. |
| `NotFoundException` | Represents required domain data that cannot be found. |
| `OverstayException` | Represents an overstay condition. |
| `ReservationConflictException` | Rejects a second confirmed reservation for the same slot. |
| `ReservationExpiredException` | Represents an expired reservation. |
| `SlotUnavailableException` | Rejects entry into missing, occupied, or unavailable slots. |

All eleven concrete exceptions inherit from `ParkingException`, allowing domain errors to share a consistent runtime hierarchy while retaining specific meanings at call sites.

## Conclusion

The Smart Parking Management System demonstrates a complete object-oriented design for a role-based parking application. Its layered UI, service, repository, and domain organization keeps responsibilities focused, while `ParkingService` provides a clear Facade for core entry and exit operations. The Mermaid diagrams document the system from static classes through use cases, navigation, sequences, and state transitions. Encapsulation, inheritance, polymorphism, and abstraction are visible in concrete classes and contracts rather than being theoretical labels. The exact eleven implemented patterns support pricing variation, state safety, persistence substitution, event notifications, navigation, and use-case organization. Finally, locked INR rates, a deterministic ten-step Strategy chain, JUnit coverage, and eleven domain-specific exceptions make the business rules explicit, testable, and suitable for academic evaluation.
