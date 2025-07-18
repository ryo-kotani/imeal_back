package com.imeal.imeal_back.helper;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationTestDataFactory {
  
  private final LocationRepository locationRepository;

  public Location createDefaultLocation(LocationCreateRequest request) {
    Location location = new Location();

    location.setLat(request.getLat());
    location.setLon(request.getLon());

    locationRepository.insert(location);
    return location;
  }
}
