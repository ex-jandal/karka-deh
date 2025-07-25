package com.karka_deh.models.requests.auth;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {

  @NotNull(message = "username is required")
  private String username;

  /*
   * @NotNull(message = "email is required")
   * 
   * private String email;
   */

  @NotNull(message = "password is required")
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
