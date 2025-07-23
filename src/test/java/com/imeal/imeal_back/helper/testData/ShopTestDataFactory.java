package com.imeal.imeal_back.helper.testData;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.helper.faker.ShopFaker;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShopTestDataFactory {
  
  // 基本機能
  private final ShopFaker shopFaker;
  private final ShopRepository shopRepository;

  // 外部キー制約のあるテストデータを生成
  private final LocationTestDataFactory locationTestDataFactory;
  private final BaseTestDataFactory baseTestDataFactory;

  public Shop createDefaultShop() {
    return builder().buildAndPersist();
  }
  public ShopTestDataBuilder builder() {
    return new ShopTestDataBuilder();
  }

  public class ShopTestDataBuilder {
    private String url = shopFaker.createUrl();
    private String name = shopFaker.createName();
    private String address = shopFaker.createAddress();
    private Integer distance = shopFaker.createDistance();
    private Integer minutes = shopFaker.createMinutes();
    private Location location;
    private Base base;

    public ShopTestDataBuilder withUrl(String url) {
      this.url = url;
      return this;
    }

    public ShopTestDataBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ShopTestDataBuilder withAddress(String address) {
      this.address = address;
      return this;
    }

    public ShopTestDataBuilder withDistance(Integer distance) {
      this.distance = distance;
      return this;
    }

    public ShopTestDataBuilder withMinutes(Integer minutes) {
      this.minutes = minutes;
      return this;
    }

    public ShopTestDataBuilder withLocation(Location location) {
      this.location = location;
      return this;
    }

    public ShopTestDataBuilder withBase(Base base) {
      this.base = base;
      return this;
    }

    public Shop build() {
      if (location == null) {
        location = locationTestDataFactory.createDefaultLocation();
      }

      if (base == null) {
        base = baseTestDataFactory.createDefaultBase();
      }

      Shop shop = new Shop();

      shop.setUrl(url);
      shop.setName(name);
      shop.setAddress(address);
      shop.setDistance(distance);
      shop.setMinutes(minutes);
      shop.setLocation(location);
      shop.setBase(base);

      return shop;
    }

    public Shop buildAndPersist() {
      Shop shopToPersist = build();
      shopRepository.insert(shopToPersist);
      return shopToPersist;
    }
  }
}
