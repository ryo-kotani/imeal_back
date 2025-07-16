package com.imeal.imeal_back.shop.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.imeal.imeal_back.shop.entity.Shop;

@Mapper
public interface ShopRepository {
  @SelectProvider(type = ShopSqlBuilder.class, method = "buildSearchSql")
  List<Shop> findByX(@Param("baseId")Integer baseId);

  // Shopに紐づくLocation, Reviews, Reviewに紐づくUserを取得する
  @Select("select s.*, l.lat, l.lon from shops s join locations l on s.location_id = l.id where s.id = #{id}")
  @Results(value = {
    @Result(column = "id", property = "id"),
    @Result(column = "location_id", property = "location.id"),
    @Result(column = "lat", property = "location.lat"),
    @Result(column = "lon", property = "location.lon"),
    @Result(column = "id", property = "reviews", many = @Many(select = "com.imeal.imeal_back.review.repository.ReviewRepository.findWithUserByShopId"))
  })
  Shop findById(Integer id);

  @Insert("INSERT INTO shops (url, name, address, distance, minutes, base_id, location_id) VALUES(#{url}, #{name}, #{address}, #{distance}, #{minutes}, #{base.id}, #{location.id})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Shop shop);
  
  void update(Shop shop);
  void delete(Integer id);
}
