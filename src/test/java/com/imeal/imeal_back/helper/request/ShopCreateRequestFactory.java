package com.imeal.imeal_back.helper.request;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.helper.faker.LocationFaker;
import com.imeal.imeal_back.helper.faker.ShopFaker;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShopCreateRequestFactory {
  private final ShopFaker shopFaker;
  private final LocationFaker locationFaker;

  // デフォルトのリクエストオブジェクトを生成
  public ShopCreateRequest createValidRequest() {
    return builder().build();
  }

  // ビルダーのエントリーポイント
  public ShopCreateRequestBuilder builder() {
    return new ShopCreateRequestBuilder();
  }

  // ランダムなリクエストオブジェクトを生成
  public class ShopCreateRequestBuilder {
    private String name = shopFaker.createName();
    private String url = shopFaker.createUrl();
    private String address = shopFaker.createAddress();
    private Integer distance = shopFaker.createDistance();
    private Integer minutes = shopFaker.createMinutes();
    private Integer baseId = 1;
    private BigDecimal locationLat = locationFaker.createLat();
    private BigDecimal locationLon = locationFaker.createLon();

    public ShopCreateRequestBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ShopCreateRequestBuilder withUrl(String url) {
      this.url = url;
      return this;
    }

    public ShopCreateRequestBuilder withAddress(String address) {
      this.address = address;
      return this;
    }

    public ShopCreateRequestBuilder withDistance(Integer distance) {
      this.distance = distance;
      return this;
    }

    public ShopCreateRequestBuilder withMinutes(Integer minutes) {
      this.minutes = minutes;
      return this;
    }

    public ShopCreateRequestBuilder withShopId(Integer baseId) {
      this.baseId = baseId;
      return this;
    }

    public ShopCreateRequestBuilder withLocationLat(BigDecimal locationLat) {
      this.locationLat = locationLat;
      return this;
    }

    public ShopCreateRequestBuilder withLocationLon(BigDecimal locationLon) {
      this.locationLon = locationLon;
      return this;
    }

    public ShopCreateRequest build() {
      ShopCreateRequest request = new ShopCreateRequest();
      
      request.setName(name);
      request.setUrl(url);
      request.setAddress(address);
      request.setDistance(distance);
      request.setMinutes(minutes);
      request.setBaseId(baseId);
      request.setLocationLat(locationLat);
      request.setLocationLon(locationLon);

      return request;
    }
  }
}
