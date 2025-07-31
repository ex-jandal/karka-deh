package com.karka_deh.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class PostRequest {
  @NotNull(message = "title is required")
  @Schema(description = "the post title (it's also the slug with some adjustments)", example = "who likes java any way?")
  private String title;

  @NotNull(message = "contnet is required")
  @Schema(description = "the post content", example = "seriously, how loves it?")
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
