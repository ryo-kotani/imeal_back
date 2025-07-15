package com.imeal.imeal_back.shop.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.imeal.imeal_back.shop.entity.Shop;

@Mapper
public interface ShopRepository {
  @SelectProvider(type = ShopSqlBuilder.class, method = "buildSearchSql")
  List<Shop> findByX(@Param("baseId")Integer baseId);
  Shop findById(Integer id);
  void insert(Shop shop);
  void update(Shop shop);
  void delete(Integer id);
  // locationIdQuery = "where base_id = 1"
  // locationIdQuery = "where base_id = " + locationId
}
