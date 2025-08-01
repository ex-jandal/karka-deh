package com.karka_deh.errors;

public class PostNotOwnedByUser extends RuntimeException {
  public PostNotOwnedByUser() {
    super("you can not update a post you don not own");
  }
}
