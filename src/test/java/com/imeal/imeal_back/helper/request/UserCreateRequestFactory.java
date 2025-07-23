package com.imeal.imeal_back.helper.request;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.helper.faker.UserFaker;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCreateRequestFactory {
  private final UserFaker userFaker;

  public UserCreateRequest createValidRequest() {
    return builder().build();
  }

  // Builderクラスへのエントリーポイント
  public UserCreateRequestBuilder builder() {
    return new UserCreateRequestBuilder();
  }

  // 内部クラスとしてBuilderを定義
  public class UserCreateRequestBuilder {
    private String name = userFaker.createName();
    private String email = userFaker.createEmail();
    private String password = userFaker.createPassword();
    private String passwordConfirmation = password;

    public UserCreateRequestBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public UserCreateRequestBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserCreateRequestBuilder withPassword(String password) {
      this.password = password;
      return this;
    }
    
    public UserCreateRequestBuilder withPasswordConfirmation(String passwordConfirmation) {
      this.passwordConfirmation = passwordConfirmation;
      return this;
    }

    public UserCreateRequest build() {
      UserCreateRequest request = new UserCreateRequest();
      request.setName(this.name);
      request.setEmail(this.email);
      request.setPassword(this.password);
      request.setPasswordConfirmation(this.passwordConfirmation);
      return request;
    }
  }
}