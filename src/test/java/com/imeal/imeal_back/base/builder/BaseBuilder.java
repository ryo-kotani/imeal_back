package com.imeal.imeal_back.base.builder;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.location.entity.Location;

public class BaseBuilder {

  private String name = "テスト拠点";
  private Location location;

  public static BaseBuilder aBase() {
    return new BaseBuilder();
  }

  public BaseBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public BaseBuilder withLocation(Location location) {
    this.location = location;
    return this;
  }

  public Base build() {
    Base base = new Base();
    base.setName(this.name);
    base.setLocation(location);
    return base;
  }
}
