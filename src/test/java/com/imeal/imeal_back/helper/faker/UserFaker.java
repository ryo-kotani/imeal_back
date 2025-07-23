package com.imeal.imeal_back.helper.faker;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFaker {
  private final Faker faker;

  public String createName() {
    return faker.lorem().characters(1, 10);
  }

  public String createEmail() {
    return faker.internet().emailAddress();
  }

  public String createPassword() {
    return faker.internet().password(6, 72);
  }
}