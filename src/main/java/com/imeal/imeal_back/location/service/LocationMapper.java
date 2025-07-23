package com.imeal.imeal_back.location.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.dto.LocationResponse;
import com.imeal.imeal_back.location.entity.Location;

@Component
public class LocationMapper {

  public Location toModel(LocationCreateRequest request) {
    Location location = new Location();
    location.setLat(request.getLat());
    location.setLon(request.getLon());

    return location;
  }

  public LocationResponse toResponse(Location location) {
    LocationResponse locationResponse = new LocationResponse();
    locationResponse.setId(location.getId());
    locationResponse.setLocationLat(location.getLat());
    locationResponse.setLocationLon(location.getLon());

    return locationResponse;
  }
}
