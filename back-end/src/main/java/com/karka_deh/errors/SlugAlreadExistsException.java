package com.karka_deh.errors;

public class SlugAlreadExistsException extends RuntimeException {
  public SlugAlreadExistsException(String slug) {
    super("Slug already exists: " + slug);
  }

}
