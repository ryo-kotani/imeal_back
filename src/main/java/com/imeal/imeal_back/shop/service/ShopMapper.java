package com.imeal.imeal_back.shop.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.service.LocationService;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.entity.Shop;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShopMapper {
  private final LocationService locationService;
  private final BaseRepository baseRepository;

  public ShopResponse toResponse(Shop shop) {
    ShopResponse response = new ShopResponse();
    response.setShop(shop);
    return response;
  }

  public Shop toModel(ShopCreateRequest request) {
    Base base = baseRepository.findById(request.getBaseId());

    LocationCreateRequest locationCreateRequest = new LocationCreateRequest();
    locationCreateRequest.setLat(request.getLocationLat());
    locationCreateRequest.setLon(request.getLocationLon());
    Location persistedLocation = locationService.createLocation(locationCreateRequest);

    Shop shop = new Shop();
    shop.setName(request.getName());
    shop.setUrl(request.getUrl());
    shop.setAddress(request.getAddress());
    shop.setDistance(request.getDistance());
    shop.setMinutes(request.getMinutes());
    shop.setBase(base);
    shop.setLocation(persistedLocation);

    return shop;
  }
}
