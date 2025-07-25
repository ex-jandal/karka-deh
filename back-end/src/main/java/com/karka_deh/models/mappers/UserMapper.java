package com.karka_deh.models.mappers;

import org.mapstruct.Mapper;

import com.karka_deh.models.entities.UserEntity;
import com.karka_deh.models.responses.auth.SignupResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
  SignupResponse toSignupResponse(UserEntity userEntity);
}
