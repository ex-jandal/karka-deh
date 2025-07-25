package com.karka_deh.controllers.auth;

import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import com.karka_deh.jwt.JwtCookieUtil;
import com.karka_deh.jwt.JwtUtil;
import com.karka_deh.models.reqs.AuthCred;
import com.karka_deh.repos.UserRepo;

@RestController
@RequestMapping("/auth")
public class Login {

  private final JwtUtil jwtUtil;
  private final UserRepo userRepo;

  public Login(JwtUtil jwtUtil, UserRepo userRepo) {
    this.jwtUtil = jwtUtil;
    this.userRepo = userRepo;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthCred req, HttpServletResponse response) {
    try {
      if (!this.userRepo.existsByUsername(req.getUsername())) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "no username"));
      }

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
