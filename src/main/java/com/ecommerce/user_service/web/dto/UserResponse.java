package com.ecommerce.user_service.web.dto;

import com.ecommerce.user_service.entity.UserProfile;
import java.time.Instant;

/** Response view of a user profile. */
public record UserResponse(Long id, String email, String displayName, Instant createdAt) {

  public static UserResponse from(UserProfile profile) {
    return new UserResponse(
        profile.getId(), profile.getEmail(), profile.getDisplayName(), profile.getCreatedAt());
  }
}
