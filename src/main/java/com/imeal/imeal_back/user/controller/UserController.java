package com.imeal.imeal_back.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.common.validation.ValidationGroups;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.repository.UserRepository;
import com.imeal.imeal_back.user.service.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;
  
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse createUser(@RequestBody @Validated(ValidationGroups.Group.class) UserCreateRequest request) {
    return userService.createUser(request);
  }
  
  @GetMapping("/")
  public UserResponse getMethodName() {
    UserCreateRequest request = new UserCreateRequest();
    request.setName("name");
    request.setEmail("sample@test.com");
    request.setPassword("a".repeat(72));
    request.setPasswordConfirmation(request.getPassword());
    return userService.createUser(request);
  }
  
}
