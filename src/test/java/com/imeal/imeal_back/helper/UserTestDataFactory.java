package com.imeal.imeal_back.helper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserTestDataFactory {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User createDefaultUser(UserCreateRequest request) {
    // リクエストをuserエンティティに整形
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());

    // passwordはエンコード後セット
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // userエンティティをテーブルに挿入
    userRepository.insert(user);
    return user;
  }
}
