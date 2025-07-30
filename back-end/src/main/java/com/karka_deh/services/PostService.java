package com.karka_deh.services;

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

  public Optional<PostResponse> findBySlug(String title) {
    return this.postRepo.findBySlug(title).map(entity -> {
      return this.postMapper.toPostResponse(entity);
    });
  }

  public void createPost(PostRequest postRequest, String username) {
    var uuid = UUID.randomUUID();
    var user_id = this.userService.getUserId(username).get();
    var postEntity = this.postMapper.toPostEntity(postRequest);

    postEntity.setId(uuid);
    postEntity.setAuthorId(user_id);
    postEntity.setCreatedAt(LocalDateTime.now());

    this.postRepo.save(postEntity);
  }

}
