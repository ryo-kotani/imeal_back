package com.imeal.imeal_back.shop.dto;

import java.util.List;

import com.imeal.imeal_back.base.dto.BaseResponse;
import com.imeal.imeal_back.location.dto.LocationResponse;
import com.imeal.imeal_back.review.dto.ReviewResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//shop情報とそれに紐づいたreviewのリストを返すDTO
public class ShopReviewsResponse {
  Integer id;
  String url;
  String name;
  String address;
  Integer distance;
  Integer minutes;
  BaseResponse base;
  LocationResponse location;
  List<ReviewResponse> reviews;
}
