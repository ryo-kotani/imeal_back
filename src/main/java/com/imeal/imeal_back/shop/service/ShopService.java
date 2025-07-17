package com.imeal.imeal_back.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.common.exception.ResourceNotFoundException;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.service.LocationService;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopListResponse;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;
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

  public void deleteShop(Integer id) {
    // deleteメソッドの戻り値（削除件数）で存在を判断する
    int affectedRows = shopRepository.delete(id);
    if (affectedRows == 0) {
      // 1件も削除されなかった場合はリソースが存在しない
      throw new ResourceNotFoundException("Shop not found with id: " + id);
    }
  }

  public ShopResponse updateShop(Integer id, ShopUpdateRequest request) {
    // 最初にShopを取得
    Shop shop = shopRepository.findById(id);

    if (shop == null) {
      throw new ResourceNotFoundException("Shop not found with id: " + id);
    }
    // Shopのlocation情報を取得して更新する
    Location existingLocation = shop.getLocation();
    if (existingLocation != null) {
      existingLocation.setLat(request.getLocationLat());
      existingLocation.setLon(request.getLocationLon());
      locationService.updateLocation(existingLocation);
    } else {
      //locationが無かったら新規作成する
      LocationCreateRequest locationCreateRequest = new LocationCreateRequest();
      locationCreateRequest.setLat(request.getLocationLat());
      locationCreateRequest.setLon(request.getLocationLon());
      existingLocation = locationService.createLocation(locationCreateRequest);
    }

    shop.setName(request.getName());
    shop.setUrl(request.getUrl());
    shop.setAddress(request.getAddress());
    shop.setDistance(request.getDistance());
    shop.setMinutes(request.getMinutes());
    shop.setLocation(existingLocation);

    shopRepository.update(shop);
    return shopMapper.toResponse(shop);
  }
}
