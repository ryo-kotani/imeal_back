package com.imeal.imeal_back.shop.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ShopUpdateRequest {
  private String url;
  private String name;
  private String address;
  private Integer distance;
  private Integer minutes;
  private BigDecimal locationLat;
  private BigDecimal locationLon;
}
