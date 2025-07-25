package com.karka_deh.controllers.auth;

import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import com.karka_deh.jwt.JwtCookieUtil;
import com.karka_deh.jwt.JwtUtil;
import com.karka_deh.models.requests.auth.LoginRequest;

@RestController
@RequestMapping("/auth")
public class Login {

  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  public Login(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletResponse response) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

      // if we’re here, that means authentication was successful
      String token = jwtUtil.generate(req.getUsername());
      Cookie jwtCookie = JwtCookieUtil.createJwtCookie(token, 3600);
      response.addCookie(jwtCookie);

      return ResponseEntity.ok(Map.of(
          "message", "Login successful",
          "token", token));

    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Map.of("error", "Invalid username or password"));
    }
  }
}
