package com.imeal.imeal_back.location.dto;

import java.math.BigDecimal;

import com.imeal.imeal_back.common.validation.Latitude;
import com.imeal.imeal_back.common.validation.Longitude;
import com.imeal.imeal_back.common.validation.ValidationGroups;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationCreateRequest {

  @NotNull(message="緯度は必須です", groups = ValidationGroups.First.class)
  @Latitude(groups = ValidationGroups.Second.class)
  private BigDecimal lat;

  @NotNull(message="経度は必須です", groups = ValidationGroups.First.class)
  @Longitude(groups = ValidationGroups.Second.class)
  private BigDecimal lon;
}