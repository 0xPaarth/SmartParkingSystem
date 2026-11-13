package com.parking.repository;

import com.parking.domain.model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
  @Test void inMemoryAndJsonRepositoriesRoundTrip() throws Exception {
    Path file = Files.createTempFile("vehicles", ".json");
    try {
        JsonRepositoryImpl<User> json = new JsonRepositoryImpl<>(file, User.class);
        User user = new User();
        user.setId("user-1");
        user.setName("Test User");
        json.save(user);
        assertEquals("Test User", json.findById("user-1").orElseThrow().getName());
        assertEquals(1, new JsonRepositoryImpl<>(file, User.class).findAll().size());
    } finally {
      Files.deleteIfExists(file);
    }
  }

}
