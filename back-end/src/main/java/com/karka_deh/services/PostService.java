package com.karka_deh.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.mappers.PostMapper;
import com.karka_deh.models.requests.PostRequest;
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

  public List<PostEntity> getAllUserPosts(String username) {
    UUID user_id = this.userService.getUserId(username).get();

    return this.postRepo.findAllPostsByUserId(user_id);
  }

  public Optional<PostEntity> findByTitle(String title) {
    return this.postRepo.findByTitle(title);
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
