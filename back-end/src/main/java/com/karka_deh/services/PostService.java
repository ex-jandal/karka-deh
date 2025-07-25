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
  private final UserRepo userRepo;

  @Autowired
  private final PostMapper postMapper;

  public PostService(PostRepo postRepo, UserRepo userRepo, PostMapper postMapper) {
    this.postRepo = postRepo;
    this.userRepo = userRepo;
    this.postMapper = postMapper;
  }

  public List<PostEntity> findAll() {
    return this.postRepo.findAll();
  }

  public Optional<PostEntity> findByTitle(String title) {
    return this.postRepo.findByTitle(title);
  }

  private Optional<UUID> getUserId(String username) {
    return this.userRepo.findByUsername(username).map(user -> {
      return user.getId();
    });
  }

  public void createPost(PostRequest postRequest, String username) {
    var uuid = UUID.randomUUID();
    var user_id = this.getUserId(username).get();
    var postEntity = this.postMapper.toPostEntity(postRequest);

    postEntity.setId(uuid);
    postEntity.setAuthorId(user_id);
    postEntity.setCreatedAt(LocalDateTime.now());

    this.postRepo.save(postEntity);
  }

}
