package com.imeal.imeal_back.helper.request;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.dto.BaseCreateRequest;
import com.imeal.imeal_back.helper.faker.BaseFaker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BaseCreateRequestFactory {
  private final BaseFaker baseFaker;

  public BaseCreateRequest createValidRequest() {
    return builder().build();
  }

  public BaseCreateRequestBuilder builder() {
    return new BaseCreateRequestBuilder();
  }

  public class BaseCreateRequestBuilder {

    private String name = baseFaker.createName();
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
