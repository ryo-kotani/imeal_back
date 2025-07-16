package com.imeal.imeal_back.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.shop.dto.ShopListResponse;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {
  private final ShopRepository shopRepository;
  private final ShopMapper shopMapper;

  public ShopListResponse getShops(Integer baseId) {
    List<Shop> shops = shopRepository.findByX(baseId);
    return new ShopListResponse(shops);
  }

  public ShopResponse getShop(Integer id) {
    Shop shop = shopRepository.findById(id);
    return shopMapper.toResponse(shop);
  }
}
