package com.ecommerce.user_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ecommerce.user_service.entity.UserProfile;
import com.ecommerce.user_service.repository.UserProfileRepository;
import com.ecommerce.user_service.web.dto.CreateUserRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserProfileServiceTest {

  private UserProfileRepository repository;
  private UserProfileService service;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(UserProfileRepository.class);
    service = new UserProfileService(repository);
  }

  @Test
  void createOrGet_savesWhenEmailIsNew() {
    CreateUserRequest request = new CreateUserRequest("ada@example.com", "Ada");
    Mockito.when(repository.findByEmail("ada@example.com")).thenReturn(Optional.empty());
    Mockito.when(repository.save(Mockito.any(UserProfile.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    UserProfile result = service.createOrGet(request);

    assertThat(result.getEmail()).isEqualTo("ada@example.com");
    Mockito.verify(repository).save(Mockito.any(UserProfile.class));
  }

  @Test
  void createOrGet_returnsExistingWhenEmailKnown() {
    UserProfile existing = new UserProfile("ada@example.com", "Ada");
    Mockito.when(repository.findByEmail("ada@example.com")).thenReturn(Optional.of(existing));

    UserProfile result = service.createOrGet(new CreateUserRequest("ada@example.com", "Ada L."));

    assertThat(result).isSameAs(existing);
    Mockito.verify(repository, Mockito.never()).save(Mockito.any());
  }

  @Test
  void getById_throwsWhenMissing() {
    Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getById(99L)).isInstanceOf(UserNotFoundException.class);
  }
}
