package com.karka_deh.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karka_deh.models.entities.CommentEntity;
import com.karka_deh.models.mappers.CommentMapper;
import com.karka_deh.models.requests.CommentRequest;
import com.karka_deh.models.responses.CommentResponse;
import com.karka_deh.repos.CommentRepo;

@Service
public class CommentService {

  private final CommentRepo commentRepo;
  private final UserService userService;

  @Autowired
  private final CommentMapper commentMapper;

  public CommentService(CommentRepo commentRepo, UserService userService, CommentMapper commentMapper) {
    this.commentRepo = commentRepo;
    this.userService = userService;
    this.commentMapper = commentMapper;
  }

  public List<CommentResponse> getAllPostComments(String postId, String username) {
    List<CommentResponse> comments = this.commentRepo.getAllPostCommentsByUser(postId, username).stream()
        .map(comment -> this.commentMapper.toCommentResponse(comment)).toList();

    return comments;

  }

  public void createComment(CommentRequest commentRequest, String username) {
    UUID user_id = this.userService.getUserId(username).get();
    UUID id = UUID.randomUUID();

    CommentEntity commentEntity = this.commentMapper.toCommentEntity(commentRequest);

    commentEntity.setId(id.toString());
    commentEntity.setAuthorId(user_id.toString());

    this.commentRepo.save(commentEntity);
  }
}
