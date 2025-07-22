package com.imeal.imeal_back.helper.request;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocationCreateRequestFactory {

  private final Faker faker;

  public LocationCreateRequest createValidRequest() {
    return builder().build();
  }
  
  public LocationCreateRequestBuilder builder() {
    return new LocationCreateRequestBuilder();
  }

  public class LocationCreateRequestBuilder {

    private BigDecimal lat = new BigDecimal(faker.address().latitude());
    private BigDecimal lon = new BigDecimal(faker.address().longitude());

    public LocationCreateRequestBuilder withLat(BigDecimal lat) {
      this.lat = lat;
      return this;
    }

    public LocationCreateRequestBuilder withLon(BigDecimal lon) {
      this.lon = lon;
      return this;
    }

    public LocationCreateRequest build() {
      LocationCreateRequest locationCreateRequest = new LocationCreateRequest();

      locationCreateRequest.setLat(lat);
      locationCreateRequest.setLon(lon);
      
      return locationCreateRequest;
    }
  }
}
