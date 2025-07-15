package com.imeal.imeal_back.shop.dto;

import java.util.List;

import com.imeal.imeal_back.shop.entity.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopListResponse {
  private List<Shop> shops;
}
