package com.imeal.imeal_back.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imeal.imeal_back.review.dto.ReviewsShopUserResponse;
import com.imeal.imeal_back.review.entity.Review;
import com.imeal.imeal_back.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public ReviewsShopUserResponse getReviews(Integer baseId, String sort, Integer limit) {
    List<Review> reviews = reviewRepository.findByX(baseId, sort, limit);
    return new ReviewsShopUserResponse(reviews);
  }
}
