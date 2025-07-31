package com.karka_deh.models.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {

  @NotNull(message = "username is required")
  @Schema(description = "Username", example = "abdullah")
  private String username;

  /*
   * @NotNull(message = "email is required")
   * 
   * private String email;
   */

  @NotNull(message = "password is required")
  @Schema(description = "User password", example = "verysecure")
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
