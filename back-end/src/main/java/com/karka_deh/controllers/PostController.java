package com.karka_deh.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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

import com.karka_deh.errors.BadSearchQueryException;
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

  @Operation(summary = "Get a post by the slug (a slug is like the title, but it shows in the url)", description = """
      It returns a single post that matches the slug

      a slug is the title, but modifed and goes through a filter so it will be in the url

      to get the slug of a post, you can see all the posts from the user using the `/posts/me` to ge the all the posts for the currently logged in user
      or use `/posts/all` to get all of the posts from the database

      once you get the slug, you can then request it alone, again with something like so

      Usage:

          GET http://localhost:8080/posts/first-post-yay


      Return:

          200 => {
                "createdAt": "2025-08-01T18:03:33.94332",
                "title": "very cool post",
                "content": "the best post ever",
                "slug": "very-cool-post"
            }

          404 => NO_CONTENT

          // if the server failed
          500 => {
            "error": "THE_REASON_WHY"
          }
          """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the post", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "There no such slug")
  })
  @GetMapping("/{slug}")
  public ResponseEntity<PostResponse> getBySlug(@PathVariable String slug) {
    var post = this.postService.findBySlug(slug);

    return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "get all the posts published by the user", description = """
      This is exactly the same as `/posts/all` except it filters out just the posts the posted

      this endpoint is pageable, meaning you can pass in queries to limit the returned values, for example:

        to get only 2 posts
        GET localhost:8080/post/me?size=2

        or specify the amount of pages, default is 10
        GET localhost:8080/post/me?page=2

        Usage:

          GET localhost:8080/post/me


        Return:

          200 => {
            "content": [
                {
                  "createdAt": "2025-08-01T18:03:33.94332",
                  "title": "very cool post",
                  "content": "the best post ever",
                  "slug": "very-cool-post"
                },
                ...
            ],
            "page": {
                "size": 20,
                "number": 0,
                "totalElements": 1,
                "totalPages": 1
            }
          }

          404 => {
            "error": "User not found: USERNAME"
          }

          // if the server failed
          500 => {
            "message": "THE_REASON_WHY"
          }

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

  @Operation(summary = "get all the posts on the database", description = """
      Gets all of the posts no matter who posted
      this endpoint is pageable, meaning you can pass in queries to limit the returned values, for example:

        to get only 2 posts
        GET localhost:8080/post/all?size=2

        or specify the amount of pages, default is 10
        GET localhost:8080/post/all?page=2

        Usage:

          GET localhost:8080/post/all

        Return:

          200 => {
            "content": [
                {
                  "createdAt": "2025-08-01T18:03:33.94332",
                  "title": "very cool post",
                  "content": "the best post ever",
                  "slug": "very-cool-post"
                },
                ...
            ],
            "page": {
                "size": 20,
                "number": 0,
                "totalElements": 1,
                "totalPages": 1
            }
          }


          // if the server failed
          500 => {
            "message": "THE_REASON_WHY"
          }

      """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class)) }),
  })
  @GetMapping("/all")
  public ResponseEntity<PagedModel<PostResponse>> getAllPosts(Pageable pageable, Authentication auth) {
    var posts = this.postService.getAllPosts(pageable);

    return ResponseEntity.ok(new PagedModel<>(posts));
  }

  @Operation(summary = "search on posts globally", description = """
        searching is applied on both the post title and the content

        this endpoint is pageable, meaning you can pass in queries to limit the returned values, for example:

        to get only 2 posts
        GET localhost:8080/post/search?q=cool?size=2

        or specify the amount of pages, default is 10
        GET localhost:8080/post/search?q=cool?page=2

        Usage:

          GET localhost:8080/post/search?q=cool

        Return:

          200 => {
            "content": [
                {
                  "createdAt": "2025-08-01T18:03:33.94332",
                  "title": "very cool post",
                  "content": "the best post ever",
                  "slug": "very-cool-post"
                },
                ...
            ],
            "page": {
                "size": 20,
                "number": 0,
                "totalElements": 1,
                "totalPages": 1
            }
          }

          400 => {
            "error": "Bad search query use"
          }

          // if the server failed
          500 => {
            "error": "THE_REASON_WHY"
          }
      """)
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
      throw new BadSearchQueryException();
    }

    var posts = this.postService.searchPost(auth.getName(), keyword, pageable);

    return ResponseEntity.ok(new PagedModel<>(posts));
  }

  @Operation(summary = "update a post", description = """
        update a post with the a new post

        you can either update the post title, or the content

        do note that if you update the title, it will also change the slug

        Usage:

          PUT http://localhost:8080/posts/very-cool-post

          {
            // you can leave them empty to indicate you don't want to update it
            // or put in the same data it was

            "title": "NEW_TITLE",
            "content": "NEW_CONTENT"
          }


        Return:

          // updated successfully
          204 => NO_CONTENT

          // if the user is not found
          404 => {
            "error": "User not found: USERNAME"
          }

          // if the slug is not found
          404 => {
            "error": "Slug not found: SLUG"
          }

          403 => {
            "error": "you can not update a post you don not own"
          }

          // if the server failed
          500 => {
            "error": "THE_REASON_WHY"
          }
      """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "updated successfully"),
      @ApiResponse(responseCode = "404", description = "either the user was not found, or the slug is not found"),
      @ApiResponse(responseCode = "403", description = "the post is not owned by the user"),
  })
  @PutMapping("/{slug}")
  public ResponseEntity<Void> updatePost(
      @Valid @RequestBody PostRequest newPost,
      @PathVariable String slug,
      Authentication auth) {
    this.postService.updateUserPost(auth.getName(), slug, newPost);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "delete a post", description = """

        Usage:

          DELETE http://localhost:8080/posts/very-cool-post

        Return:

          // deleted successfully
          204 => NO_CONTENT

          // if the user is not found
          404 => {
            "error": "User not found: USERNAME"
          }

          // if the slug is not found
          404 => {
            "error": "Slug not found: SLUG"
          }

          403 => {
            "error": "you can not update a post you don not own"
          }

          // if the server failed
          500 => {
            "error": "THE_REASON_WHY"
          }
      """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "deleted successfully"),
      @ApiResponse(responseCode = "404", description = "either the user was not found, or the slug is not found"),
      @ApiResponse(responseCode = "403", description = "the post is not owned by the user"),
  })
  @DeleteMapping("/{slug}")
  public ResponseEntity<Void> deletePost(
      @PathVariable String slug,
      Authentication auth) {
    this.postService.deleteUserPost(auth.getName(), slug);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "create a new post", description = """
        Usage:

          POST http://localhost:8080/posts

          {
            "title": "very cool post",
            "content": "nice"
          }


        Return:

          201 => NO_CONTENT

          409 => {
            "error": "Slug already exists: USERNAME"
          }

          404 => {
            "error": "User not found: USERNAME"
          }


          // if the server failed
          500 => {
            "error": "THE_REASON_WHY"
          }
      """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "post created"),
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
