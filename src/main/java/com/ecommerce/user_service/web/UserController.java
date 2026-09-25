package com.ecommerce.user_service.web;

import com.ecommerce.user_service.service.UserProfileService;
import com.ecommerce.user_service.web.dto.CreateUserRequest;
import com.ecommerce.user_service.web.dto.UserResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST endpoints for user profiles. */
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserProfileService service;

  public UserController(UserProfileService service) {
    this.service = service;
  }

  /** Creates (or returns the existing) profile for the given email. Internal, not in the docs. */
  @Hidden
  @PostMapping
  public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    UserResponse body = UserResponse.from(service.createOrGet(request));
    return ResponseEntity.created(URI.create("/users/" + body.id())).body(body);
  }

  /**
   * The caller's own profile, created on first access. {@code X-User-Id} is set by the gateway from
   * the verified token. The other endpoints here are internal: the gateway does not expose them.
   */
  @GetMapping("/me")
  public UserResponse me(@RequestHeader("X-User-Id") String userId) {
    return UserResponse.from(service.createOrGet(new CreateUserRequest(userId, userId)));
  }

  @Hidden
  @GetMapping
  public List<UserResponse> list() {
    return service.findAll().stream().map(UserResponse::from).toList();
  }

  @Hidden
  @GetMapping("/{id}")
  public UserResponse getById(@PathVariable Long id) {
    return UserResponse.from(service.getById(id));
  }

  @Hidden
  @GetMapping("/by-email")
  public UserResponse getByEmail(@RequestParam String email) {
    return UserResponse.from(service.getByEmail(email));
  }
}
