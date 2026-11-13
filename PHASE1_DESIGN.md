# Parking Management System — Phase 1 Design

## Scope and principles
A Java 17/JavaFX desktop application for multi-level parking operations, payments, reservations, loyalty, and reporting. Phase 2 supplies a compile-ready skeleton; business behavior is intentionally deferred to Phase 3.

## F1–F5 functional foundations
- **F1 — Access and roles:** splash, login, session timeout/lockout, and role-based navigation.
- **F2 — Parking operations:** levels, typed slots, vehicles, entry/exit sessions, availability, and reservations.
- **F3 — Payments and wallets:** payment methods, receipts, wallet balance, loyalty account, and pricing strategies.
- **F4 — Administration:** configuration, repositories, event bus, and audit-ready transactions.
- **F5 — Reporting and export:** dashboard views plus CSV/PDF export through a provider-neutral interface.
- **N1 (optional):** QR/barcode support is reserved for tickets and passes through ZXing.

## Package layout
`app` bootstraps configuration, sessions, events, and navigation; `domain` contains models, enums, contracts, and exceptions; `repository` and `service` are framework-free; `ui` contains JavaFX-facing code and resources.

## 4. Phase 2 folder tree
```text
src/main/java/com/parking/
├── app/{MainApp,config,context,events,navigation,session}
├── domain/
│   ├── enums
│   ├── exception
│   ├── model/{ParkingLot,ParkingLevel,ParkingSlot,Vehicle,ParkingSession,Reservation,Transaction,User,Wallet,LoyaltyAccount}
│   ├── export
│   ├── payment
│   ├── repository
│   └── service
└── repository
```

The domain, repository, and service packages remain independent of JavaFX. JavaFX is limited to the application bootstrap and future UI/resources packages.

## Diagrams
See the standalone Mermaid sources and the matching embeds in `README.md`.
