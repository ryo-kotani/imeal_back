package com.imeal.imeal_back.location.dto;

import lombok.Data;

@Data
public class LocationCreateRequest {
  private Integer lat;
  private Integer lon;

  public LocationCreateRequest(Integer lat, Integer lon) {
    this.lat = lat;
    this.lon = lon;
  }
}