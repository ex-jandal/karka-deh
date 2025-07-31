package com.karka_deh.services;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.karka_deh.errors.SlugAlreadExistsException;
import com.karka_deh.errors.UserNotFoundException;
import com.karka_deh.models.mappers.PostMapper;
import com.karka_deh.models.repo_find.Posts;
import com.karka_deh.models.requests.PostRequest;
import com.karka_deh.models.responses.PostResponse;
import com.karka_deh.repos.PostRepo;

@Service
public class PostService {
  private final PostRepo postRepo;
  private final UserService userService;

  @Autowired
  private final PostMapper postMapper;

  public PostService(PostRepo postRepo, UserService userService, PostMapper postMapper) {
    this.postRepo = postRepo;
    this.userService = userService;
    this.postMapper = postMapper;
  }

  public Page<PostResponse> getAllUserPosts(String username, Pageable pageable) {
    UUID userId = this.userService.getUserId(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return this.postRepo.findAllPostsByUserId(userId, pageable).toPage(this.postMapper::toPostResponse);
  }

  public Page<PostResponse> searchPost(String username, String keyword, Pageable pageable) {
    return this.postRepo.searchPosts(keyword, pageable)
        .toPage(this.postMapper::toPostResponse);
  }

  public Optional<PostResponse> findBySlug(String slug) {
    return this.postRepo.findBySlug(slug).map(entity -> {
      return this.postMapper.toPostResponse(entity);
    });
  }

  public void createPost(PostRequest postRequest, String username) {
    var slug = PostService.slugify(postRequest.getTitle());

    if (this.postRepo.findBySlug(slug).isPresent()) {
      throw new SlugAlreadExistsException(slug);
    }

    var uuid = UUID.randomUUID();
    var user_id = this.userService.getUserId(username).orElseThrow(() -> new UserNotFoundException(username));
    var postEntity = this.postMapper.toPostEntity(postRequest);

    postEntity.setId(uuid);
    postEntity.setAuthorId(user_id);
    postEntity.setCreatedAt(LocalDateTime.now());
    postEntity.setSlug(slugify(postRequest.getTitle()));

    this.postRepo.save(postEntity);

  }

  private static String slugify(String input) {
    if (input == null || input.isBlank())
      return "";

    // remove accents like é → e
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    String lower = normalized.toLowerCase();

    // replace all non-alphanumeric with hyphens
    String hyphenated = lower.replaceAll("[^a-z0-9]+", "-");

    // remove leading/trailing hyphens
    String cleaned = hyphenated.replaceAll("^-+|-+$", "");

    return cleaned;
  }

}
