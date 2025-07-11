package com.imeal.imeal_back.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  
  @PostMapping("/")
  public UserResponse createUser(UserCreateRequest request) {
    return userService.createUser(request);
  }
  
}
