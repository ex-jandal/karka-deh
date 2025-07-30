package com.karka_deh.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class PostEntity {
  @NotNull(message = "id is required")
  private UUID id;

  @JsonAlias("author_id")
  @NotNull(message = "author_id is required")
  private UUID authorId;

  @JsonAlias("created_at")
  @Null
  private LocalDateTime createdAt;

  @NotNull(message = "title is required")
  private String title;

  @NotNull(message = "contnet is required")
  private String content;

  @NotNull(message = "slug is required")
  private String slug;

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Post{" +
        "id='" + this.id + '\'' +
        ", authorId='" + this.authorId + '\'' +
        ", title='" + this.title + '\'' +
        ", content='" + this.content + '\'' +
        ", createdAt=" + this.createdAt +
        '}';
  }

  public UUID getAuthorId() {
    return authorId;
  }

  public UUID getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
}
