package com.karka_deh.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public List<CommentResponse> getAllPostComments(String slug, String username) {
    List<CommentResponse> comments = this.commentRepo.getAllPostCommentsByUser(slug, username).stream()
        .map(comment -> this.commentMapper.toCommentResponse(comment)).toList();

    return comments;

  }

  public void createComment(CommentRequest commentRequest, String username) {
    UUID user_id = this.userService.getUserId(username).orElseThrow(() -> new UserNotFoundException(username));
    UUID id = UUID.randomUUID();
    var postSlug = commentRequest.getPostSlug();

    UUID postId = this.postRepo.findPostIdBySlug(postSlug)
        .orElseThrow(() -> new SlugNotFoundException(postSlug));

    CommentEntity commentEntity = this.commentMapper.toCommentEntity(commentRequest);

    commentEntity.setId(id);
    commentEntity.setPostId(postId);
    commentEntity.setAuthorId(user_id);

    this.commentRepo.save(commentEntity);
  }
}
