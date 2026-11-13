package com.parking.app.viewmodel;

import com.parking.domain.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginViewModelTest {
  @Test
  void authenticatesSeededUserIds() {
    LoginViewModel viewModel = new LoginViewModel();
    var user = viewModel.authenticate("ADMIN-1");
    assertTrue(user.isPresent());
    assertEquals(Role.ADMINISTRATOR, user.get().getRole());
  }

  @Test
  void rejectsUnknownUserIds() {
    assertTrue(new LoginViewModel().authenticate("unknown").isEmpty());
  }
}
