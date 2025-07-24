
package com.karka_deh.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class JwtCookieUtil {
  public static final String COOKIE_NAME = "AUTH_TOKEN";

  public static Cookie createJwtCookie(String jwt, int maxAgeSeconds) {
    Cookie cookie = new Cookie(COOKIE_NAME, jwt);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(maxAgeSeconds);
    cookie.setSecure(false); // set to true if you run HTTPS
    // cookie.setSameSite("Strict"); // to prevent CSRF, adjust if needed
    return cookie;
  }

  public static String getJwtFromCookie(HttpServletRequest request) {
    if (request.getCookies() == null)
      return null;
    for (Cookie cookie : request.getCookies()) {
      if (COOKIE_NAME.equals(cookie.getName())) {
        return cookie.getValue();
      }
    }
    return null;
  }
}
