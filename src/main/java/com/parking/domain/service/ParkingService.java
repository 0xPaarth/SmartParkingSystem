package com.parking.domain.service;

import com.parking.domain.exception.SlotUnavailableException;
import com.parking.domain.model.ParkingSession;
import com.parking.domain.model.ParkingSlot;
import com.parking.domain.model.Vehicle;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingService {
  private final PricingService pricingService;
  private final Map<String, ParkingSlot> slots = new ConcurrentHashMap<>();
  private final Map<String, ParkingSession> sessions = new ConcurrentHashMap<>();

  public ParkingService() { this(new PricingService()); }
  public ParkingService(PricingService pricingService) { this.pricingService = pricingService; }
  public void registerSlot(ParkingSlot slot) { slots.put(slot.getId(), slot); }
  public ParkingSlot findSlot(String id) { return slots.get(id); }
  public ParkingSession startSession(Vehicle vehicle, String slotId) {
    ParkingSlot slot = slots.get(slotId);
    if (slot == null) throw new SlotUnavailableException("Slot is unavailable: " + slotId);
    synchronized (slot) {
      if (slot.getState() != com.parking.domain.enums.SlotState.AVAILABLE)
        throw new SlotUnavailableException("Slot is unavailable: " + slotId);
      slot.occupy();
    }
    ParkingSession session = new ParkingSession();
    session.setId(UUID.randomUUID().toString());
    session.setVehicle(vehicle);
    session.setSlot(slot);
    session.setEntryTime(Instant.now());
    sessions.put(session.getId(), session);
    return session;
  }
  public PricingResult quote(ParkingSession session) { return pricingService.calculate(session); }
  public PricingResult endSession(String sessionId) {
    ParkingSession session = sessions.get(sessionId);
    if (session == null) throw new IllegalArgumentException("Unknown session: " + sessionId);
    if (session.getStatus() == com.parking.domain.enums.SessionStatus.COMPLETED) return quote(session);
    session.setExitTime(Instant.now());
    session.setStatus(com.parking.domain.enums.SessionStatus.COMPLETED);
    if (session.getSlot() != null) session.getSlot().release();
    return quote(session);
  }
  public PricingService getPricingService() { return pricingService; }
}
