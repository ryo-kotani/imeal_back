package com.imeal.imeal_back.helper.faker;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BaseFaker {
  private final Faker faker;

  public String createName() {
    return faker.lorem().characters(1, 256);
  }
}
