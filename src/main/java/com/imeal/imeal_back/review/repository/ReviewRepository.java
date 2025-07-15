package com.imeal.imeal_back.review.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.imeal.imeal_back.review.entity.Review;

public interface ReviewRepository {
  @SelectProvider(type = ReviewSqlBuilder.class, method = "buildSearchSql")
  List<Review> findByX(@Param("baseId")Integer baseId, @Param("sort")String sort, @Param("limit")Integer limit);
  void insert(Review review);
  void update(Review review);
  void delete(Integer id);
}
