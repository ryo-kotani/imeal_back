package com.imeal.imeal_back.review.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsShopUserResponse {
  private List<ReviewShopUserResponse> reviews;
}
