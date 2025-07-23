package com.imeal.imeal_back.review.dto;

import java.sql.Timestamp;

import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.user.dto.UserResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewShopUserResponse {
  private Integer id;
  private String imgPath;
  private String comment;
  private Integer amount;
  private Integer evaluation;
  private Timestamp createdAt;
  private ShopResponse shop;
  private UserResponse user;
}
