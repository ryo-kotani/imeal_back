package com.imeal.imeal_back.shop.dto;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.shop.entity.Shop;

import lombok.Data;

@Data
public class ShopResponse {
  private Integer id;
  private String url;
  private String name;
  private String address;
  private String distance;
  private Integer minutes;
  private Base base;
  private Location location;

  public ShopResponse(Shop shop) {
    this.id = shop.getId();
    this.url = shop.getUrl();
    this.name = shop.getName();
    this.address = shop.getAddress();
    this.distance = shop.getDistance();
    this.minutes = shop.getMinutes();
    this.base = shop.getBase();
    this.location = shop.getLocation();
  }
}
