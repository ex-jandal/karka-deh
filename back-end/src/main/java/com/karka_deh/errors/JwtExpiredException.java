package com.karka_deh.errors;

public class JwtExpiredException extends RuntimeException {
  public JwtExpiredException() {
    super("expired jwt token");
  }

}
