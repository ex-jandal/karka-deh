package com.karka_deh.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karka_deh.Models.User;
import com.karka_deh.Repos.UserRepo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserRepo userRepo;

  public UserController(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @GetMapping
  public List<User> listUsers() {
    return this.userRepo.findAll();
  }

  @GetMapping("/{id}")
  public Optional<User> getByTitle(@PathVariable UUID uuid) {
    return this.userRepo.findById(uuid);
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
    this.userRepo.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
