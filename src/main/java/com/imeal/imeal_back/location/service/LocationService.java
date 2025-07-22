package com.imeal.imeal_back.location.service;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

  private final LocationMapper locationMapper;
  private final LocationRepository locationRepository;

  public Location createLocation(LocationCreateRequest request) {
    Location location = locationMapper.toModel(request);
    locationRepository.insert(location);
    return location;
  }

  public Location updateLocation(Location location) {
    locationRepository.updateLocation(location);
    return location;
  }
}
