package com.imeal.imeal_back.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.service.LocationService;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopListResponse;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {
  private final ShopRepository shopRepository;
  private final LocationService locationService;

  public ShopListResponse getShops(Integer baseId) {
    List<Shop> shops = shopRepository.findByX(baseId);
    return new ShopListResponse(shops);
  }
  
  public ShopResponse createShop(ShopCreateRequest request) {
    Base base = new Base();
    base.setId(request.getBaseid());

    Location persistedLocation = locationService.createLocation(new LocationCreateRequest(request.getLocationLat(), request.getLocationLon()));
    
    Shop shop = new Shop();
    shop.setName(request.getName());
    shop.setUrl(request.getUrl());
    shop.setAddress(request.getAddress());
    shop.setDistance(request.getDistance());
    shop.setMinutes(request.getMinutes());
    shop.setBase(base);
    shop.setLocation(persistedLocation);

    shopRepository.insert(shop);
    ShopResponse response = new ShopResponse(shop);
    return response;
  }
}
