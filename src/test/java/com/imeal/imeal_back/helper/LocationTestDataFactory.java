package com.imeal.imeal_back.helper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationTestDataFactory {

  // 必須項目
  private final Faker faker;
  private final LocationRepository locationRepository;

  public Location createDefaultLocation() {
    return builder().buildAndPersist();
  }

  public LocationTestDataBuilder builder() {
    return new LocationTestDataBuilder();
  }

  public class LocationTestDataBuilder {
    private BigDecimal lat = new BigDecimal(faker.address().latitude());
    private BigDecimal lon = new BigDecimal(faker.address().longitude());

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
