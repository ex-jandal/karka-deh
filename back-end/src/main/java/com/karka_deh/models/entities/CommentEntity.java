package com.karka_deh.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public class CommentEntity {
  @NotNull(message = "id is required")
  UUID id;

  @NotNull(message = "post_id is required")
  @JsonAlias("post_id")
  UUID postId;

  @NotNull(message = "author_id is required")
  @JsonAlias("author_id")
  UUID authorId;

  @NotNull(message = "content is required")
  String content;

  @JsonAlias("created_at")
  LocalDateTime createdAt;

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public UUID getAuthorId() {
    return authorId;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }

  public UUID getPostId() {
    return postId;
  }

  public void setPostId(UUID postId) {
    this.postId = postId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "CommentEntity {" +
        "id='" + this.id + '\'' +
        ", authorId='" + this.authorId + '\'' +
        ", postId='" + this.postId + '\'' +
        ", content='" + this.content + '\'' +
        ", createdAt=" + this.createdAt +
        '}';
  }

}
