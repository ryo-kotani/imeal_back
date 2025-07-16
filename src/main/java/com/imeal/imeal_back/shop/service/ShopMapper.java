package com.imeal.imeal_back.shop.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.entity.Shop;

@Component
public class ShopMapper {
  public ShopResponse toResponse(Shop shop) {
    ShopResponse response = new ShopResponse();
    response.setShop(shop);
    return response;
  }
}
