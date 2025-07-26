
package com.karka_deh.models.mappers;

import org.mapstruct.Mapper;

import com.karka_deh.models.entities.CommentEntity;
import com.karka_deh.models.requests.CommentRequest;
import com.karka_deh.models.responses.CommentResponse;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  CommentEntity toCommentEntity(CommentRequest commentRequest);

  CommentResponse toCommentResponse(CommentEntity commentRequest);
}
