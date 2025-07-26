package com.karka_deh.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karka_deh.models.requests.CommentRequest;
import com.karka_deh.models.responses.CommentResponse;
import com.karka_deh.services.CommentService;

import jakarta.validation.Valid;

@RequestMapping("/comments")
@RestController
public class Comment {
  private final CommentService commentService;

  public Comment(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/all/{post_id}")
  public List<CommentResponse> getAllPostComments(@PathVariable("post_id") String postId, Authentication auth) {
    return this.commentService.getAllPostComments(UUID.fromString(postId), auth.getName());
  }

  @PostMapping
  public ResponseEntity<?> createComment(@Valid @RequestBody CommentRequest commentRequest, Authentication auth) {
    this.commentService.createComment(commentRequest, auth.getName());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
