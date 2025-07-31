
package com.karka_deh.models.requests;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CommentRequest {
  @NotNull(message = "post_id is required")
  @JsonAlias("post_id")
  @Schema(description = "the post id where to comment on", example = "e6bb66de-ea2b-4976-bf8c-88f3ed4572e0")
  UUID postId;

  @NotNull(message = "content is required")
  @Schema(description = "the comment content", example = "java is too much")
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
