package com.parking.app.viewmodel;

import com.parking.domain.model.User;
import com.parking.service.PersistenceState;
import com.parking.service.SeedDataLoader;

import java.util.Optional;

public final class LoginViewModel {
  private final PersistenceState state;

  public LoginViewModel() {
    this(new SeedDataLoader().load());
  }

  public LoginViewModel(PersistenceState state) {
    this.state = state;
  }

  public Optional<User> authenticate(String userId) {
    if (userId == null || userId.isBlank()) {
      return Optional.empty();
    }
    return state.getUsers().stream()
        .filter(user -> userId.trim().equalsIgnoreCase(user.getId()))
        .findFirst();
  }
}
