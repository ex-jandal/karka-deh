package com.karka_deh.errors;

public class BadSearchQueryException extends RuntimeException {
  public BadSearchQueryException() {
    super("Bad search query use");
  }

}
