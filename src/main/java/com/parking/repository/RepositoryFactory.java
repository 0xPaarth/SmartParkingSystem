package com.parking.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.domain.repository.Repository;
import java.nio.file.Path;

public class RepositoryFactory {
  public <T> Repository<T> repositoryFor(Class<T> type) { return new InMemoryRepositoryImpl<>(); }
  public <T> Repository<T> inMemoryRepository(Class<T> type) { return repositoryFor(type); }
  public <T> Repository<T> jsonRepositoryFor(Path file, Class<T> type) {
    return new JsonRepositoryImpl<>(file, type, new ObjectMapper().findAndRegisterModules());
  }
  public <T> Repository<T> jsonRepository(Path file, Class<T> type) { return jsonRepositoryFor(file, type); }
}
