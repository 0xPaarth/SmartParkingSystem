package com.parking.app.viewmodel;

import com.parking.domain.enums.SlotState;
import com.parking.domain.enums.SlotType;
import com.parking.domain.model.ParkingLevel;
import com.parking.domain.model.ParkingSlot;
import com.parking.service.PersistenceState;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public final class SlotMapViewModel {
  public static final String ALL = "ALL";
  private final PersistenceState state;
  private final IntegerProperty level = new SimpleIntegerProperty(1);
  private final ObjectProperty<String> slotType = new SimpleObjectProperty<>(ALL);
  private final ObjectProperty<ParkingSlot> selectedSlot = new SimpleObjectProperty<>();
  private final ObservableList<ParkingSlot> visibleSlots = FXCollections.observableArrayList();

  public SlotMapViewModel(PersistenceState state) {
    this.state = Objects.requireNonNull(state);
    level.addListener((obs, oldValue, newValue) -> refresh());
    slotType.addListener((obs, oldValue, newValue) -> refresh());
    refresh();
  }

  public PersistenceState getState() { return state; }
  public IntegerProperty levelProperty() { return level; }
  public ObjectProperty<String> slotTypeProperty() { return slotType; }
  public ObjectProperty<ParkingSlot> selectedSlotProperty() { return selectedSlot; }
  public ObservableList<ParkingSlot> visibleSlots() { return visibleSlots; }
  public ObservableList<String> levels() {
    return FXCollections.observableArrayList(state.getParkingLot().getLevels().stream()
        .map(ParkingLevel::getLevelNumber).map(String::valueOf).toList());
  }
  public ObservableList<String> slotTypes() {
    ObservableList<String> types = FXCollections.observableArrayList(ALL);
    for (SlotType type : SlotType.values()) types.add(type.name());
    return types;
  }
  public void refresh() {
    visibleSlots.setAll(state.getParkingLot().getLevels().stream()
        .filter(item -> item.getLevelNumber() == level.get())
        .flatMap(item -> item.getSlots().stream())
        .filter(slot -> ALL.equals(slotType.get()) || slot.getType().name().equals(slotType.get()))
        .toList());
  }
  public String stateLabel(ParkingSlot slot) { return slot == null ? "" : slot.getState().name(); }
  public SlotState state(ParkingSlot slot) { return slot == null ? null : slot.getState(); }
}
