package com.imeal.imeal_back.shop.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.imeal.imeal_back.shop.entity.Shop;

@Mapper
public interface ShopRepository {
  List<Shop> findByX(String locationIdQuery);
  Shop findById(Integer id);
  void insert(Shop shop);
  void update(Shop shop);
  void delete(Integer id);
}
