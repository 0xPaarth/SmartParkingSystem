package com.parking.app.viewmodel;

import com.parking.domain.model.Transaction;
import com.parking.service.PersistenceState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TransactionsViewModel {
  private final PersistenceState state;
  private final StringProperty search = new SimpleStringProperty("");
  private final ObservableList<Transaction> filtered = FXCollections.observableArrayList();

  public TransactionsViewModel(PersistenceState state) {
    this.state = state;
    search.addListener((obs, oldValue, newValue) -> refresh());
    refresh();
  }
  public StringProperty searchProperty() { return search; }
  public ObservableList<Transaction> filtered() { return filtered; }
  public void refresh() {
    String query = search.get().trim().toLowerCase();
    filtered.setAll(state.getTransactions().stream().filter(item -> query.isBlank()
        || item.getId().toLowerCase().contains(query)
        || String.valueOf(item.getType()).toLowerCase().contains(query)
        || String.valueOf(item.getStatus()).toLowerCase().contains(query)).toList());
  }
  public void exportCsv(Path destination) throws IOException {
    try (OutputStream output = Files.newOutputStream(destination)) {
      output.write("id,type,status,amount\n".getBytes(StandardCharsets.UTF_8));
      for (Transaction item : filtered) {
        output.write((item.getId() + "," + item.getType() + "," + item.getStatus() + "," + item.getAmount() + "\n")
            .getBytes(StandardCharsets.UTF_8));
      }
    }
  }
}
