package com.imeal.imeal_back.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final PasswordEncoder passwordEncoder;

  public UserResponse createUser(UserCreateRequest request) {
    User user = userMapper.toModelFromCreate(request);

    System.out.println(user.getEmail());

    String encodedPassword = passwordEncoder.encode(user.getPassword());

    System.out.println(user.getEmail());

    user.setPassword(encodedPassword);
    userRepository.insert(user);
    return userMapper.toResponse(user);
  }
}
