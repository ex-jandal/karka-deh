
package com.karka_deh.models.requests;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotNull;

public class CommentRequest {
  @NotNull(message = "post_id is required")
  @JsonAlias("post_id")
  String postId;

  @NotNull(message = "content is required")
  String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

}
