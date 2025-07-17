package com.imeal.imeal_back.review.service;

import org.springframework.stereotype.Component;

import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.shop.entity.Shop;

@Component
public class ReviewMapper {
  public Review toModel(ReviewCreateRequest request) {
    Review review = new Review();
    review.setImgPath(request.getImgPath());
    review.setComment(request.getComment());
    review.setAmount(request.getAmount());
    review.setEvaluation(request.getEvaluation());
    Shop shop = new Shop();
    shop.setId(request.getShopId());
    review.setShop(shop);
    return review;
  }

  public ReviewShopUserResponse toResponse(Review review) {
    ReviewShopUserResponse response = new ReviewShopUserResponse();
    response.setReview(review);
    return response;
  }
}
