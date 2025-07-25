
package com.karka_deh.controllers.auth;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import com.karka_deh.jwt.JwtCookieUtil;
import com.karka_deh.jwt.JwtUtil;
import com.karka_deh.models.requests.auth.SignupRequest;
import com.karka_deh.repos.UserRepo;
import com.karka_deh.services.UserService;

@RestController
@RequestMapping("/auth")
public class Signup {

  private final JwtUtil jwtUtil;
  private final UserService userService;

  public Signup(UserRepo userRepo, UserService userService, JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest req, HttpServletResponse response) {
    try {
      if (this.userService.existsByUsername(req.getUsername())) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("error", "Username already taken"));
      }

      var user = this.userService.createUser(req.getUsername(),
          req.getPassword());

      String token = jwtUtil.generate(req.getUsername());
      Cookie jwtCookie = JwtCookieUtil.createJwtCookie(token, 3600);
      response.addCookie(jwtCookie);

      return ResponseEntity.ok(Map.of(
          "message", "Signup successful",
          "token", token,
          "id", user.getId(),
          "username", user.getUsername(),
          "created_at", user.getCreatedAt()));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("error", "Something went wrong"));
    }
  }
}
