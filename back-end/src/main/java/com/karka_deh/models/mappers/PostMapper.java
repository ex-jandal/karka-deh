package com.karka_deh.models.mappers;

import org.mapstruct.Mapper;

import com.karka_deh.models.entities.PostEntity;
import com.karka_deh.models.entities.UserEntity;
import com.karka_deh.models.requests.PostRequest;
import com.karka_deh.models.responses.auth.SignupResponse;

@Mapper(componentModel = "spring")
public interface PostMapper {
  PostEntity toPostEntity(PostRequest postRequest);
}
