# Smart Parking Management System
## 10-Slide Presentation Outline

## Slide 1 — Title

### Slide bullets

- Smart Parking Management System
- Object-Oriented Modelling and Design Project
- Java 17 and JavaFX 21
- Group members: ______________________________

### Speaker Notes

Good morning/afternoon. We are presenting our Smart Parking Management System, a Java 17 and JavaFX 21 desktop application developed as an Object-Oriented Modelling and Design project. The system manages parking slots, reservations, vehicle entry and exit, pricing, payments, notifications, reporting, and persistence. We will explain the problem, architecture, object-oriented design, implemented patterns, pricing model, state lifecycle, testing, and future scope.

## Slide 2 — Problem and Objectives

### Slide bullets

- Parking operations involve multiple roles and workflows
- Manual processes can cause slot conflicts and inconsistent charges
- Roles: Driver, Attendant, Administrator
- Objectives: reservations, check-in, checkout, pricing, reports, persistence

### Speaker Notes

The problem is that parking operations combine several users, resources, state changes, reservations, and financial rules. Without a coordinated system, it is difficult to identify available slots, prevent duplicate reservations, apply consistent charges, process payments, and maintain records. Our objectives were to provide role-based workflows for drivers, attendants, and administrators; model parking entities as domain objects; enforce legal slot transitions; support reservations, QR tickets, checkout, receipts, notifications, reports, and JSON persistence; and make pricing deterministic and testable.

## Slide 3 — Technology Stack and Architecture

### Slide bullets

- Java 17, JavaFX 21, Maven
- UI: FXML, controllers, MVVM view models
- Layered flow: UI → Service → Repository → Domain
- `ParkingService` Facade coordinates entry, exit, slots, sessions, and pricing

### Speaker Notes

The application uses Java 17, JavaFX 21, and Maven. The UI is built from FXML views, JavaFX controllers, and MVVM view models. The implementation is organized into four layers: UI, Service, Repository, and Domain. The UI handles interaction and navigation. Services centralize use cases such as reservations, pricing, administration, audit, reports, and backups. Repositories abstract persistence, and the domain contains the parking models and business rules. The ParkingService class acts as a Facade by coordinating slot availability, session creation, session completion, slot release, and pricing through one focused API.

## Slide 4 — Object-Oriented Design

### Slide bullets

- Encapsulation: private domain fields and validated methods
- Inheritance: `ParkingException` hierarchy
- Polymorphism: pricing strategies, states, exporters, repositories
- Abstraction: `PricingStrategy`, `SlotState`, `Repository<T>`, `DataExporter`, `PaymentProcessor`

### Speaker Notes

The project applies the four required object-oriented concepts directly in the implementation. Encapsulation is shown by private fields and controlled methods in classes such as ParkingSession, ParkingSlot, User, and PricingConfig. Inheritance is shown by the common ParkingException base class and its concrete domain exceptions. Polymorphism is used when PricingService iterates over PricingStrategy objects, ParkingSlot delegates to SlotState implementations, ReportService accepts DataExporter implementations, and repositories are accessed through Repository. Abstraction is provided by stable contracts such as PricingStrategy, SlotState, Repository, DataExporter, and PaymentProcessor.

## Slide 5 — Eleven Implemented Patterns

### Slide bullets

- Strategy, State, Factory, Repository
- Singleton, Observer, Adapter, Facade
- MVVM, Front Controller, Service Layer
- No Decorator pattern is claimed

### Speaker Notes

Exactly eleven patterns and architectural patterns are implemented. Strategy is used by the pricing rules. State models parking-slot lifecycle. Factory creates repository and slot-state implementations. Repository separates persistence access from use cases. Singleton provides shared services or state. Observer is implemented by EventBus and NotificationEvent. Adapter is implemented by JsonRepositoryImpl around JSON persistence. Facade is implemented by ParkingService. MVVM separates JavaFX views from presentation logic. NavigationManager acts as the Front Controller. Finally, the Service Layer is represented by ReservationService, PricingService, AdminService, AuditService, ReportService, and BackupService. The project deliberately does not claim a Decorator pattern.

## Slide 6 — Dynamic Pricing, GST, and the 10-Step Chain

### Slide bullets

- Locked hourly rates: Compact ₹30, Standard ₹50, Large ₹70
- Accessible ₹50, Electric ₹60
- `TaxStrategy` applies configured 18% GST last
- `BaseRateStrategy` → `SurgeStrategy` → `PeakHourStrategy` → `WeekendHolidayStrategy` → `EvDiscountStrategy` → `ReservationFeeStrategy` → `PromoCodeStrategy` → `LoyaltyRedemptionStrategy` → `OverstayPenaltyStrategy` → `TaxStrategy`

### Speaker Notes

Pricing starts with locked hourly INR rates: Compact is ₹30.00, Standard is ₹50.00, Large is ₹70.00, Accessible is ₹50.00, and Electric is ₹60.00. PricingService applies exactly ten strategies in order. The chain is BaseRateStrategy, SurgeStrategy, PeakHourStrategy, WeekendHolidayStrategy, EvDiscountStrategy, ReservationFeeStrategy, PromoCodeStrategy, LoyaltyRedemptionStrategy, OverstayPenaltyStrategy, and TaxStrategy. Each step receives the current session and amount, produces a rounded line item, and passes the result to the next step. TaxStrategy applies the configured 18% GST after the preceding nine steps. The implementation prevents negative totals, limits loyalty redemption, preserves points during quoting, and applies overstay charges after grace time.

## Slide 7 — Parking-Slot State Lifecycle

### Slide bullets

- States: `AVAILABLE`, `OCCUPIED`, `OUT_OF_SERVICE`
- Valid transitions: occupy, release, take out of service, restore
- Invalid transitions raise `InvalidSlotStateException`
- State pattern protects slot consistency

### Speaker Notes

ParkingSlot uses the State pattern to protect its lifecycle. A slot begins in the AVAILABLE state. The occupy operation moves it to OCCUPIED, and release returns it to AVAILABLE. An available slot can be taken OUT_OF_SERVICE for maintenance, and restore returns it to AVAILABLE. Illegal operations, such as occupying an out-of-service slot, releasing an available slot, or taking an occupied slot out of service, raise InvalidSlotStateException. This keeps state rules inside the domain instead of scattering them across controllers.

## Slide 8 — JSON and Jackson Persistence

### Slide bullets

- `Repository<T>` defines persistence access
- `InMemoryRepositoryImpl` supports in-memory storage
- `JsonRepositoryImpl` supports JSON files
- Jackson handles serialization and deserialization
- `RepositoryFactory` selects implementations

### Speaker Notes

Persistence is isolated behind the generic Repository interface. InMemoryRepositoryImpl provides fast in-memory storage for application operation and tests. JsonRepositoryImpl adapts JSON file storage to the same repository contract and delegates storage behavior to the in-memory implementation. Jackson performs serialization and deserialization of domain objects. RepositoryFactory creates the required repository implementation, so higher layers do not depend on file-format details. DataPersistenceException reports JSON read, write, and backup failures.

## Slide 9 — Testing and Exceptions

### Slide bullets

- JUnit tests cover pricing, state, services, repositories, persistence, and UI support
- Pricing tests verify locked rates, GST, modifiers, clamping, and non-mutating quotes
- Eleven custom exceptions share `ParkingException`
- Covers authentication, payment, reservation, persistence, slot, and overstay errors

### Speaker Notes

The JUnit suite covers the main business and support areas of the application. Pricing tests verify locked base rates, the ten pricing line items, GST, surge, peak hours, weekends, electric discounts, reservation fees, promo codes, loyalty redemption, overstay grace, negative durations, missing slots, discount clamping, and non-mutating quotes. Other tests cover slot states, administration, reservations, repositories, JSON round trips, backups, seed data, event bus behavior, navigation, validation, login, price breakdowns, and slot-map view models. The exception model contains eleven concrete custom exceptions: AccountLockedException, AuthenticationException, DataPersistenceException, InsufficientFundsException, InvalidPaymentException, InvalidSlotStateException, NotFoundException, OverstayException, ReservationConflictException, ReservationExpiredException, and SlotUnavailableException. All inherit from ParkingException.

## Slide 10 — Conclusion and Future Scope

### Slide bullets

- Complete role-based parking workflow
- Explicit OOP design and exactly 11 implemented patterns
- Deterministic pricing and tested domain rules
- Future scope: production authentication, deployment, analytics, and integrations

### Speaker Notes

In conclusion, the Smart Parking Management System provides a complete role-based workflow for parking operations. Its layered architecture, domain models, state management, pricing strategies, persistence abstraction, and service organization demonstrate practical object-oriented design. The exact eleven implemented patterns are supported by concrete classes, and the key business rules are covered by JUnit tests and domain-specific exceptions. Future scope includes production-grade authentication, deployment to a managed environment, richer analytics, real payment gateways, sensor or IoT integration, and expanded reporting. The current implementation establishes a reliable and extensible academic foundation for those capabilities.
