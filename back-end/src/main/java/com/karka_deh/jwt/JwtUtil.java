package com.karka_deh.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.karka_deh.errors.JwtExpiredException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {
  private final Key key;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generate(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
        .signWith(key)
        .compact();
  }

  public Jws<Claims> parse(String jwt) throws JwtExpiredException {
    try {

      return Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(jwt);
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      throw new JwtExpiredException();
    }
  }
}
