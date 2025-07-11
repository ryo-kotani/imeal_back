package com.imeal.imeal_back.user.service;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;

  public UserResponse createUser(UserCreateRequest request) {
    User user = userMapper.toModelFromCreate(request);
    userRepository.insert(user);
    return userMapper.toResponse(user);
  }
}
