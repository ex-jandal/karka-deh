package com.karka_deh.models.responses;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CommentResponse {
  @JsonAlias("post_id")
  String postId;

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
