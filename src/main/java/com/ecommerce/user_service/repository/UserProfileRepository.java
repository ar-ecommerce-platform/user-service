package com.ecommerce.user_service.repository;

import com.ecommerce.user_service.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Data access for {@link UserProfile}. */
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

  Optional<UserProfile> findByEmail(String email);

  boolean existsByEmail(String email);
}
