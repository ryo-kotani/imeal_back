package com.imeal.imeal_back.review.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsResponse {
  private List<ReviewResponse> reviews;
}
