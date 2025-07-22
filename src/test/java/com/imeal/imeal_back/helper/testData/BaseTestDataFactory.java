package com.imeal.imeal_back.helper.testData;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.helper.faker.BaseFaker;
import com.imeal.imeal_back.location.entity.Location;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BaseTestDataFactory {

  // 基本機能
  private final BaseFaker baseFaker;
  private final BaseRepository baseRepository;

  // 外部キー制約のあるデータの生成
  private final LocationTestDataFactory locationTestDataFactory;

  public Base createDefaultBase() {
    return builder().buildAndPersist();
  }

  public BaseTestDataBuilder builder() {
    return new BaseTestDataBuilder();
  }

  public class BaseTestDataBuilder {

    private String name = baseFaker.createName();
    private Location location;

    public BaseTestDataBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public BaseTestDataBuilder withLocation(Location location) {
      this.location = location;
      return this;
    }

    public Base build() {
      if (this.location == null) {
        this.location = locationTestDataFactory.createDefaultLocation();
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
