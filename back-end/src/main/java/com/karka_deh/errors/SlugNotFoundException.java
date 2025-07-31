package com.karka_deh.errors;

public class SlugNotFoundException extends RuntimeException {
  public SlugNotFoundException(String slug) {
    super("Slug not found");
  }

}
