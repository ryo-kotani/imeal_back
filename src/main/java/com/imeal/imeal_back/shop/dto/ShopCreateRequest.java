package com.imeal.imeal_back.shop.dto;

import lombok.Data;

@Data
public class ShopCreateRequest {
  private String url;
  private String name;
  private String address;
  private String distance;
  private Integer minutes;
  private Integer baseid;
  private Integer locationLat;
  private Integer locationLon;
}
