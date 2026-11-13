package com.parking.repository;

import com.parking.domain.repository.Repository;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryRepositoryImpl<T> implements Repository<T> {
  private final LinkedHashMap<String, T> entities = new LinkedHashMap<>();

  @Override public Optional<T> findById(String id) { return Optional.ofNullable(entities.get(id)); }
  @Override public List<T> findAll() { return new ArrayList<>(entities.values()); }
  @Override public T save(T entity) {
    Objects.requireNonNull(entity, "entity must not be null");
    String id = idOf(entity);
    if (id == null || id.isBlank()) throw new IllegalArgumentException("entity id must not be blank");
    entities.put(id, entity);
    return entity;
  }
  @Override public void deleteById(String id) { entities.remove(id); }

  private String idOf(T entity) {
    try {
      Method getter = entity.getClass().getMethod("getId");
      return (String) getter.invoke(entity);
    } catch (ReflectiveOperationException | ClassCastException e) {
      throw new IllegalArgumentException("Entity must expose getId() returning String", e);
    }
  }
}
