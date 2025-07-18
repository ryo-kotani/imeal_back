package com.imeal.imeal_back.base.builder;

import com.imeal.imeal_back.base.entity.Base;

public class BaseBuilder {

  private String name = "テスト拠点";

  public static BaseBuilder aBase() {
    return new BaseBuilder();
  }

  public BaseBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public Base build() {
    Base base = new Base();
    base.setName(this.name);
    return base;
  }
}
