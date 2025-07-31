package com.karka_deh.models.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public class PostResponse {
  @JsonAlias("created_at")
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
    return "PostResponse{" +
        ", title='" + this.title + '\'' +
        ", content='" + this.content + '\'' +
        ", createdAt=" + this.createdAt +
        '}';
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
