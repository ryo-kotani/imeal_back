package com.imeal.imeal_back.helper.faker;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationFaker {

  private final Faker faker;

  public BigDecimal createLat() {
    return new BigDecimal(faker.address().latitude());
  }

  public BigDecimal createLon() {
    return new BigDecimal(faker.address().longitude());
  }
}
