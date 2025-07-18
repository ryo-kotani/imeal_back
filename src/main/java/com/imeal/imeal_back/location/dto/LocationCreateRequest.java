package com.imeal.imeal_back.location.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationCreateRequest {
  private BigDecimal lat;
  private BigDecimal lon;
}