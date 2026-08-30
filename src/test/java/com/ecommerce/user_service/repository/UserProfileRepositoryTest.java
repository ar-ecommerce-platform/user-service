package com.ecommerce.user_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.user_service.entity.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserProfileRepositoryTest {

  @Autowired private UserProfileRepository repository;

  @BeforeEach
  void seed() {
    repository.save(new UserProfile("ada@example.com", "Ada"));
  }

  @Test
  void findByEmail_returnsMatch() {
    assertThat(repository.findByEmail("ada@example.com"))
        .map(UserProfile::getDisplayName)
        .contains("Ada");
    assertThat(repository.findByEmail("nobody@example.com")).isEmpty();
  }

  @Test
  void existsByEmail_reflectsPersistence() {
    assertThat(repository.existsByEmail("ada@example.com")).isTrue();
    assertThat(repository.existsByEmail("nobody@example.com")).isFalse();
  }
}
