package com.karka_deh.models.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignupRequest {

  @NotNull(message = "username is required")
  @Size(min = 3, max = 64)
  @Schema(description = "Username", example = "abdullah")
  private String username;

  /*
   * @NotNull(message = "email is required")
   * 
   * @Email(message = "email is invalid")
   * private String email;
   */

  @NotNull(message = "password is required")
  @Size(min = 8, max = 128)
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
