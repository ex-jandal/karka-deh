package com.karka_deh.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RequestMapping("/comments")
@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Comments")
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @Operation(summary = "get the user comments for a post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)) }),
  })
  @GetMapping("/me/{slug}")
  public ResponseEntity<Page<CommentResponse>> getUserPostComments(
      @PathVariable("slug") String slug,
      Authentication auth,
      Pageable pageable) {
    var comments = this.commentService.getUserPostComments(slug, auth.getName(), pageable);

    return ResponseEntity.ok(comments);
  }

  @Operation(summary = "get all the comments for a post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)) }),
  })
  @GetMapping("/all/{slug}")
  public ResponseEntity<Page<CommentResponse>> getAllPostComments(
      @PathVariable("slug") String slug,
      Authentication auth,
      Pageable pageable) {
    var comments = this.commentService.getAllPostComments(slug, pageable);

    return ResponseEntity.ok(comments);
  }

  @Operation(summary = "create a new comment")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201"),
      @ApiResponse(responseCode = "404", description = "the user was not found")

  })
  @PostMapping
  public ResponseEntity<?> createComment(
      @Valid @RequestBody CommentRequest commentRequest,
      Authentication auth) {
    this.commentService.createComment(commentRequest, auth.getName());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
