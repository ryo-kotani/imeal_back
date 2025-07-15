package com.imeal.imeal_back.user.factory;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.user.dto.UserCreateRequest;

public class UserCreateRequestFactory {
  private static final Faker FAKER = new Faker();

  // デフォルトで有効なリクエストを返すメソッドはそのまま残す
  public static UserCreateRequest createValidRequest() {
    return builder().build();
  }

  // Builderクラスへのエントリーポイント
  public static UserCreateRequestBuilder builder() {
    return new UserCreateRequestBuilder();
  }

  // 内部クラスとしてBuilderを定義
  public static class UserCreateRequestBuilder {
    private String name = FAKER.lorem().characters(1, 10);
    private String email = FAKER.internet().emailAddress();
    private String password = FAKER.internet().password(6, 72);
    private String passwordConfirmation = password; // デフォルトでは一致させておく

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