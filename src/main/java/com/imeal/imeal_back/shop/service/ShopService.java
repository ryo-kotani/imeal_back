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
  private final ShopMapper shopMapper;

  public ShopListResponse getShops(Integer baseId) {
    List<Shop> shops = shopRepository.findByX(baseId);
    return new ShopListResponse(shops);
  }
  
  public ShopResponse createShop(ShopCreateRequest request) {
    Base base = new Base();
    base.setId(request.getBaseId());

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
    System.out.println(base.getId());
    System.out.println(persistedLocation.getId());
    shop.setBase(base);
    shop.setLocation(persistedLocation);

    shopRepository.insert(shop);
    ShopResponse response = new ShopResponse();
    response.setShop(shop);
    return response;
  }

  public ShopResponse getShop(Integer id) {
    Shop shop = shopRepository.findById(id);
    return shopMapper.toResponse(shop);
  }
}
