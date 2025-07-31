package com.karka_deh.models.responses;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CommentResponse {
  @JsonAlias("post_id")
  UUID postId;

  @JsonAlias("author_id")
  UUID authorId;

  String content;

  public UUID getAuthorId() {
    return authorId;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }

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
