package com.imeal.imeal_back.review.repository;

import java.util.List;

import com.imeal.imeal_back.review.entity.Review;

public interface ReviewRepository {
  List<Review> findByX(String locationIdQuery, String limitQuery, String sortQuery);
  void insert(Review review);
  void update(Review review);
  void delete(Integer id);
}
