package com.imeal.imeal_back.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.base.dto.BaseResponse;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.base.service.BaseMapper;
import com.imeal.imeal_back.location.dto.LocationResponse;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.service.LocationMapper;
import com.imeal.imeal_back.location.service.LocationService;
import com.imeal.imeal_back.review.dto.ReviewResponse;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.service.ReviewMapper;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.dto.ShopReviewsResponse;
import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;
import com.imeal.imeal_back.shop.dto.ShopsResponse;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShopMapper {
  private final LocationService locationService;
  private final LocationMapper locationMapper;
  private final BaseRepository baseRepository;
  private final BaseMapper baseMapper;
  private final ShopRepository shopRepository;
  private final ReviewMapper reviewMapper;

  //Shop情報（単体）とそれに紐づいたbaseとlocation情報を返すマッパー
  public ShopResponse toShopResponse(Shop shop) {
    ShopResponse response = new ShopResponse();
    BaseResponse base = baseMapper.toBaseResponse(shop.getBase()); //BaseMapperを呼び出してレスポンスに変換する
    LocationResponse location = locationMapper.toResponse(shop.getLocation()); //LocationMapperを呼び出してレスポンスに変換する

    response.setId(shop.getId());
    response.setUrl(shop.getUrl());
    response.setName(shop.getName());
    response.setAddress(shop.getAddress());
    response.setDistance(shop.getDistance());
    response.setMinutes(shop.getMinutes());
    response.setBase(base);
    response.setLocation(location);
    return response;
  }
  //Shop情報をリストにして返す
  public ShopsResponse toShopsResponse(List<Shop> shops) {
    List<ShopResponse> shopResponseList = new ArrayList<>();

    for(Shop shop: shops){
      shopResponseList.add(toShopResponse(shop));
    }
    ShopsResponse response = new ShopsResponse(shopResponseList);
    return response;
  }
  //Shopとそれに紐づくレビュー情報のリストを返す
  public ShopReviewsResponse toShopReviewsResponse(Shop shop) {
    ShopReviewsResponse response = new ShopReviewsResponse();
    BaseResponse base = baseMapper.toBaseResponse(shop.getBase()); //BaseMapperを呼び出してレスポンスに変換する
    LocationResponse location = locationMapper.toResponse(shop.getLocation()); //LocationMapperを呼び出してレスポンスに変換する

    response.setId(shop.getId());
    response.setUrl(shop.getUrl());
    response.setName(shop.getName());
    response.setAddress(shop.getAddress());
    response.setDistance(shop.getDistance());
    response.setMinutes(shop.getMinutes());
    response.setBase(base);
    response.setLocation(location);

    List<ReviewResponse> reviews = new ArrayList<>();
    for(Review review : shop.getReviews()) {
      reviews.add(reviewMapper.toReviewResponse(review));
    }
    response.setReviews(reviews);

    return response;
  }

  

  public Shop toModel(ShopCreateRequest request, Location location) {
    Base base = baseRepository.findById(request.getBaseId());

    Shop shop = new Shop();
    shop.setName(request.getName());
    shop.setUrl(request.getUrl());
    shop.setAddress(request.getAddress());
    shop.setDistance(request.getDistance());
    shop.setMinutes(request.getMinutes());
    shop.setBase(base);
    shop.setLocation(location);

    return shop;
  }
  public Shop toUpdateModel(Shop shop, ShopUpdateRequest request) {

    shop.setName(request.getName());
    shop.setUrl(request.getUrl());
    shop.setAddress(request.getAddress());
    shop.setDistance(request.getDistance());
    shop.setMinutes(request.getMinutes());

    return shop;
  }
}
