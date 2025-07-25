package com.imeal.imeal_back.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.common.exception.ResourceNotFoundException;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.service.LocationService;
import com.imeal.imeal_back.review.dto.ReviewsResponse;
import com.imeal.imeal_back.review.service.ReviewMapper;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.dto.ShopReviewsResponse;
import com.imeal.imeal_back.shop.dto.ShopUpdateRequest;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository shopRepository;
  private final BaseRepository baseRepository;
  private final LocationService locationService;
  private final ShopMapper shopMapper;
  private final ReviewMapper reviewMapper;

  
  /**
   * パラメーターから拠点情報を受け取って拠点にある店舗の一覧を返す
   * @param baseId 拠点のID
   * @return ShopsResponse
   */
  public List<ShopResponse> getShops(Integer baseId) {
    // 最初にbaseを取得
    Base base = baseRepository.findById(baseId);
    //baseが無かったらエラーを返す
    if (base == null) {
      throw new ResourceNotFoundException("Shop not found with id: " + baseId);
    }

    List<Shop> shops = shopRepository.findByX(baseId); //店舗一覧を取得
    List<ShopResponse> response = shopMapper.toShopsResponse(shops); //エンティティをレスポンスDTOに変換
    return response;
  }

  public ShopResponse createShop(ShopCreateRequest request) {
    LocationCreateRequest locationCreateRequest = request.getLocation();
    Location persistedLocation = locationService.createLocation(locationCreateRequest); //ロケーションサービスを呼び出してロケーションを作る

    Base foundBase = baseRepository.findById(request.getBaseId());

    Shop shop = shopMapper.toModel(request, persistedLocation, foundBase); //作ったロケーションとリクエストをセットしエンティティに変換する
    shopRepository.insert(shop);
    return shopMapper.toShopResponse(shop);
  }

  public ShopReviewsResponse getShopWithReviews(Integer id) {
    Shop shop = shopRepository.findByIdWithReviews(id);
    ShopReviewsResponse shopReviewsResponse = shopMapper.toShopReviewsResponse(shop); //shop情報だけ格納される(レビュー情報は空)

    ReviewsResponse reviews = reviewMapper.toReviewsResponse(shop.getReviews());
    shopReviewsResponse.setReviews(reviews);

    return shopReviewsResponse;
    //shop情報を格納→reviewsResponseを作る→合体する

  }

  public ShopResponse getShop(Integer id) {
    Shop shop = shopRepository.findById(id);
    return shopMapper.toShopResponse(shop);
  }

  public void deleteShop(Integer id) {
    // deleteメソッドの戻り値（削除件数）で存在を判断する
    int affectedRows = shopRepository.delete(id);
    if (affectedRows == 0) {
      // 1件も削除されなかった場合はリソースが存在しない
      throw new ResourceNotFoundException("Shop not found with id: " + id);
    }
  }

  public ShopResponse updateShop(Integer id, ShopUpdateRequest request) {
    // 最初にShopを取得
    Shop shop = shopRepository.findById(id);
    //Shopが無かったらエラーを返す
    if (shop == null) {
      throw new ResourceNotFoundException("Shop not found with id: " + id);
    }

    // Shopのlocation情報を取得して更新する
    Location existingLocation = shop.getLocation();
    if (existingLocation != null) {
      existingLocation.setLat(request.getLocation().getLat());
      existingLocation.setLon(request.getLocation().getLon());
      locationService.updateLocation(existingLocation);
    } else {
      //locationが無かったら新規作成する
      LocationCreateRequest locationCreateRequest = request.getLocation();
      existingLocation = locationService.createLocation(locationCreateRequest);
    }
    shop.setLocation(existingLocation);

    Shop shopUpdate = shopMapper.toUpdateModel(shop, request);
    shopRepository.update(shopUpdate);

    return shopMapper.toShopResponse(shopUpdate);
  }
}