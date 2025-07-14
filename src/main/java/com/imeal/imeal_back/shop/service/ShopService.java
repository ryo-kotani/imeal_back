package com.imeal.imeal_back.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.shop.dto.ShopListResponse;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {
  private final ShopRepository shopRepository;

  public ShopListResponse getShops(Integer baseId) {
    List<Shop> shops = shopRepository.findByX(baseId);
    return new ShopListResponse(shops);
  }
}
