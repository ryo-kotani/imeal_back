package com.imeal.imeal_back.location.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;

@Component
public class LocationMapper {

  //Location作成用 requestDto→location
  public Location toModel(LocationCreateRequest request) {
    Location location = new Location();
    location.setLat(request.getLat());
    location.setLon(request.getLon());

    return location;
  }

}
