# Role Matrix

| Feature | Driver | Attendant | Admin |
|---|:---:|:---:|:---:|
| Live Occupancy | ❌ | ✅ | ✅ |
| Walk-in Entry | ❌ | ✅ | ✅ |
| Process Exit | ❌ | ✅ | ✅ |
| Lost Ticket Override | ❌ | ✅ | ✅ |
| Shift Report | ❌ | ✅ | ✅ |
| Maintenance | ❌ | ❌ | ✅ |
| My Reservations | ✅ | ❌ | ✅ |
| Make Reservation | ✅ | ❌ | ✅ |
| My Wallet | ✅ | ❌ | ✅ |
| My Vehicles | ✅ | ❌ | ✅ |
| Self Check-In/Out | ✅ | ❌ | ✅ |
| Loyalty | ❌ | ❌ | ✅ |

The authorization matrix is enforced by `AuthorizationManager`, and route loading is guarded by `NavigationManager`. Navigation also filters sidebar controls per role.
