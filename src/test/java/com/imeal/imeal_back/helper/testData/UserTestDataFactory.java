package com.imeal.imeal_back.helper.testData;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.imeal.imeal_back.helper.faker.UserFaker;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserTestDataFactory {
  
  // 基本機能
  private final UserFaker userFaker;
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public User createDefaultUser() {
    return builder().buildAndPersist();
  }
  public UserTestDataBuilder builder() {
    return new UserTestDataBuilder();
  }

  public class UserTestDataBuilder {
    private String name = userFaker.createName();
    private String email = userFaker.createEmail();
    private String password = userFaker.createPassword();

    public UserTestDataBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public UserTestDataBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserTestDataBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public User build() {
      User user = new User();

      user.setName(name);
      user.setEmail(email);
      user.setPassword(passwordEncoder.encode(password));

      return user;
    }

    public User buildAndPersist() {
      User userToPersist = build();
      userRepository.insert(userToPersist);
      return userToPersist;
    }
  }
}
