package com.parking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.domain.exception.DataPersistenceException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BackupService {
  private final ObjectMapper mapper;

  public BackupService() { this(new ObjectMapper().findAndRegisterModules()); }
  public BackupService(ObjectMapper mapper) { this.mapper = mapper; }

  public void exportState(PersistenceState state, Path destination) {
    if (state == null || destination == null) throw new IllegalArgumentException("state and destination are required");
    try {
      Path parent = destination.toAbsolutePath().getParent();
      if (parent != null) Files.createDirectories(parent);
      mapper.writerWithDefaultPrettyPrinter().writeValue(destination.toFile(), state);
    } catch (IOException | RuntimeException e) {
      throw new DataPersistenceException("Unable to export backup: " + destination, e);
    }
  }

  public PersistenceState importState(Path source) {
    if (source == null) throw new IllegalArgumentException("source is required");
    try {
      if (!Files.isRegularFile(source)) throw new IOException("Backup file does not exist");
      PersistenceState state = mapper.readValue(source.toFile(), PersistenceState.class);
      if (state == null) throw new IOException("Backup contained no state");
      return state;
    } catch (IOException | RuntimeException e) {
      throw new DataPersistenceException("Unable to import backup: " + source, e);
    }
  }

  public void exportToJson(PersistenceState state, Path destination) { exportState(state, destination); }
  public PersistenceState importFromJson(Path source) { return importState(source); }
  public void export(PersistenceState state, Path destination) { exportState(state, destination); }
  public PersistenceState importBackup(Path source) { return importState(source); }
}
