package com.imeal.imeal_back.review.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.imeal.imeal_back.review.entity.Review;

@Mapper
public interface ReviewRepository {
  @SelectProvider(type = ReviewSqlBuilder.class, method = "buildSearchSql")
  List<Review> findByX(@Param("baseId")Integer baseId, @Param("sort")String sort, @Param("limit")Integer limit);
  void insert(Review review);
  void update(Review review);
  void delete(Integer id);

  // user情報も一緒に取得する
  @Select("select r.*, u.name as user_name from reviews r join users u on r.user_id = u.id where r.shop_id = #{shopId}")
  @Results(value = {
    @Result(column = "user_id", property = "user.id"),
    @Result(column = "user_name", property = "user.name")
  })
  List<Review> findWithUserByShopId(Integer shopId);

}
