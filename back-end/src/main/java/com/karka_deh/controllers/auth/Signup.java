
package com.karka_deh.controllers.auth;

import org.springframework.http.*;
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
public class Signup {

  private final JwtUtil jwtUtil;
  private final UserRepo userRepo;

  public Signup(UserRepo userRepo, JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    this.userRepo = userRepo;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody AuthCred req, HttpServletResponse response) {
    try {
      if (this.userRepo.existsByUsername(req.getUsername())) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("error", "Username already taken"));
      }

      this.userRepo.createUser(req.getUsername(),
          req.getPassword());

      String token = jwtUtil.generate(req.getUsername());
      Cookie jwtCookie = JwtCookieUtil.createJwtCookie(token, 3600);
      response.addCookie(jwtCookie);

      return ResponseEntity.ok(Map.of("message", "Signup successful"));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("error", "Something went wrong"));
    }
  }
}
