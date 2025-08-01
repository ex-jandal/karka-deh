package com.karka_deh.models.responses;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PostResponse {
  @JsonAlias("created_at")
  private LocalDateTime createdAt;

  @JsonAlias("author_id")
  private UUID authorId;

  private UUID id;

  private String title;
  private String content;
  private String slug;

  public UUID getAuthorId() {
    return authorId;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

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
