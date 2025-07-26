package com.karka_deh.models.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public class CommentEntity {
  @NotNull(message = "id is required")
  String id;

  @NotNull(message = "post_id is required")
  @JsonAlias("post_id")
  String postId;

  @NotNull(message = "author_id is required")
  @JsonAlias("author_id")
  String authorId;

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

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
