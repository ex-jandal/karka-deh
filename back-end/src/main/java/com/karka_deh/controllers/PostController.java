package com.karka_deh.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karka_deh.models.requests.PostRequest;
import com.karka_deh.models.responses.PostResponse;
import com.karka_deh.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/posts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Posts", description = "Operation related to posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @Operation(summary = "Get a post by the slug (a slug is like the title, but it shows in the url)", description = "Return a single post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the post", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "There no such slug")
  })
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

  @Operation(summary = "get all the posts published by the user", description = """
      this endpoint is pageable, meaning you can pass in queries to limit the returned values, for example:

        to get only 2 posts
        GET localhost:8080/post/me?size=2

        or specify the amount of pages
        GET localhost:8080/post/me?page=2

      """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "The user was not found")
  })
  @GetMapping("/me")
  public ResponseEntity<PagedModel<PostResponse>> getAllUserPosts(Pageable pageable, Authentication auth) {
    var posts = this.postService.getAllUserPosts(auth.getName(), pageable);

    return ResponseEntity.ok(new PagedModel<>(posts));
  }

  @Operation(summary = "search on posts globally", description = "this endpoint is pageable")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)) }),
      @ApiResponse(responseCode = "400", description = "you did not specify the `q` query or it's empty")
  })
  @GetMapping("/search")
  public ResponseEntity<PagedModel<PostResponse>> searchPosts(@RequestParam("q") String keyword, Pageable pageable,
      Authentication auth) {

    var trimmedKeyword = keyword == null ? "" : keyword.trim();

    if (trimmedKeyword.isEmpty()) {
      return ResponseEntity.badRequest().body(new PagedModel<>(Page.empty()));
    }

    var posts = this.postService.searchPost(auth.getName(), keyword, pageable);

    return ResponseEntity.ok(new PagedModel<>(posts));
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

  @Operation(summary = "create a new post")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "post created"),
      // TODO: maybe we can instead suffix it with something instead of error out
      @ApiResponse(responseCode = "409", description = "the slug is already used"),
      @ApiResponse(responseCode = "404", description = "the user was not found")
  })
  @PostMapping
  public ResponseEntity<Void> createPost(@Valid @RequestBody PostRequest post, Authentication auth) {
    this.postService.createPost(post, auth.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
