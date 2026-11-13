package com.parking.app.viewmodel;

import com.parking.domain.model.ParkingSlot;
import com.parking.domain.service.AdminService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.Reader;

public final class SlotManagementViewModel {
  private final AdminService service;
  private final ObservableList<ParkingSlot> slots = FXCollections.observableArrayList();
  public SlotManagementViewModel(AdminService service) { this.service = service; refresh(); }
  public ObservableList<ParkingSlot> slots() { return slots; }
  public void refresh() { slots.setAll(service.slots()); }
  public void save(ParkingSlot slot) { service.saveSlot(slot); refresh(); }
  public void delete(ParkingSlot slot) { service.deleteSlot(slot.getId()); refresh(); }
  public void setMaintenance(ParkingSlot slot, boolean value) { service.setMaintenance(slot, value); refresh(); }
  public void importCsv(Reader reader) { service.importSlots(reader); refresh(); }
}
