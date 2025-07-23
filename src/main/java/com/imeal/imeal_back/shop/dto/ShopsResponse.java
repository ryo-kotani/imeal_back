package com.imeal.imeal_back.shop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//shop情報のリストを返すDTO
public class ShopsResponse {
  private List<ShopResponse> shops;
}
