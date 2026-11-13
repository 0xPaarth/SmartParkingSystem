package com.parking.repository;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.domain.exception.DataPersistenceException;
import com.parking.domain.repository.Repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonRepositoryImpl<T> implements Repository<T> {
  private final Path file;
  private final Class<T> type;
  private final ObjectMapper mapper;
  private final InMemoryRepositoryImpl<T> delegate = new InMemoryRepositoryImpl<>();

  public JsonRepositoryImpl(Path file, Class<T> type) { this(file, type, new ObjectMapper().findAndRegisterModules()); }
  public JsonRepositoryImpl(Path file, Class<T> type, ObjectMapper mapper) {
    this.file = file; this.type = type; this.mapper = mapper;
    load();
  }
  @Override public Optional<T> findById(String id) { return delegate.findById(id); }
  @Override public List<T> findAll() { return delegate.findAll(); }
  @Override public T save(T entity) { T saved=delegate.save(entity); persist(); return saved; }
  @Override public void deleteById(String id) { delegate.deleteById(id); persist(); }

  private void load() {
    if (!Files.exists(file) || isEmpty(file)) return;
    try {
      JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, type);
      List<T> values = mapper.readValue(file.toFile(), listType);
      for (T value : values) delegate.save(value);
    } catch (IOException | RuntimeException e) {
      throw new DataPersistenceException("Unable to read repository JSON: " + file, e);
    }

  }
  private boolean isEmpty(Path path) {
    try {
      return Files.size(path) == 0;
    } catch (IOException e) {
      throw new DataPersistenceException("Unable to inspect repository JSON: " + file, e);
    }
  }
  private void persist() {
    try {
      Path parent=file.toAbsolutePath().getParent();
      if (parent != null) Files.createDirectories(parent);
      mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), new ArrayList<>(delegate.findAll()));
    } catch (IOException | RuntimeException e) {
      throw new DataPersistenceException("Unable to write repository JSON: " + file, e);
    }
  }
}
