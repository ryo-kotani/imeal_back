package com.imeal.imeal_back.review.entity;

import java.sql.Timestamp;

import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
  Integer id;
  String imgPath;
  String comment;
  Integer amount;
  Integer evaluation;
  Timestamp createdAt;
  Shop shop;
  User user;
}
