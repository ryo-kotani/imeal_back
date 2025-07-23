package com.imeal.imeal_back.helper.faker;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewFaker {
  private final Faker faker;

  public String createImgPath() {
    return faker.lorem().characters(1, 256);
  }

  public String createComment() {
    return faker.lorem().characters(1, 256);
  }

  public Integer createAmount() {
    return faker.number().hashCode();
  }

  public Integer createEvaluation() {
    return faker.number().numberBetween(0, 5);
  }
}