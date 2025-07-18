package com.imeal.imeal_back.helper;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.dto.BaseCreateRequest;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.helper.request.BaseCreateRequestFactory;
import com.imeal.imeal_back.helper.request.LocationCreateRequestFactory;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BaseTestDataFactory {
  
  private final BaseRepository baseRepository;
  private final LocationTestDataFactory locationTestDataFactory;

  public Base createDefaultBase() {
    return builder().buildAndPersist();
  }

  public BaseTestDataBuilder builder() {
    return new BaseTestDataBuilder();
  }

  public class BaseTestDataBuilder {

    private String name;
    private Location location;

    public BaseTestDataBuilder withBase(BaseCreateRequest baseCreateRequest) {
      this.name = baseCreateRequest.getName();
      return this;
    }

    public BaseTestDataBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public BaseTestDataBuilder withLocation(Location location) {
      this.location = location;
      return this;
    }

    public Base build() {
      if (this.name == null) {
        BaseCreateRequest baseCreateRequest = BaseCreateRequestFactory.createValidRequest();
        this.name = baseCreateRequest.getName();
      }

      if (this.location == null) {
        LocationCreateRequest locationCreateRequest = LocationCreateRequestFactory.createValidRequest();
        this.location = locationTestDataFactory.createDefaultLocation(locationCreateRequest);
      }

      Base base = new Base();
      base.setName(name);
      base.setLocation(location);

      return base;
    }

    public Base buildAndPersist() {
      Base baseToPersist = build();
      baseRepository.insert(baseToPersist);
      return baseToPersist;
    }
  }
}
