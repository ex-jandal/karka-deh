
package com.karka_deh.models.requests;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public class CommentRequest {
  @NotNull(message = "post_id is required")
  @JsonAlias("post_id")
  UUID postId;

  @NotNull(message = "content is required")
  String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public UUID getPostId() {
    return postId;
  }

  public void setPostId(UUID postId) {
    this.postId = postId;
  }

}
