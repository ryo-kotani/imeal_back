package com.imeal.imeal_back.helper.testData;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.helper.faker.LocationFaker;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationTestDataFactory {

  // 基本機能
  private final LocationFaker locationFaker;
  private final LocationRepository locationRepository;

  public Location createDefaultLocation() {
    return builder().buildAndPersist();
  }

  public LocationTestDataBuilder builder() {
    return new LocationTestDataBuilder();
  }

  public class LocationTestDataBuilder {
    private BigDecimal lat = locationFaker.createLat();
    private BigDecimal lon = locationFaker.createLon();

    public LocationTestDataBuilder withLat(BigDecimal lat) {
      this.lat = lat;
      return this;
    }

    public LocationTestDataBuilder withLon(BigDecimal lon) {
      this.lon = lon;
      return this;
    }

    public Location build() {
      Location location = new Location();

      location.setLat(lat);
      location.setLon(lon);
      
      return location;
    }

    public Location buildAndPersist() {
      Location locationToPersist = build();
      locationRepository.insert(locationToPersist);
      return locationToPersist;
    }
  }
}
