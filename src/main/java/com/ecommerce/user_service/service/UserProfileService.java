package com.ecommerce.user_service.service;

import com.ecommerce.user_service.entity.UserProfile;
import com.ecommerce.user_service.repository.UserProfileRepository;
import com.ecommerce.user_service.web.dto.CreateUserRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Business operations for user profiles. */
@Service
public class UserProfileService {

  private final UserProfileRepository repository;

  public UserProfileService(UserProfileRepository repository) {
    this.repository = repository;
  }

  /** Creates a profile, or returns the existing one for that email (idempotent provisioning). */
  @Transactional
  public UserProfile createOrGet(CreateUserRequest request) {
    return repository
        .findByEmail(request.email())
        .orElseGet(() -> repository.save(new UserProfile(request.email(), request.displayName())));
  }

  @Transactional(readOnly = true)
  public UserProfile getById(Long id) {
    return repository.findById(id).orElseThrow(() -> notFound("id", String.valueOf(id)));
  }

  @Transactional(readOnly = true)
  public UserProfile getByEmail(String email) {
    return repository.findByEmail(email).orElseThrow(() -> notFound("email", email));
  }

  @Transactional(readOnly = true)
  public List<UserProfile> findAll() {
    return repository.findAll();
  }

  private static UserNotFoundException notFound(String field, String value) {
    return new UserNotFoundException("No user profile with " + field + " '" + value + "'");
  }
}
