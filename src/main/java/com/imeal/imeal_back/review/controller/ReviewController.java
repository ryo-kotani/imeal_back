package com.imeal.imeal_back.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imeal.imeal_back.review.dto.ReviewsShopUserResponse;
import com.imeal.imeal_back.review.service.ReviewService;

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
  
}
