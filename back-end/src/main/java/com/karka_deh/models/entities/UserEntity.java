package com.karka_deh.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

// import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class UserEntity {

  @NotNull(message = "id is required")
  private UUID id;

  @NotNull(message = "username is required")
  @Size(min = 3, max = 64)
  private String username;

  // @NotNull(message = "email is required")
  // @Email(message = "email is invalid")
  // private String email;

  @NotNull(message = "password_hash is required")
  @JsonAlias("password_hash")
  private String passwordHash;

  @Null
  @JsonAlias("created_at")
  private LocalDateTime createdAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;

  }

  @Override
  public String toString() {
    return "UserEntity{" + this.getId() + ", " + this.getUsername() + ", " + this.getPasswordHash() + ", "
        + this.getCreatedAt().toString() + "}";

  }

  /*
   * // TODO: maybe we can do these too?
   * 
   * @Null
   * String bio;
   *
   * 
   * @JsonAlias("avatar_base64")
   * String avatarBase64
   * 
   */
}
