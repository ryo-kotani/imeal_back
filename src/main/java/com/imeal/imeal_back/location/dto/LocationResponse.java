package com.imeal.imeal_back.location.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse {
  private Integer id;
  private BigDecimal locationLon;
  private BigDecimal locationLat;
}
