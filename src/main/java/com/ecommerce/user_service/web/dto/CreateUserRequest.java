package com.ecommerce.user_service.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Request body for creating a user profile. */
public record CreateUserRequest(@NotBlank @Email String email, @NotBlank String displayName) {}
