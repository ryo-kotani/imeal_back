package com.imeal.imeal_back.helper.faker;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShopFaker {
  
  private final Faker faker;

  public String createUrl() {
    return faker.lorem().characters(1, 256);
  }

  public String createName() {
    return faker.lorem().characters(1, 256);
  }

  public String createAddress() {
    return faker.lorem().characters(1, 256);
  }

  public Integer createDistance() {
    return faker.number().hashCode();
  }

  public Integer createMinutes() {
    return faker.number().hashCode();
  }
}
