package com.imeal.imeal_back.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.common.exception.ResourceNotFoundException;
import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopResponse;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.dto.ReviewUpdateRequest;
import com.imeal.imeal_back.review.dto.ReviewsShopUserResponse;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  public List<ReviewShopUserResponse> getReviews(Integer baseId, String sort, Integer limit) {
    List<Review> reviews = reviewRepository.findByX(baseId, sort, limit);
    ReviewsShopUserResponse response = reviewMapper.toReviewsShopUserResponse(reviews);
    return response;
  }

  //レビュー情報(単体) + 店舗情報を取得
  public ReviewShopResponse getReview(Integer reviewId){
    Review reviewWithShop = reviewRepository.findWithShopLocationById(reviewId);
    return reviewMapper.toReviewShopResponse(reviewWithShop);
  }

  public ReviewShopUserResponse createReview(Integer userId, ReviewCreateRequest request) {
    Review review = reviewMapper.toModel(request, userId);
    reviewRepository.insert(review);
    Review createdReview = reviewRepository.findWithShopLocationUserById(review.getId());
    return reviewMapper.toReviewShopUserResponse(createdReview);
  }

  public ReviewShopUserResponse updateReview(Integer reviewId, ReviewUpdateRequest request) {
    Review review = reviewMapper.toModelFromUpdate(request, reviewId);
    reviewRepository.update(review);
    Review updatedReview = reviewRepository.findWithShopLocationUserById(reviewId);
    return reviewMapper.toReviewShopUserResponse(updatedReview);
  }

  public void deleteReview(Integer reviewId) {
    // deleteメソッドの戻り値（削除件数）で存在を判断する
    int affectedRows = reviewRepository.delete(reviewId);
    if (affectedRows == 0) {
      // 1件も削除されなかった場合はリソースが存在しない
      throw new ResourceNotFoundException("Review not found with id: " + reviewId);
    }
    
  }
}
