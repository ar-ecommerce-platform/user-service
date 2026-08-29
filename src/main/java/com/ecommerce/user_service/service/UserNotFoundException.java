package com.ecommerce.user_service.service;

/** Thrown when a requested user profile does not exist. */
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
