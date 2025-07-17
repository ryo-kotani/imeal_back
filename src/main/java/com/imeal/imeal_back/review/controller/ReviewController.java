package com.imeal.imeal_back.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.review.dto.ReviewCreateRequest;
import com.imeal.imeal_back.review.dto.ReviewShopUserResponse;
import com.imeal.imeal_back.review.dto.ReviewsShopUserResponse;
import com.imeal.imeal_back.review.service.ReviewService;
import com.imeal.imeal_back.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @GetMapping("")
  public ResponseEntity<ReviewsShopUserResponse> getReviews(@RequestParam("baseId")Integer baseId, 
                                                            @RequestParam(name = "sort", required = false)String sort, @RequestParam(name = "limit", required = false)Integer limit) {
    ReviewsShopUserResponse response = reviewService.getReviews(baseId, sort, limit);
    return ResponseEntity.ok(response);
  }
  
  @PostMapping
  public ReviewShopUserResponse postReview(@RequestBody ReviewCreateRequest request, @AuthenticationPrincipal CustomUserDetails currentUser) {
    return reviewService.createReview(currentUser.getUser().getId(), request);
  }
  
}
