package com.imeal.imeal_back.review.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.shop.entity.Shop;
import com.imeal.imeal_back.user.entity.User;

@Component
public class ReviewMapper {
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

  public ReviewShopUserResponse toResponse(Review review) {
    ReviewShopUserResponse response = new ReviewShopUserResponse();
    response.setReview(review);
    return response;
  }
}
