package com.karka_deh.Controllers;

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

import com.karka_deh.Models.Post;
import com.karka_deh.Repos.PostRepo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
  private final PostRepo postRepo;

  public PostController(PostRepo postRepo) {
    this.postRepo = postRepo;
  }

  @GetMapping
  public List<Post> listPosts() {
    return this.postRepo.findAll();
  }

  @GetMapping("/{title}")
  public Optional<Post> getByTitle(@PathVariable String title) {
    return this.postRepo.findByTitle(title);
  }

  @PostMapping
  public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {
    System.out.println(post);
    this.postRepo.save(post);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
