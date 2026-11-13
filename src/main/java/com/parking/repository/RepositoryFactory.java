package com.parking.repository;

import com.parking.domain.repository.Repository;

public class RepositoryFactory { public <T> Repository<T> repositoryFor(Class<T> type) { throw new UnsupportedOperationException("Repositories are introduced in Phase 3"); } }
