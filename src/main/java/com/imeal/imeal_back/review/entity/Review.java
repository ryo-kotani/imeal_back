package com.imeal.imeal_back.review.entity;

import java.sql.Timestamp;

import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
  private Integer id;
  private String imgPath;
  private String comment;
  private Integer amount;
  private Integer evaluation;
  private Timestamp createdAt;
  private Shop shop;
  private User user;
}
