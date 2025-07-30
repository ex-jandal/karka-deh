package com.karka_deh.services;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.mappers.PostMapper;
import com.karka_deh.models.repo_find.Posts;
import com.karka_deh.models.requests.PostRequest;
import com.karka_deh.models.responses.PostResponse;
import com.karka_deh.repos.PostRepo;
import com.karka_deh.repos.UserRepo;

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
    // should not happen but handle it anyway
    UUID userId = this.userService.getUserId(username).get();

    int page = pageable.getPageNumber();
    int size = pageable.getPageSize();

    List<PostEntity> entities = this.postRepo.findAllPostsByUserId(userId, page, size);
    int total = this.postRepo.countPostsByUserId(userId);

    List<PostResponse> responses = entities.stream()
        .map(this.postMapper::toPostResponse)
        .toList();

    return new PageImpl<>(responses, pageable, total);
  }

  public Page<PostResponse> searchPost(String username, String keyword, Pageable pageable) {
    int page = pageable.getPageNumber();
    int size = pageable.getPageSize();

    Posts posts = this.postRepo.searchPosts(keyword, page, size);

    int total = posts.getCount();

    List<PostResponse> responses = posts.getPostEntities().stream().map(this.postMapper::toPostResponse)
        .toList();

    return new PageImpl<>(responses, pageable, total);

  }

  public Optional<PostResponse> findBySlug(String title) {
    return this.postRepo.findBySlug(title).map(entity -> {
      return this.postMapper.toPostResponse(entity);
    });
  }

  public boolean createPost(PostRequest postRequest, String username) {
    if (this.findBySlug(postRequest.getTitle()).isPresent()) {
      return false;
    }
    var uuid = UUID.randomUUID();
    var user_id = this.userService.getUserId(username).get();
    var postEntity = this.postMapper.toPostEntity(postRequest);

    postEntity.setId(uuid);
    postEntity.setAuthorId(user_id);
    postEntity.setCreatedAt(LocalDateTime.now());
    postEntity.setSlug(slugify(postRequest.getTitle()));

    this.postRepo.save(postEntity);

    return true;
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
