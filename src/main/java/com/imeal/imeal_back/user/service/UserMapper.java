package com.imeal.imeal_back.user.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.entity.User;

@Component
public class UserMapper {
  
  public User toModelFromCreate(UserCreateRequest request) {
    User user = new User();

    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());

    return user;
  }

  public UserResponse toResponse(User user) {
    UserResponse response = new UserResponse();

    response.setId(user.getId());
    response.setName(user.getName());

    return response;
  }
}
