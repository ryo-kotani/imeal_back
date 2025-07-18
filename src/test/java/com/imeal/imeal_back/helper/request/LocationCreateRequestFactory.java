package com.imeal.imeal_back.helper.request;

import java.math.BigDecimal;

import com.github.javafaker.Faker;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;

public class LocationCreateRequestFactory {

  private static final Faker faker = new Faker();

  public static LocationCreateRequest createValidRequest() {
    return builder().build();
  }
  
  public static LocationCreateRequestBuilder builder() {
    return new LocationCreateRequestBuilder();
  }

  public static class LocationCreateRequestBuilder {

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
