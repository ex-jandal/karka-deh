package com.karka_deh.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Comment;

import com.karka_deh.errors.SlugNotFoundException;
import com.karka_deh.errors.UserNotFoundException;
import com.karka_deh.models.entities.CommentEntity;
import com.karka_deh.models.mappers.CommentMapper;
import com.karka_deh.models.requests.CommentRequest;
import com.karka_deh.models.responses.CommentResponse;
import com.karka_deh.repos.CommentRepo;
import com.karka_deh.repos.PostRepo;

@Service
public class CommentService {

  private final CommentRepo commentRepo;
  private final PostRepo postRepo;
  private final UserService userService;

  @Autowired
  private final CommentMapper commentMapper;

  public CommentService(CommentRepo commentRepo, PostRepo postRepo, UserService userService,
      CommentMapper commentMapper) {
    this.commentRepo = commentRepo;
    this.userService = userService;
    this.commentMapper = commentMapper;
    this.postRepo = postRepo;
  }

  public Page<CommentResponse> getUserPostComments(String slug, String username, Pageable pageable) {
    return this.commentRepo.getUserPostComments(slug, username, pageable).toPage(this.commentMapper::toCommentResponse);
  }

  public Page<CommentResponse> getAllPostComments(String slug, Pageable pageable) {
    return this.commentRepo.getAllPostComments(slug, pageable).toPage(this.commentMapper::toCommentResponse);
  }

  public void createComment(CommentRequest commentRequest, String username) {
    UUID userId = this.userService.getUserId(username).orElseThrow(() -> new UserNotFoundException(username));
    UUID id = UUID.randomUUID();
    var postSlug = commentRequest.getPostSlug();

    UUID postId = this.postRepo.findPostIdBySlug(postSlug)
        .orElseThrow(() -> new SlugNotFoundException(postSlug));

    CommentEntity commentEntity = this.commentMapper.toCommentEntity(commentRequest);

    commentEntity.setId(id);
    commentEntity.setPostId(postId);
    commentEntity.setAuthorId(userId);

    this.commentRepo.save(commentEntity);
  }
}
