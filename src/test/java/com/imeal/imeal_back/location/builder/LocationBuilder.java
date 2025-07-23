package com.imeal.imeal_back.location.builder;

import java.math.BigDecimal;

import com.imeal.imeal_back.location.entity.Location;

public class LocationBuilder {

  private BigDecimal lat = new BigDecimal("35.681236"); // デフォルト値: 東京駅
  private BigDecimal lon = new BigDecimal("139.767125");

  // ビルダーのインスタンスを返すstaticメソッド
  public static LocationBuilder aLocation() {
    return new LocationBuilder();
  }

  public LocationBuilder withLat(String lat) {
    this.lat = new BigDecimal(lat);
    return this;
  }

  public LocationBuilder withLon(String lon) {
    this.lon = new BigDecimal(lon);
    return this;
  }

  // 最終的にLocationオブジェクトを生成して返す
  public Location build() {
    Location location = new Location();
    location.setLat(this.lat);
    location.setLon(this.lon);
    return location;
  }
}