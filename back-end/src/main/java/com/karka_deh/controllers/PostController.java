package com.karka_deh.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.requests.PostRequest;
import com.karka_deh.models.responses.PostResponse;
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

  @GetMapping("/slug/{slug}")
  public ResponseEntity<PostResponse> getBySlug(@PathVariable String slug) {
    var post = this.postService.findBySlug(slug);

    return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  /**
   * a pageable posts
   *
   * /posts/me?page=1&size=10&sort=createdAt,desc
   *
   */
  @GetMapping("/me")
  public Page<PostResponse> getAllUserPosts(Pageable pageable, Authentication auth) {
    return this.postService.getAllUserPosts(auth.getName(), pageable);
  }

  @GetMapping("/search")
  public Page<PostResponse> searchPosts(@RequestParam("q") String keyword, Pageable pageable,
      Authentication auth) {

    return this.postService.searchPost(auth.getName(), keyword, pageable);
  }

  // @PutMapping("/{id}")
  // public ResponseEntity<Void> updatePost(@Valid @RequestBody PostRequest post,
  // @PathVariable String id,
  // Authentication auth) {
  //
  // }
  //
  // @DeleteMapping("/{id}")
  // public ResponseEntity<Void> deletePost(@PathVariable String id,
  // Authentication auth) {
  // }

  @PostMapping
  public ResponseEntity<Void> createPost(@Valid @RequestBody PostRequest post, Authentication auth) {
    this.postService.createPost(post, auth.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
