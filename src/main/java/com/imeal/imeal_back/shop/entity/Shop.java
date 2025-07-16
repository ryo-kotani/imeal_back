package com.imeal.imeal_back.shop.entity;

import java.util.List;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.review.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shop {
  Integer id;
  String url;
  String name;
  String address;
  Integer distance;
  Integer minutes;
  Base base;
  Location location;
  List<Review> reviews;
}
