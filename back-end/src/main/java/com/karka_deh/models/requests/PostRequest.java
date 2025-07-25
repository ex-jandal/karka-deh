package com.karka_deh.models.requests;

import jakarta.validation.constraints.NotNull;

public class PostRequest {
  @NotNull(message = "title is required")
  private String title;

  @NotNull(message = "contnet is required")
  private String content;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Post{" +
        ", title='" + this.title + '\'' +
        ", content='" + this.content + '\'' +
        '}';
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

}
