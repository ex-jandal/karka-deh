
package com.karka_deh.models.requests;

import com.fasterxml.jackson.annotation.JsonAlias;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CommentRequest {
  @NotNull(message = "post_slug is required")
  @JsonAlias("post_slug")
  @Schema(description = "the post slug where to comment on", example = "first-post-yay")
  String postSlug;

  @NotNull(message = "content is required")
  @Schema(description = "the comment content", example = "java is too much")
  String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPostSlug() {
    return postSlug;
  }

  public void setPostSlug(String postSlug) {
    this.postSlug = postSlug;
  }

}
