package com.imeal.imeal_back.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
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

  public ReviewsShopUserResponse getReviews(Integer baseId, String sort, Integer limit) {
    List<Review> reviews = reviewRepository.findByX(baseId, sort, limit);
    return new ReviewsShopUserResponse(reviews);
  }

  public ReviewShopUserResponse createReview(Integer userId, ReviewCreateRequest request) {
    Review review = reviewMapper.toModel(request, userId);
    reviewRepository.insert(review);
    Review createdReview = reviewRepository.findWithShopLocationUserById(review.getId());
    return reviewMapper.toResponse(createdReview);
  }

  public ReviewShopUserResponse updateReview(Integer reviewId, ReviewUpdateRequest request) {
    Review review = reviewMapper.toModelFromUpdate(request, reviewId);
    reviewRepository.update(review);
    Review updatedReview = reviewRepository.findWithShopLocationUserById(reviewId);
    return reviewMapper.toResponse(updatedReview);
  }

  public void deleteReview(Integer reviewId) {
    reviewRepository.delete(reviewId);
  }
}
