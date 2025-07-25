
package com.karka_deh.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // get the jwt cookie, if not, get the `Authorization` header
    String jwt = JwtCookieUtil.getJwtFromCookie(request);
    if (jwt == null) {
      String authHeader = request.getHeader("Authorization");
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        jwt = authHeader.substring(7);
      }
    }

    if (jwt != null) {
      try {
        Claims claims = jwtUtil.parse(jwt).getBody();

        var expiration = claims.getExpiration();
        if (expiration != null && expiration.before(new Date())) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }

        String username = claims.getSubject();

        // store the user credentials, username is the only thing needed, because it's
        // jwt, no need for password and roles
        var principal = new User(username, "", java.util.List.of());

        var authToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
      } catch (JwtException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    // don't filter the login, it should create a new one, no filtering
    return path.equals("/auth/login");
  }
}
