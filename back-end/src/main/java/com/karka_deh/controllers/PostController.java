package com.karka_deh.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.requests.PostRequest;
import com.karka_deh.services.PostService;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  // TODO: check if user owns it
  @GetMapping("/{title}")
  public Optional<PostEntity> getByTitle(@PathVariable String title) {
    return this.postService.findByTitle(title);
  }

  @GetMapping("/all")
  public List<PostEntity> getAllUserPosts(Authentication auth) {
    return this.postService.getAllUserPosts(auth.getName());
  }

  @PostMapping
  public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest post, Authentication auth) {
    this.postService.createPost(post, auth.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
