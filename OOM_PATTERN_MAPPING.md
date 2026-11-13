# OOP and pattern mapping

This report maps the implemented Java code to the required object-oriented concepts and exactly eleven patterns/architectural patterns visible in the final code. No Decorator pattern is claimed.

## OOP concepts

| Concept | Implemented evidence |
|---|---|
| Inheritance | `ParkingException` is the abstract runtime base for `InvalidSlotStateException`, `SlotUnavailableException`, `ReservationConflictException`, `DataPersistenceException`, and `InvalidPaymentException`. |
| Polymorphism | `PricingService` iterates over `List<PricingStrategy>`; `ParkingSlot` delegates through `SlotState`; `ReportService` accepts `DataExporter`; repositories are used through `Repository<T>`. |
| Encapsulation | Domain fields are private and accessed through methods in `ParkingSession`, `ParkingSlot`, `User`, `PricingConfig`, and related models. `ParkingSession` validates duration and clamps loyalty/grace inputs. |
| Abstraction | `PricingStrategy`, `SlotState`, `Repository<T>`, `DataExporter`, and `PaymentProcessor` expose stable contracts while hiding implementations. |

## Exactly eleven implemented patterns

1. **Strategy** — `PricingStrategy` with the ten pricing strategy classes.
2. **State** — `ParkingSlot`, `SlotState`, `AvailableState`, `OccupiedState`, `OutOfServiceState`, and `SlotStates`.
3. **Factory** — `RepositoryFactory` creates repository implementations; `SlotStates.of` creates concrete slot states.
4. **Repository** — `Repository<T>`, `InMemoryRepositoryImpl`, and `JsonRepositoryImpl`.
5. **Singleton** — `AdminService.shared()`, `AuditService.shared()`, `AppContext.EVENT_BUS`, and static `ParkingAppState`.
6. **Observer** — `EventBus`, `subscribe`, `publish`, and `NotificationEvent`.
7. **Adapter** — `JsonRepositoryImpl` adapts JSON persistence to `Repository<T>` and delegates storage operations to `InMemoryRepositoryImpl`.
8. **Facade** — `ParkingService` presents one entry/exit/pricing API over slots, sessions, state transitions, and pricing.
9. **MVVM** — FXML views/controllers bind to `LoginViewModel`, `CheckInViewModel`, `CheckoutPaymentViewModel`, `ReservationViewModel`, and related view models.
10. **Front Controller** — `NavigationManager` is the single FXML navigation and role-routing entry point.
11. **Service Layer** — `ReservationService`, `PricingService`, `AdminService`, `AuditService`, `ReportService`, and `BackupService` centralize use-case operations.

## Exception usage

`InvalidSlotStateException` protects illegal slot transitions; `SlotUnavailableException` protects check-in and parking entry; `ReservationConflictException` prevents duplicate confirmed reservations; `DataPersistenceException` wraps JSON repository and backup I/O failures; and `InvalidPaymentException` is the domain type for invalid payment processing. All inherit from `ParkingException`.
