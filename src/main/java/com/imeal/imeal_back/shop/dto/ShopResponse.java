package com.imeal.imeal_back.shop.dto;

import com.imeal.imeal_back.base.dto.BaseResponse;
import com.imeal.imeal_back.location.dto.LocationResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//shop情報(単体)とそれに紐づくbaseとlocationを返すDTO
public class ShopResponse {
  Integer id;
  String url;
  String name;
  String address;
  Integer distance;
  Integer minutes;
  BaseResponse base;
  LocationResponse location;
}
