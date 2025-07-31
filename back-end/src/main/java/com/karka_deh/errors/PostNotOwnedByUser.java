package com.karka_deh.errors;

public class PostNotOwnedByUser extends RuntimeException {
  public PostNotOwnedByUser() {
    super("You can't update a post you don't own");
  }
}
