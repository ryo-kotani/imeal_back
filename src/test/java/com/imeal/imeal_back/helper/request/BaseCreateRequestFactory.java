package com.imeal.imeal_back.helper.request;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.base.dto.BaseCreateRequest;

public class BaseCreateRequestFactory {
  private static final Faker faker = new Faker();

  public static BaseCreateRequest createValidRequest() {
    return builder().build();
  }

  public static BaseCreateRequestBuilder builder() {
    return new BaseCreateRequestBuilder();
  }

  public static class BaseCreateRequestBuilder {

    private String name = faker.lorem().characters(1, 256);
    private Integer locationId = 1;

    public BaseCreateRequestBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public BaseCreateRequestBuilder withLocationId(Integer locationId) {
      this.locationId = locationId;
      return this;
    }

    public BaseCreateRequest build() {
      BaseCreateRequest baseCreateRequest = new BaseCreateRequest();
      baseCreateRequest.setName(name);
      baseCreateRequest.setLocationId(locationId);
      return baseCreateRequest;
    }
  }
}
