package com.imeal.imeal_back.shop.dto;

import java.util.List;

import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.shop.entity.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopResponse {
  Shop shop;
  List<Review> reviews;
}
