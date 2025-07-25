package com.imeal.imeal_back.review.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewResponse;
import com.imeal.imeal_back.review.dto.ReviewShopResponse;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;
import com.imeal.imeal_back.review.dto.ReviewsResponse;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.shop.dto.ShopResponse;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.shop.service.ShopMapper;
import com.imeal.imeal_back.user.dto.UserResponse;
import com.imeal.imeal_back.user.entity.User;
import com.imeal.imeal_back.user.service.UserMapper;

@Component
public class ReviewMapper {
  private ShopMapper shopMapper;
  private final UserMapper userMapper;

  @Autowired
  public void setShopMapper(ShopMapper shopMapper) {
      this.shopMapper = shopMapper;
  }

  public ReviewMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }
  /**
   * Create処理専用
   * @param request
   * @param userId
   * @return Reviewエンティティ
   */
  public Review toModel(ReviewCreateRequest request, Integer userId) {
    Review review = new Review();
    review.setImgPath(request.getImgPath());
    review.setComment(request.getComment());
    review.setAmount(request.getAmount());
    review.setEvaluation(request.getEvaluation());
    Shop shop = new Shop();
    shop.setId(request.getShopId());
    review.setShop(shop);
    User user = new User();
    user.setId(userId);
    review.setUser(user);
    return review;
  }

  /**
   * Update処理専用
   * @param request
   * @param reviewId
   * @return Reviewエンティティ
   */
  public Review toModelFromUpdate(ReviewUpdateRequest request, Integer reviewId) {
    Review review = new Review();
    review.setImgPath(request.getImgPath());
    review.setComment(request.getComment());
    review.setAmount(request.getAmount());
    review.setEvaluation(request.getEvaluation());
    review.setId(reviewId);
    return review;
  }
  //レビュー + ショップ情報 + ユーザー情報
  public ReviewShopUserResponse toReviewShopUserResponse(Review review) {
    ReviewShopUserResponse response = new ReviewShopUserResponse();//レビュー情報のセット
    response.setImgPath(review.getImgPath());
    response.setComment(review.getComment());
    response.setAmount(review.getAmount());
    response.setEvaluation(review.getEvaluation());
    response.setId(review.getId());
    
    ShopResponse shopResponse = shopMapper.toShopResponse(review.getShop());//ショップ情報のセット
    response.setShop(shopResponse);

    UserResponse userResponse = userMapper.toResponse(review.getUser());//ユーザー情報のセット
    response.setUser(userResponse);

    return response;
  }

  public List<ReviewShopUserResponse> toReviewsShopUserResponse(List<Review> reviews) {
    List<ReviewShopUserResponse> responses = new ArrayList<>();
    for (Review review : reviews) {
      ReviewShopUserResponse response = new ReviewShopUserResponse();
      response.setImgPath(review.getImgPath());
      response.setComment(review.getComment());
      response.setAmount(review.getAmount());
      response.setEvaluation(review.getEvaluation());
      response.setId(review.getId());

      ShopResponse shopResponse = shopMapper.toShopResponse(review.getShop());
      response.setShop(shopResponse);

      UserResponse userResponse = userMapper.toResponse(review.getUser());
      response.setUser(userResponse);

      responses.add(response);
    }

    return responses;
  }
  //レビュー + ショップ情報
  public ReviewShopResponse toReviewShopResponse(Review review) {
    ReviewShopResponse response = new ReviewShopResponse();//レビュー情報のセット
    response.setImgPath(review.getImgPath());
    response.setComment(review.getComment());
    response.setAmount(review.getAmount());
    response.setEvaluation(review.getEvaluation());
    response.setId(review.getId());
    
    ShopResponse shopResponse = shopMapper.toShopResponse(review.getShop());//ショップ情報のセット
    response.setShop(shopResponse);

    return response;
  }
  //shopに紐づくレビュー情報のセット用
  public ReviewResponse toReviewResponse(Review review) {
    ReviewResponse response = new ReviewResponse();
    response.setImgPath(review.getImgPath());
    response.setComment(review.getComment());
    response.setAmount(review.getAmount());
    response.setEvaluation(review.getEvaluation());
    response.setCreatedAt(review.getCreatedAt());
    response.setId(review.getId());

    return response;
  }

  public ReviewsResponse toReviewsResponse(List<Review> reviews) {
    List<ReviewResponse> reviewResponses = new ArrayList<>();
    for(Review review : reviews) {
      reviewResponses.add(toReviewResponse(review));
    };
    ReviewsResponse response = new ReviewsResponse();
    response.setReviews(reviewResponses);
    return response;
  }
}
