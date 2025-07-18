package com.imeal.imeal_back.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.imeal.imeal_back.base.dto.BaseCreateRequest;
import com.imeal.imeal_back.base.entity.Base;
import com.imeal.imeal_back.base.repository.BaseRepository;
import com.imeal.imeal_back.location.dto.LocationCreateRequest;
import com.imeal.imeal_back.location.entity.Location;
import com.imeal.imeal_back.location.repository.LocationRepository;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;
import com.imeal.imeal_back.shop.dto.ShopCreateRequest;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.repository.ShopRepository;
import com.imeal.imeal_back.user.dto.UserCreateRequest;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.repository.UserRepository;

public class TestDataFactory {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private LocationRepository locationRepository;
  
  @Autowired
  private BaseRepository baseRepository;

  @Autowired
  private ShopRepository shopRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  /**
   * userエンティティを作成・テーブルに挿入
   * @param userCreateRequest postリクエスト
   * @return userエンティティ
   */
  public User createUser(UserCreateRequest userCreateRequest) {
    User user = new User();

    user.setName(userCreateRequest.getName());
    user.setEmail(userCreateRequest.getEmail());
    user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

    userRepository.insert(user);
    user.setPassword(userCreateRequest.getPassword());
    return user;
  }

  /**
   * locationエンティティを作成・テーブルに挿入
   * @param locationCreateRequest postリクエスト
   * @return locationエンティティ
   */
  public Location createLocation(LocationCreateRequest locationCreateRequest) {
    Location location = new Location();

    location.setLat(locationCreateRequest.getLat());
    location.setLon(locationCreateRequest.getLon());

    locationRepository.insert(location);
    return location;
  }

  /**
   * baseエンティティを作成・テーブルに挿入
   * @param baseCreateRequest postリクエスト
   * @param locationCreateRequest baseに紐づくlocationのpostリクエスト
   * @return baseエンティティ
   */
  public Base createBase(BaseCreateRequest baseCreateRequest, Location location) {
    Base base = new Base();

    base.setName(baseCreateRequest.getName());
    base.setLocation(location);
    
    baseRepository.insert(base);
    return base;
  }

  /**
   * shopエンティティを作成・テーブルに挿入
   * @param shopCreateRequest postリクエスト
   * @param baseCreateRequest shopに紐づくbaseのpostリクエスト
   * @param locationCreateRequest1 baseに紐づくlocationのpostリクエスト
   * @param locationCreateRequest2 shopに紐づくlocationのpostリクエスト
   * @return shopエンティティ
   */
  public Shop createShop(ShopCreateRequest shopCreateRequest, BaseCreateRequest baseCreateRequest, LocationCreateRequest locationCreateRequest1, LocationCreateRequest locationCreateRequest2) {
    Shop shop = new Shop();
    Base base = createBase(baseCreateRequest, locationCreateRequest1);
    Location location = createLocation(locationCreateRequest2);

    shop.setName(shopCreateRequest.getName());
    shop.setUrl(shopCreateRequest.getUrl());
    shop.setAddress(shopCreateRequest.getAddress());
    shop.setDistance(shopCreateRequest.getDistance());
    shop.setMinutes(shopCreateRequest.getMinutes());
    shop.setBase(base);
    shop.setLocation(location);

    shopRepository.insert(shop);
    return shop;
  }

  /**
   * reviewエンティティを作成・テーブルに挿入
   * @param reviewCreateRequest postリクエスト
   * @param userCreateRequest reviewに紐づくuserのpostリクエスト
   * @param shopCreateRequest reviewに紐づくshopのpostリクエスト
   * @param baseCreateRequest shopに紐づくbaseのpostリクエスト
   * @param locationCreateRequest1 baseに紐づくlocationのpostリクエスト
   * @param locationCreateRequest2 shopに紐づくlocationのpostリクエスト
   * @return reviewエンティティ
   */
  public Review createReview(ReviewCreateRequest reviewCreateRequest, UserCreateRequest userCreateRequest, ShopCreateRequest shopCreateRequest, BaseCreateRequest baseCreateRequest, LocationCreateRequest locationCreateRequest1, LocationCreateRequest locationCreateRequest2) {
    Review review = new Review();
    User user = createUser(userCreateRequest);
    Shop shop = createShop(shopCreateRequest, baseCreateRequest, locationCreateRequest1, locationCreateRequest2);

    review.setImgPath(reviewCreateRequest.getImgPath());
    review.setComment(reviewCreateRequest.getComment());
    review.setAmount(reviewCreateRequest.getAmount());
    review.setEvaluation(reviewCreateRequest.getEvaluation());
    review.setUser(user);
    review.setShop(shop);

    reviewRepository.insert(review);
    return review;
  }
}
