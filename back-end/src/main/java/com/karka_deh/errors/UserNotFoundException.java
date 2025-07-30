package com.karka_deh.errors;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("User not found: " + username);
  }

}
